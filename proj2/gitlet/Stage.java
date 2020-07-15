package gitlet;

//To clear the stage faster and more efficiently.

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Gitlet.STAGE_PATH;

public class Stage implements Serializable {

    /* file name, id */
    private HashMap<String, String> stagedToAdd;
    private HashMap<String, String> stagedToRemove;

    /* init stage */
    public Stage() {
        stagedToAdd = new HashMap<>();
        stagedToRemove = new HashMap<>();
    }

    public HashMap<String, String> getStagedToAdd(){
        return stagedToAdd;
    }

    public HashMap<String, String> getStagedToRemove(){
        return stagedToRemove;
    }

    public void stageToAdd(String fileName, String id) {
        stagedToAdd.put(fileName, id);
    }

    public void stageToRemove(String fileName, String id) {
        stagedToRemove.put(fileName, id);
    }

    public void clearStage() {
        stagedToAdd.clear();
        stagedToRemove.clear();
    }

    public void addStage() {
        File stageFile = Utils.join(STAGE_PATH);
        Utils.writeObject(stageFile, this);
    }

}

