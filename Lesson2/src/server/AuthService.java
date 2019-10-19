package server;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String password) {
        String sql = String.format("SELECT nickname FROM main\n" +
                "WHERE login = '%s'\n" +
                "AND password = '%s'", login, password);

        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getDictionary() {
        String sql = "SELECT word FROM dictionary";
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addMessageToDB(String sender, String receiver,
                                      String text, String date) {
        String sql = String.format("INSERT INTO messages(sender, receiver, text, date)\n" +
                "VALUES('%s','%s','%s','%s')", sender, receiver, text, date);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeNickInDB(String login, String nickToChange) {
        String sql = String.format("UPDATE main SET nickname = '%s' WHERE login = '%s'", nickToChange, login);
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void writeHistoryToFile(String sender, String receiver, String text, String date) {
        try {
            FileWriter fw = new FileWriter("D:\\1.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(sender + " " + receiver + " " + text + " " + date);
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException a) {
            a.printStackTrace();
        }
    }

    public static String get100messagesFromHistoryInFile(String nick) {
        try {
            File history = new File("D:\\1.txt");
            FileReader fr = new FileReader(history);
            BufferedReader br = new BufferedReader(fr);

            StringBuffer sb = new StringBuffer();
            int laststrings = 100;
            ArrayList<String> fileToStrings = new ArrayList();

            while (br.ready()) {
                fileToStrings.add(br.readLine());
            }

            if (fileToStrings.size() > laststrings) {
                for (int startRead = fileToStrings.size() - laststrings - 1; startRead < fileToStrings.size() - 1; startRead++) {
                    String[] read = fileToStrings.get(startRead).split(" ", 3);
                    if (!read[0].equals("")) {
                        if (read[1].equals("null")) {
                            sb.append(read[0] + " : " + read[2] + "\n");
                        } else if (nick.equals(read[1]) || nick.equals(read[0])) {
                            sb.append("private[ " + read[0] + " ] to [ "
                                    + read[1] + " ] " + read[2] + "\n");
                        }
                    }
                }
            } else {
                for (String msg : fileToStrings) {
                    String[] read = msg.split(" ", 3);
                    if (!read[0].equals("")) {
                        if (read[1].equals("null")) {
                            sb.append(read[0] + " : " + read[2] + "\n");
                        } else if (nick.equals(read[1]) || nick.equals(read[0])) {
                            sb.append("private[ " + read[0] + " ] to [ "
                                    + read[1] + " ] " + read[2] + "\n");
                        }
                    }
                }
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException a) {
            a.printStackTrace();
        }
        return null;
    }

    public static String getMessageFromDBForNick(String nick) {
        String sql = String.format("SELECT * FROM messages\n" +
                "WHERE sender='%s'\n" +
                "OR receiver = '%s'\n" +
                "OR receiver = 'null'", nick, nick);

        StringBuilder sb = new StringBuilder();
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String sender = rs.getString(2);
                String receiver = rs.getString(3);
                String text = rs.getString(4);
                String date = rs.getString(5);

                if (receiver.equals("null")) {
                    sb.append(sender + " : " + text + "\n");
                } else {
                    sb.append("private[ " + sender + " ] to [ "
                            + receiver + " ] :" + text + "\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
