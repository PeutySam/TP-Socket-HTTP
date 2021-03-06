/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package streamTCP;

import streamTCP.window.Window;

import javax.swing.*;
import java.io.*;
import java.net.*;


public class EchoClient {


    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     * Start the UI
     * Asks the user to choose a username
     * If a connection is accepted a client thread is started
     * @param args Arguments for program, the ip and port to connect to
     * @throws IOException Exception if cannot read
     **/
    public static void main(String[] args) throws IOException {


        String username = null;
        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0], new Integer(args[1]).intValue());
            socIn = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            socOut = new PrintStream(echoSocket.getOutputStream());
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
        if (username == null || username.equals("")){
            socOut.close();
            socIn.close();
            stdIn.close();
            echoSocket.close();
            return;
        }
        socOut.println(username);
        socOut.println(username + " has joined the party");

        String line;

        Window w = new Window(socOut, username);


        ClientBackgroundThread ct = new ClientBackgroundThread(socIn, w);
        ct.start();


        w.setVisible(true);
        while (true) {
            line = stdIn.readLine();
            if (line.equals(".")) break;
            socOut.println(username + ": " + line);
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }
}


