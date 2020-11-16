package stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class History {
    private List<String> background;
    private String myPath;

    public History(String path) {
        this.background = new LinkedList<>();
        myPath = path;

        File file = new File(path);

        if (file.canRead()) {
            Scanner reader = null;
            try {
                reader = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (reader != null) {
                while (reader.hasNext()) {
                    background.add(reader.nextLine());
                }
                reader.close();
            }

        }


    }

    public List<String> getBackground() {
        return background;
    }

    public void add(String string) {
        background.add(string);
    }

    public void save() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(myPath);
            for (String str : background) {

                writer.write(str);

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
