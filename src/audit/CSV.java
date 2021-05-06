package audit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CSV {
    private static CSV single_instance = null;

    private CSV()
    {

    }

    public static CSV getInstance()
    {
        if(single_instance == null)
            single_instance = new CSV();
        return single_instance;
    }

    public List<String> read(String fileName)
    {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(String.format("data/%s", fileName)));
        } catch (IOException e){
            System.out.println("The file " + fileName + " couldn't be open.");
        }

        return lines;
    }

    public void write(String fileName, List<String> content){
        try {
            Files.write(Paths.get(String.format("data/%s", fileName)), content);
        } catch (IOException e){
            System.out.println("The file " + fileName + " couldn't be open.");
        }
    }
}
