package gitlet;

import java.util.*;
import java.io.*;

//make a new Blob class for this code to create a Blob
//does serializable work like this
public class Blob implements Serializable {
    private String alphaNumeric; //the version name
    private byte[] fileContents;
    private Boolean tracked;

    public Blob() {
        alphaNumeric = null;
        fileContents = null;
        tracked = true;
    }

    public Blob(String fileName) {
        File currFile = new File(fileName);
        if (currFile.exists()){
            fileContents = Utils.readContents(currFile);
            tracked = true;
            alphaNumeric = Utils.sha1(fileContents);
        } else {
            System.out.println(Utils.error("File does not exist.").getMessage());
        }
    }

//    public byte[] Blob (byte[] fileContents){
//        this.fileContents=fileContents;
//        return this.fileContents;
//    }

}
