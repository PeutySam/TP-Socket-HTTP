package stream;

import stream.window.Window;

import java.io.BufferedReader;

public class ClientBackgroundThread extends Thread {

    private BufferedReader socIn;
    Window theWindow;
    public ClientBackgroundThread(BufferedReader socIn, Window w) {
        this.socIn = socIn;
        theWindow = w;
    }


    public void run() {
        try {
            while(true){
                theWindow.writeText(socIn.readLine());
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClient:" + e);
        }

    }
}
