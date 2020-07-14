package gitlet;
import java.util.*;
import java.io.*;

public class Gitlet implements Serializable {

    private File startergit;
    private File stage;
    private File trash;
    HashMap <String, String> branchHistory;// branch name as key, id of current branch as value
    //make and declare the directories
    private static final String cd_gitlet = ".Gitlet";
    private static final String cd_stage = ".Gitlet/Stage";
    private static final String trash = ".Gitlet/Trash";

    public Gitlet(){
        startergit= new File (cd_gitlet);
        startergit.mkdir();
        stage = new File (cd_stage);
        stage.mkdir();
    }

    public static void fromFile(String args[]){
        //FIXMEALOT
        boolean isTracked()==false;
        boolean addTrack()==false;
        boolean stopTrack()==true;
    }

    public void commit(String commitMessage) {
        return;
    }

}