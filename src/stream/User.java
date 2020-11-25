package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class User {
    private String username;
    private Socket socket;
    private PrintStream socOut;
    private BufferedReader socIn;
    private String room;

    /**
     *
     * @param username the name the user chose
     * @param socket the socket used by the user to send message
     * @throws IOException
     */
    public User(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        socIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        socOut = new PrintStream(socket.getOutputStream());
        this.room="root";
    }

    /**
     *
     * @return the username of the current user
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return  the output socket
     */
    public PrintStream getSocOut() {
        return socOut;
    }

    /**
     *
     * @return the input socket
     */
    public BufferedReader getSocIn() {
        return socIn;
    }

    /**
     * This method is used to send the content of the history to the user given his room
     * @param background the class used to store the history
     */
    public void sendBackground(History background){
        for(String str : background.getBackgroundByRoom(this.room)){
            send(str);
        }
    }

    /**
     *
     * @param str the message to send
     */
    public void send(String str){
        socOut.println(str);
    }

    /**
     * Close all the sockets
     */
    public void closeMe()  {
        socOut.close();
        try {
            socIn.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return the name of the room currently used by the user
     */
    public String getRoom() {
        return room;
    }

    /**
     * Change the room the user is currently assigned to
     * @param room the room name
     */
    public void setRoom(String room) {
        this.room = room;
    }
}
