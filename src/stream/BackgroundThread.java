package stream;

import java.io.BufferedReader;

public class BackgroundThread extends Thread {

    private BufferedReader socIn;

    public BackgroundThread(BufferedReader socIn) {
        this.socIn = socIn;
    }


    public void run() {
        try {
            while(true){
                System.out.println(socIn.readLine());
            }
        } catch (Exception e) {
            System.err.println("Error in EchoClient:" + e);
        }

    }
}
