package streamUDP;



import streamUDP.window.Window;

import java.io.BufferedReader;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ClientBackgroundThread extends Thread {

    private MulticastSocket socket;
    private Window window;
    public ClientBackgroundThread(MulticastSocket socket, Window w) {
        this.socket = socket;
        this.window = w;
    }


    public void run() {
        try {
            while(true){
                byte buffer[] = new byte[1024];
                DatagramPacket data = new DatagramPacket(buffer, buffer.length);
                socket.receive(data);
                window.writeText(new String(data.getData(), StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClient:" + e);
        }

    }
}
