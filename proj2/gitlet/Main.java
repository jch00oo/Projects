package gitlet;

import java.util.*;
import java.io.*;
/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void Main(String... args) {
        // FILL THIS IN
        Gitlet xgitlet = null;
        //based off lab9
        if (args.length == 0){
            System.out.println("Please enter a command.");
        }
        switch(args[0]){
            case "init":

                xgitlet=new gitlet();
                xgitlet.init();
                break;
            case "add":
                xgitlet.add(args);
                break;
            case "commit":
                xgitlet.commit(args);
                break;
            case "rm":
                xgitlet.remove(args);

                break;
            case "log":
                xgitlet.log(args);
                break;
            case "global-log":
                xgitlet.globalog(args);
                break;
                //other cases to be added after the checkpoint
            default: //else
                System.out.println("No command with that name exists.");
                break;
            }
        }

    private HashMap<String,Commit> commithistory; //stores all the commits, id as key and Commit as value
    private HashMap <String, String> tracked;// stores all tracked files, id as key and blob as value
    private HashMap <String, String> staged; //stores all staged files, id as key and blob as value
    private ArrayList <String> removedFiles; //stores fileName of removed files in an array list
    private ArrayList <String> untrackedFiles; //stores fileName of untracked files
    private Commit recentCommit; //tracks the most recent commit

    //make and declare the directories
    private static final String cd_gitlet = ".gitlet";
    private static final String cd_stage = ".gitlet/.stage";
    private static final String trash = ".gitlet/.trash";
    private static final String commitPath = ".gitlet/.commit";
    private static final String workingPath = System.getProperty("user.dir");

    public static void init() {

        return;
    }

    public static void commit(String commitMessage) {

        return;
    }

    public static void add(String fileName) {

        return;
    }

    public static void log() {

        return;
    }

}
