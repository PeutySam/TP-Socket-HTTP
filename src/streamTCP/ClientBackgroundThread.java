package streamTCP;

import streamTCP.window.Window;

import java.io.BufferedReader;

public class ClientBackgroundThread extends Thread {

    private BufferedReader socIn;
    Window theWindow;

    /**
     *
     * @param socIn the socket reading all the inputs
     * @param w the window used in the UI
     */
    public ClientBackgroundThread(BufferedReader socIn, Window w) {
        this.socIn = socIn;
        theWindow = w;
    }

    /**
     * The run method is called to print on the UI all the lines received
     */
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
