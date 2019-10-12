package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;

    public Server() throws SQLException {
        AuthService.connect();
        AuthService.addMessageToDB("nick1", null, "qwerty123", "11.11");
//        System.out.println(AuthService.getNickByLoginAndPass("log2in1","pass1"));

        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void broadcastMsg(String msg, String sender) {
        AuthService.addMessageToDB(sender, null, msg, getDate());
        for (ClientHandler c : clients) {
            c.sendMSG(sender + " : " + msg);
        }
    }

    public void broadcastMsg(String msg, String sender, String receiver) {
        AuthService.addMessageToDB(sender, receiver, msg, getDate());
        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver) ||
                    c.getNick().equals(sender)
            ) {
                c.sendMSG("private[ " + sender + " ] to [ "
                        + receiver + " ] :" + msg);
            }
        }
    }


    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
        clientHandler.sendMSG(
                AuthService.getMessageFromDBForNick(clientHandler.getNick())
        );
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isLoginAuthorised(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler c : clients) {
            sb.append(c.getNick() + " ");
        }
        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMSG(msg);
        }
    }

    public String getDate() {
        Date dateNow = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(dateNow) + " : ";
    }
}
