/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EchoServerMultiThreaded {

    /**
     * main method
     *
     **/
    public static void main(String args[]) {
        ServerSocket listenSocket;
        Set<User> clients = new HashSet<>();
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();

                BufferedReader socIn = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                String recPacket = socIn.readLine();

                User client = new User(recPacket,clientSocket);
                clients.add(client);

                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                ClientThread ct = new ClientThread(client,clients);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

  