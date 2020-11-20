package questionseven;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Payload implements Serializable {

    private String path;
    private String fileName;
    private byte[] data;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public Payload(String filePath) {
        path = filePath;
        String[] splitted = path.split("/");
        fileName = splitted[splitted.length - 1];
        data = getFileData();
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getFileData() {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    public byte[] getData() {
        return data;
    }
}
