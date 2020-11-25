/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package streamTCP;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ClientThread
        extends Thread {

    private User client;
    private Set<User> clients;
    private History background;


    /**
     *
     * @param s The user using this thread
     * @param clients All the known user using the chat
     * @param background The chat history
     */
    ClientThread(User s,Set<User> clients, History background) {
        this.client = s;
        this.clients = clients;
        this.background = background;

    }

    /**
     * receives a request from client then sends an echo to the client
     * Also check if the command !room is used and if so modify the room value of the user
     * If a user disconnects, other users will be notified
     **/
    public void run() {
        Set<PrintStream> socOuts = new HashSet<>();
        try {
            while (true) {
                String line = client.getSocIn().readLine();
                if(line.startsWith(client.getUsername() +": !room")){
                   String roomName = line.substring((client.getUsername() +": !room").length()+1);
                   line = client.getUsername() + " left to room " + roomName;
                   background.add(client.getRoom() + ": " + line);
                   sendToRoom(clients, line);
                   client.setRoom(roomName);
                   client.sendBackground(background);
                   sendToRoom(clients, client.getUsername() + " joined the room");

                }else {
                    background.add(client.getRoom() + ": " + line);
                    sendToRoom(clients, line);
                }
            }
        } catch (Exception e) {
            clients.remove(client);
            String outputStr = client.getUsername() +" has been lost in the void";
            sendToAll(clients, outputStr);
            background.add(outputStr);
            client.closeMe();

            System.err.println("Error in EchoServer thread:" + e);
        }
    }

    /**
     * This Method is used to send messages to every knwon user
     * @param users All the known users
     * @param str The message we want to send
     */
    private void sendToAll( Set<User> users, String str){
        for(User u: users){
            try {
                u.send(str);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * This Method is used to send messages to every user in the same room
     * @param users All the users known
     * @param str The message we want to send
     */
    private void sendToRoom( Set<User> users, String str){
        for(User u: users){
            try {
                if(u.getRoom().equals(client.getRoom())){
                    u.send(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
