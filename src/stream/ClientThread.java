/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientThread
        extends Thread {

    private User client;
    private Set<User> clients;

    ClientThread(User s,Set<User> clients) {
        this.client = s;
        this.clients = clients;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        Set<PrintStream> socOuts = new HashSet<>();
        try {
            while (true) {
                String line = client.getSocIn().readLine();
                sendToAll(clients,line);
            }
        } catch (Exception e) {
            clients.remove(client);
            sendToAll(clients, client.getUsername() +" has been lost in the void");
            client.closeMe();

            System.err.println("Error in EchoServer thread:" + e);
        }
    }

    private void sendToAll( Set<User> users, String str){
        for(User u: users){
            try {
                u.getSocOut().println(str);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
