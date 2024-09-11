package Model.db;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    private static Logger instance  =null;

    static BufferedWriter writer;
    private Logger(){
        try {
            writer = new BufferedWriter(new FileWriter("./Log.txt",true));
        } catch (IOException e) {
            System.err.println("Databases not found.!!");
        }
    }

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }

        return instance;
    }

    /**
     * Log the message to the Log file.
     * @param message
     */
    public void log(String message){

        try {
            writer.write("[" + LocalDateTime.now() + "]");
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
