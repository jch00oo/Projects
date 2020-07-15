package gitlet;

import java.util.*;
import java.io.*;

import static gitlet.Gitlet.*;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @two sad berkeley bears
 */
public class Main implements Serializable {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    private File startergit;
    private File stage;
    HashMap <String, String> branchHistory;// branch name as key, id of current branch as value

    /**public void Gitlet() {
     startergit= new File (cd_gitlet);
     startergit.mkdir();
     stage = new File (cd_stage);
     stage.mkdir();
     }**/

    public static void Main(String... args) {
        // FILL THIS IN
        if (args.length == 0){
            System.out.println("Please enter a command.");
        }
        switch(args[0]){
            case "init":
                init();
                break;
            case "add":
                add(args);
                break;
            case "commit":
                commit(args);
                break;
            case "log":
                log();
                break;
            //other cases to be added after the checkpoint
            default: //else
                System.out.println("No command with that name exists.");
                break;
        }
    }

    }

