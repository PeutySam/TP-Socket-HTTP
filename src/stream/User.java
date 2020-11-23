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

    public User(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        socIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        socOut = new PrintStream(socket.getOutputStream());
        this.room="root";
    }

    public String getUsername() {
        return username;
    }

    public PrintStream getSocOut() {
        return socOut;
    }

    public BufferedReader getSocIn() {
        return socIn;
    }

    public void sendBackground(History background){
        for(String str : background.getBackgroundByRoom(this.room)){
            send(str);
        }
    }

    public void send(String str){
        socOut.println(str);
    }

    public void closeMe()  {
        socOut.close();
        try {
            socIn.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
