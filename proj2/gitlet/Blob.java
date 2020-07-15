package gitlet;

import java.util.*;
import java.io.*;

//make a new Blob class for this code to create a Blob
//does serializable work like this
public class Blob implements Serializable {
    private byte[] fileContents;
    private Boolean tracked;
    private String fileName;

    public Blob() {
        fileContents = null;
        tracked = true;
    }

    public Blob(String fileName) {
        File currFile = new File(fileName);
        if (currFile.exists()){
            fileContents = Utils.readContents(currFile);
            tracked = true;
        } else {
            System.out.println(Utils.error("File does not exist.").getMessage());
        }
    }

    public byte[] getContents() {
        return fileContents;
    }
//    public byte[] Blob (byte[] fileContents){
//        this.fileContents=fileContents;
//        return this.fileContents;
//    }

}
