/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package streamUDP;


import streamUDP.window.Window;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;


public class EchoClient {


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     * @param args The arguments used to connect to the network, contains the multicast ip and the port
     * @throws IOException Exception if cannot read
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






        username = JOptionPane.showInputDialog("Please username: ");
        if (username.equals("")){
            stdIn.close();
            socket.close();
            return;
        }

        Sender sender = new Sender(username,socket,group, port);
        sender.send(username + " has joined the room");

        Window w = new Window(sender, username);

        ClientBackgroundThread clBackground = new ClientBackgroundThread(socket,w);
        clBackground.start();

        String line;

        w.setVisible(true);
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            sender.send(username +" : " + line);
        }

        stdIn.close();
        socket.close();
    }


}


