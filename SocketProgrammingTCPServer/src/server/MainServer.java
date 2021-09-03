package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    ServerSocket serverSocket;
    ExecutorService pool;

    public MainServer() throws IOException {
        System.out.println("Enter to Constructor");
        serverSocket = new ServerSocket(40000);
        System.out.println("Server Socket is initialized");
        pool = Executors.newFixedThreadPool(5);
    }

    public void run() throws IOException {
        while (true) {
            System.out.println("Server Socket starts listening");
            Socket connectionSocket = serverSocket.accept();
            System.out.println("Server Socket find a new connection");
            //TCP (IP SRC, IP DST, PORT SRC, PORT DST)
            ServerThread serverThread = new ServerThread(connectionSocket);
            pool.execute(serverThread);
        }
    }

    public static void main(String[] args) {
        try {

            MainServer mainServer = new MainServer();
            mainServer.run();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
