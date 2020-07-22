package gitlet;

import java.util.*;
import java.io.*;

import static gitlet.Gitlet.BLOB_PATH;

//make a new Blob class for this code to create a Blob
//does serializable work like this
public class Blob implements Serializable {
    private byte[] fileContents;
    private Boolean tracked;
    private String fileName;
    private String id;

    public Blob(String fileName) {
        File currFile = new File(fileName);
        if (currFile.exists()){
            fileContents = Utils.readContents(currFile);
            tracked = true;
            this.fileName = fileName;
            id = Utils.sha1(fileContents);
        } else {
            System.out.println(Utils.error("File does not exist.").getMessage());
        }
    }

    public byte[] getContents() {
        return fileContents;
    }

    public String getFileName() {
        return fileName;
    }

    public String getBlobId() {
        return id;
    }

    public void trackBlob() {
        File blobsFile = Utils.join(BLOB_PATH, this.getBlobId());
        Utils.writeContents(blobsFile, this.getContents());
    }

    static void blobCheckoutHelper (String id, String fileName) {
        File find = Utils.join(fileName);
        File from =Utils.join(Gitlet.BLOB_PATH, id);
        String content = Utils.readContentsAsString(from);
        Utils.writeContents(find, content);
    }
}