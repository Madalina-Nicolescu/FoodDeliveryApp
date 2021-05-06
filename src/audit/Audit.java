package audit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import audit.CSV;

public class Audit {
    private CSV csv = CSV.getInstance();
    private static Audit single_instance = null;

    private Audit()
    {

    }

    public static Audit getInstance()
    {
        if(single_instance == null)
            single_instance = new Audit();
        return single_instance;
    }


    public void write(String action){
        try(FileWriter writer = new FileWriter(new File("data/actions.csv"), Boolean.TRUE)) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            writer.write(String.format("%s, %s\n", action, formatter.format(date)));
        } catch (IOException e){
            System.out.println("The 'actions.csv' file couldn't be open.");
        }
    }
}
