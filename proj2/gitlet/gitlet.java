package gitlet;
import java.util.*;
import java.io.*;

public class gitlet implements Serializable {
    ///variables from google Doc- delete as necessary
    HashMap<String,Commit> commithistory; //stores all the commits, id as key and Commit as value
    HashMap <String, String> tracked;// stores all tracked files, id as key and blob as value
    HashMap <String, String> staged; //stores all staged files, id as key and blob as value
    ArrayList <String> removedFiles; //stores fileName of removed files in an array list
    ArrayList <String> untrackedFiles; //stores fileName of untracked files
    Commit recentCommit; //tracks the most recent commit
    private File startergit;
    private File stage;
    private File trash;
    HashMap <String, String> branchHistory;// branch name as key, id of current branch as value
    //make and declare the directories
    private static final String cd_gitlet=".gitlet";
    private static final String cd_stage=".gitlet/stage";
    private static final String trash=".gitlet/trash";
   //makes a skeleton gitlet- needs more added like branches
    public gitlet(){
        startergit= new File (cd_gitlet);
        startergit.mkdir();
        stage=new File (cd_stage);
        stage.mkdir();
    }
    public static void fromFile(String args[]){
        //FIXMEALOT
        boolean isTracked()==false;
        boolean addTrack()==false;
        boolean stopTrack()==true;
    }

}
