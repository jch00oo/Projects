package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Stage implements Serializable {

    /* init stage */
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

    void addStage() {
        File stageFile = Utils.join(STAGE_PATH);
        Utils.writeObject(stageFile, this);
    }

    void clearStage() {
        stagedToAdd.clear();
        stagedToRemove.clear();
    }

    static final File STAGE_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "stage");

    /* file name, id */
    private HashMap<String, String> stagedToAdd;
    private HashMap<String, String> stagedToRemove;

}