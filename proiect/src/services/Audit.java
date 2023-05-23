package services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class Audit {
    FileWriter fileWriter;

    private static Audit instance;

    private Audit() {
        try {
            this.fileWriter = new FileWriter(new File("src/csv/audit.csv").getAbsolutePath(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Audit getInstance() {
        if (instance == null) {
            instance = new Audit();
        }
        return instance;
    }

    public void write(String text) {
        try {
            this.fileWriter = new FileWriter(new File("src/csv/audit.csv").getAbsolutePath(), true);
            long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
            this.fileWriter.write((text + " - " + timestamp + "\n"));
            this.fileWriter.flush();
            this.fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
