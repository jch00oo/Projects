package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Stage implements Serializable {


    static final File STAGE_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "stage");

    /* file name, id */
    private HashMap<String, String> stagedToAdd;
    private HashMap<String, String> stagedToRemove;

    /* initial stage */
    public Stage() {
        stagedToAdd = new HashMap<>();
        stagedToRemove = new HashMap<>();
    }

    HashMap<String, String> getStagedToAdd(){
        return stagedToAdd;
    }

    HashMap<String, String> getStagedToRemove(){
        return stagedToRemove;
    }

    void stageToAdd(String fileName, String id) {
        stagedToAdd.put(fileName, id);
    }

    void stageToRemove(String fileName, String id) {
        stagedToRemove.put(fileName, id);
    }

    void deleteFromAdd(String fileName) {
        stagedToAdd.remove(fileName);
    }

    void deleteFromRemove(String fileName) {
        stagedToRemove.remove(fileName);
    }

    // Write object into stage file and adds it.
    //@source https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
    void addStage() {
        File stageFile = Utils.join(STAGE_PATH);
        Utils.writeObject(stageFile, this);
    }

    // Clears the staging area; called after commits.
    void clearStage() {
        stagedToAdd.clear();
        stagedToRemove.clear();
    }

}