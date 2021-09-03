package server;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket connectionSocket;
    BufferedReader reader;
    BufferedWriter writer;

    public ServerThread(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
    }

    @Override
    public void run() {
        System.out.println("Local IP : " +
                connectionSocket.getLocalAddress().getHostAddress());
        System.out.println("Local Port : " +
                connectionSocket.getLocalPort());
        System.out.println("Remote IP : " +
                connectionSocket.getInetAddress().getHostAddress());
        System.out.println("Remote Port : " +
                connectionSocket.getPort());
        /**
         * name {name}  name masoud
         * Welcome masoud
         */
        /**
         * upper {string} upper Salam
         * SALAM
         */
        /**
         * exit     close
         */
        while (true) {
            try {
                String line = reader.readLine();
                System.out.println("Server received " + line);
                if (line.startsWith("name")) {
                    String[] tokens = line.split(" "); // name  // {name}
                    String name = tokens[1];
                    writer.write("Welcome " + name + "\n");
                    writer.flush();
                }
                else if (line.startsWith("upper")) {
                    String[] tokens = line.split(" ");
                    String word = tokens[1];
                    word = word.toUpperCase();
                    writer.write(word + "\n");
                    writer.flush();
                }
                else if (line.startsWith("exit")) {
                    break;
                }
                else {
                    writer.write("Your keyword is not recognized\n");
                    writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            reader.close();
            writer.close();
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
