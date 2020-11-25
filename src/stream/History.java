package stream;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class History {
    private String myPath;

    /**
     *
     * @param path the path to the file where the history is stored
     */
    public History(String path) {
        myPath = path;
    }

    /**
     * Return the history of messages
     * @param roomName The room name of the current room
     * @return List of strings containing all the messages sent in the past
     */
    public List<String> getBackgroundByRoom(String roomName) {
        List<String> strings = new LinkedList<>();

        File file = new File(myPath);

        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(reader != null){
            while(reader.hasNext()){
                String line = reader.nextLine();
                if(line.startsWith(roomName)){
                    line = line.substring(roomName.length()+2);
                    strings.add(line);
                }

            }
            reader.close();
        }

        return  strings;
    }

    /**
     * This Method is used to add the messages in the history text file
     * @param string The message we want to write in the history file
     */
    public void add(String string) {
        try {
            FileOutputStream writer = new FileOutputStream(myPath,true);

            writer.write((string + "\n").getBytes());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
