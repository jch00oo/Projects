package gitlet;

//To clear the stage faster and more efficiently.

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Stage implements Serializable {

//    private HashMap<String, Blob> files;
//
//
//    public Stage() {
//        files = new HashMap<>();
//    }
//
//    public void addFile(String fileName, Blob content) {
//        files.put(fileName, content);
//    }
//
//    public boolean deleteFile(String fileName) {
//        if(files.containsKey(fileName)) {
//            files.remove(fileName);
//            return true;
//        } else {
//            return false; /** error message? Continue? **/
//        }
//    }
//
//    public HashMap<String, Blob> getFiles() {
//        return files;
//    }

    private HashMap<String, Blob> stagedBlob;
    private HashSet<String> removedBlob;

    public Stage(Stage stage) {
        this.stagedBlob = stage.stagedBlob;
        this.removedBlob = stage.removedBlob;
    }

    public void newStage() {
        stagedBlob = new HashMap<>();
        removedBlob = new HashSet<>();
    }

    public void addToStage(String fileName) {
        File file = new File(fileName);
        Blob newBlob = new Blob(fileName);
        Utils.serialize(newBlob);
        stagedBlob.put(fileName, newBlob);
        Utils.serialize(newBlob);
    }

    public void removeFromStage(String fileName) {
        stagedBlob.remove(fileName);
        removedBlob.add(fileName);
    }

    public HashMap getStagedBlob() {
        return stagedBlob;
    }
}

