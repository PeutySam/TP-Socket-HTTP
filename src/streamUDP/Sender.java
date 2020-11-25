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

    /**
     *
     * @param username the name of the user
     * @param socket the multicast socket used to communicate
     * @param group the address of the multicast group
     * @param port the port used
     */
    public Sender(String username, MulticastSocket socket, InetAddress group, int port) {
        this.username = username;
        this.socket = socket;
        this.group = group;
        this.port = port;
    }

    /**
     * Sends the packet to the multicast socket
     * @param str the packet we want to send
     */
    public void send(String str){
        DatagramPacket data = new DatagramPacket(str.getBytes(),str.length(),group, port);
        try {
            socket.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
