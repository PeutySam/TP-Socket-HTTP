package stream;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class History {
    private String myPath;

    public History(String path) {
        myPath = path;
    }

    public List<String> getBackground() {
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
                strings.add(reader.nextLine());
            }
            reader.close();
        }

        return  strings;
    }

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
