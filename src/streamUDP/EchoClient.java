/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package streamUDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;


public class EchoClient {


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {
        String username = null;
        MulticastSocket socket = null;
        InetAddress group = null;
        BufferedReader stdIn = null;
        int port = Integer.parseInt(args[1]);


        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            socket = new MulticastSocket(port);
            group = InetAddress.getByName(args[0]);
            socket.joinGroup(group);

            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + args[0]);
            System.exit(1);
        }

        ClientBackgroundThread clBackground = new ClientBackgroundThread(socket);
        clBackground.start();

        System.out.println("Input a username noob: ");
        username = stdIn.readLine();
        SendPacket(socket, username + " has joined the room", group, port);



        String line;

        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            SendPacket(socket, username +" : " + line, group, port);
        }

        stdIn.close();
        socket.close();
    }

    public static void SendPacket (MulticastSocket socket, String str,InetAddress group, int port){
        DatagramPacket data = new DatagramPacket(str.getBytes(),str.length(),group, port);
        try {
            socket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


