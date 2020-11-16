package streamUDP;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ClientBackgroundThread extends Thread {

    private MulticastSocket socket;

    public ClientBackgroundThread(MulticastSocket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
            while(true){
                byte buffer[] = new byte[1024];
                DatagramPacket data = new DatagramPacket(buffer, buffer.length);
                socket.receive(data);
                System.out.println(new String(data.getData(), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClient:" + e);
        }

    }
}
