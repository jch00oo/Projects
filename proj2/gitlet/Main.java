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
                case "rm":
                    rmHelper(args);
                    break;
                case "rm-branch":
                    rmBranchHelper(args);
                    break;
                case "global-log":
                    globalLogHelper(args);
                    break;
                case "find":
                    find(args);
                    break;
                case "reset":
                   reset(args[1]);
                   break;
                case "checkout":
                    checkoutHelper(args);
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

    public static void rmHelper(String[] args) {
        if (args.length != 2) {
            Utils.message("Incorrect operands.");
        } else {
            try {
                command.rm(args[1]);
            } catch (GitletException g) {
                Utils.message(g.getMessage());
            }
        }
    }

    public static void rmBranchHelper(String[] args) {
        if (args.length != 2) {
            Utils.message("Incorrect operands.");
        } else {
            try {
                command.rmBranch(args[1]);
            } catch (GitletException g) {
                Utils.message(g.getMessage());
            }
        }
    }

    public static void globalLogHelper(String[] args) {
        if (args.length != 1) {
            Utils.message("Incorrect operands.");
        } else {
            command.global();
        }
    }

    public static void checkoutHelper(String[] args) {
        if (args.length == 2) {
            try {
                command.checkout3(args[1]);
            } catch (GitletException error) {
                Utils.message(error.getMessage());
            }
        } else if (args.length == 3 && args[1].equals("--")) {
            try {
                command.checkout1(args[2]);
            } catch (GitletException g) {
                Utils.message(g.getMessage());
            }
        } else if (args.length == 4 && args[2].equals("--")) {
            try {
                command.checkout2(args[1], args[3]);
            } catch (GitletException g) {
                Utils.message(g.getMessage());
            }
        } else {
            Utils.message("Incorrect operands.");
        }
    }
}
