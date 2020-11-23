package streamUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Sender {
    private String username;
    private MulticastSocket socket;
    private InetAddress group;
    private int port;

    public Sender(String username, MulticastSocket socket, InetAddress group, int port) {
        this.username = username;
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    public void send(String str){
        DatagramPacket data = new DatagramPacket(str.getBytes(),str.length(),group, port);
        try {
            socket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
