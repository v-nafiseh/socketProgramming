package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    DatagramSocket serverSocket;

    public Server() throws SocketException {
        serverSocket = new DatagramSocket(20000);
    }

    public void run() throws IOException {
        /**
         * name {name}  ---> Welcome {name}
         * upper {line} --->
         * exit
         */
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Server starts listening");
            serverSocket.receive(packet);

            System.out.println("Packet Data: " + new String(packet.getData()));
            System.out.println("Packet Length: " + packet.getLength());
            System.out.println("Packet Source IP: " + packet.getAddress().getHostAddress());
            System.out.println("Packet Source Port: " + packet.getPort());

            String line = new String(packet.getData());
            String response = null;
            if (line.startsWith("name")) {
                String[] tokens = line.split(" ");
                response = "Welcome " + tokens[1];

            } else if (line.startsWith("upper")) {
                String[] tokens = line.split(" ");
                response = tokens[1].toUpperCase();
            }
            DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                    packet.getAddress(), packet.getPort());
            serverSocket.send(sendPacket);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.run();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
