package gitlet;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String[] args) {
        // FILL THIS IN
        gitlet xgitlet = null;
        //based off lab9
        if (args.length==null){
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
    }

}
