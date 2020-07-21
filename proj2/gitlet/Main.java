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
    private static File gitlet = new File(".gitlet");
    private File stage;
    private static Gitlet command = new Gitlet();
    HashMap <String, String> branchHistory;// branch name as key, id of current branch as value
    private static String commitPath = ".gitlet/.commit";

    /**public void Gitlet() {
     startergit= new File (cd_gitlet);
     startergit.mkdir();
     stage = new File (cd_stage);
     stage.mkdir();
     }**/

    public static void main(String[] args) {
        // FILL THIS IN
        if (args.length == 0){
            System.out.println("Please enter a command.");
            return;
        } else if (!args[0].equals("init") && !gitlet.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        } else {
            switch (args[0]) {
                case "init":
                    init();
                    break;
                case "add":
                    addHelper(args);
                    break;
                case "commit":
                    commitHelper(args);
                    break;
                case "log":
                    log();
                    break;
                case "branch":
                    branchHelper(args);
                    break;
                case "global-log":
                    global();
                    break;
                case "find":
                    find(args);
                    break;
                //other cases to be added after the checkpoint
                default: //else
                    System.out.println("No command with that name exists.");
                    break;
            }
        }
    }

    public static void commitHelper(String[] args) { /* our commit takes in one String */
        if (args.length > 2) {
            System.out.println("Incorrect operands.");
        } else if (args.length < 2 || args[1].isEmpty()) {
            System.out.println("Please enter a commit message.");
        } else {
            try {
                command.commit(args[1]);
            } catch (GitletException error) {
                Utils.message(error.getMessage());
            }
        }
    }

    public static void addHelper(String[] args) { /* our add takes in one String */
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
        } else {
            try {
                command.add(args[1]);
            } catch (GitletException error) {
                Utils.message(error.getMessage());
            }
        }
    }

    public static void branchHelper(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect operands.");
        } else {
            try {
                command.branch(args[1]);
            } catch (GitletException error) {
                Utils.message(error.getMessage());
            }
        }
    }
}
