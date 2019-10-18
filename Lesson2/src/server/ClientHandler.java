package server;

import client.Controller;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private Server server;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String nick;
    private String login;
    private HashSet dictionary;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

//            System.out.println("socket.getPort() "+ socket.getPort());
//            System.out.println("socket.getLocalPort() "+socket.getLocalPort());
//
//            System.out.println("socket.getInetAddress() "+socket.getInetAddress());
//            System.out.println("socket.getLocalSocketAddress() "+socket.getLocalSocketAddress());
//            System.out.println("socket.getRemoteSocketAddress() "+socket.getRemoteSocketAddress());


            ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(() -> {

                try {
                    // цикл авторизации
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");
//                            String[] token = str.split(" +",3);
                            String newNick = AuthService.getNickByLoginAndPass(token[1], token[2]);
                            if (newNick != null) {
                                if (!server.isLoginAuthorised(token[1])) {
                                    sendMSG("/authok " + newNick);
                                    nick = newNick;
                                    login = token[1];
                                    server.subscribe(this);
                                    server.broadcastMsg("Клиент " + nick + " авторизовался", nick);
                                    timeout(0);
                                    break;
                                } else {
                                    sendMSG("Учетная запись уже используется");
                                    timeout(120000);
                                }
                            } else {
                                sendMSG("Неверный логин / пароль");
                                timeout(120000);
                            }
                        }
                    }
                    //цикл работы
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                sendMSG("/end");
                                break;
                            }

                            if (str.startsWith("/changenick")) {
                                String memory = nick;
                                String[] token = str.split(" +", 2);
                                AuthService.changeNickInDB(login, token[1]);
                                nick = token[1];
                                sendMSG("/changenick " + nick);
                                server.broadcastClientList();
                                server.broadcastMsg(memory + " сменил ник на " + nick, memory);
                            }

                            if (str.startsWith("/w")) {
                                String[] token = str.split(" +", 3);
                                server.broadcastMsg(token[2], nick, token[1]);
                                // writeHistoryToFile(str);
                            }
                        } else {
                            server.broadcastMsg(str, nick);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                    System.out.println("Клиент " + nick + " отключился");
                }
            });

            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMSG(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }

    public void timeout(int ms) throws SocketException {
        try {
            socket.setSoTimeout(ms);
            System.out.println("Время пошло!");
        } catch (SocketException e) {
            throw new SocketException();
        }
    }
}
