package gitlet;

import java.io.*;



//make a new Blob class for this code to create a Blob
//does serializable work like this
public class Blob implements Serializable {

    public Blob(String fileName2) {
        File currFile = new File(fileName2);
        if (currFile.isFile()){
            fileName = fileName2;
            fileContents = Utils.readContents(currFile);
            byte[] name = this.fileName.getBytes();
            id = Utils.sha1(name, fileContents);
        }
    }

    byte[] getContents() {
        return fileContents;
    }

    String getFileName() {
        return fileName;
    }

    String getBlobId() {
        return id;
    }

    void trackBlob() {
        File blobsFile = Utils.join(BLOB_PATH, this.getBlobId());
        Utils.writeContents(blobsFile, this.getContents());
    }

    static void blobCheckoutHelper (String id, String fileName) {
        File find = Utils.join(fileName);
        File from =Utils.join(BLOB_PATH, id);
        String content = Utils.readContentsAsString(from);
        Utils.writeContents(find, content);
    }

    static final File BLOB_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "blobs");

    private byte[] fileContents;
    private String fileName;
    private String id;
}