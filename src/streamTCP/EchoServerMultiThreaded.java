/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package streamTCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServerMultiThreaded {

    /**
     * main method
     * Executes the server and opens the port set in the arguments
     * As new people connect the new threads are created, and also each user profile is created and
     * they are assigned into a room.
     * @param args Arguments given to the program, contains port
     **/
    public static void main(String args[]) {
        ServerSocket listenSocket;
        Set<User> clients = new HashSet<>();

        History background = new History("saveFile");

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                try{
                    BufferedReader socIn = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    String recPacket = socIn.readLine();

                    User client = new User(recPacket,clientSocket);

                    client.sendBackground(background);

                    clients.add(client);

                    System.out.println("Connexion from:" + clientSocket.getInetAddress());
                    ClientThread ct = new ClientThread(client,clients,background);
                    ct.start();
                }catch(Exception e){

                }
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

  