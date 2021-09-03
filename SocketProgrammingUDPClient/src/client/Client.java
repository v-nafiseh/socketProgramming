package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client {
    DatagramSocket clientSocket;

    public Client(int portNumber) throws SocketException {
        clientSocket = new DatagramSocket(portNumber);
    }

    public void run() throws IOException {

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Please enter something");
            String line = consoleReader.readLine();
            DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length,
                    InetAddress.getByName("localhost"), 20000);
            clientSocket.send(packet);

            if (line.startsWith("exit"))
                break;

            byte[] buffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(receivedPacket);

            System.out.println(new String(receivedPacket.getData()));
        }

        clientSocket.close();
    }

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client(17000);
            client.run();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
