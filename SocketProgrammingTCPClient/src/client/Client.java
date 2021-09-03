package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;

    public Client() throws IOException {
        //TCP (IP SRC, IP DST, PORT SRC, PORT DST)
        // client 20000
        // server 40000
        System.out.println("Client starts constructor");
        socket = new Socket("localhost", 40000);
        System.out.println("Client Socket is initialized");
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run() {
        System.out.println("Local IP : " +
                socket.getLocalAddress().getHostAddress());
        System.out.println("Local Port : " +
                socket.getLocalPort());
        System.out.println("Remote IP : " +
                socket.getInetAddress().getHostAddress());
        System.out.println("Remote Port : " +
                socket.getPort());
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Please enter something: ");
                String line = consoleReader.readLine();
                System.out.println(line);
                writer.write(line + "\n");
                writer.flush();
                if (line.startsWith("exit")) {
                    break;
                }
                line = reader.readLine();
                System.out.println("Server Response: " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            consoleReader.close();
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client( );
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
