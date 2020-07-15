package gitlet;

import java.util.*;
import java.io.*;

public class Gitlet implements Serializable {

    private File startergit;
    private File stage;
    HashMap <String, String> branchHistory;// branch name as key, id of current branch as value
    //make and declare the directories

    private HashMap<String,Commit> commitHistory; //stores all the commits, id as key and Commit as value
    private HashMap <String, String> tracked;// stores all tracked files, id as key and blob as value
    private String recentCommit; //tracks the most recent commit's id
    private Branch currBranch;

    //make and declare the directories
    private static String cd_gitlet = ".gitlet";
    private static String cd_stage = ".gitlet/.stage";
    private static String commitPath = ".gitlet/.commit";
    private String workingPath = System.getProperty("user.dir");

    public static void init() {
        File gitlet = new File(".gitlet");
        if (gitlet.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
        else{
            gitlet.mkdir();
            File committing = new File(".gitlet/committing");
            committing.mkdir();
            Stage stage = new Stage();
            return;
        }
    }

    public void copy(Gitlet o) {
        this.commitHistory = o.commitHistory;
        this.tracked = o.tracked;
        this.recentCommit = o.recentCommit;
        this.currBranch = o.currBranch;
    }

    public static Gitlet deserialize() {
        return (Gitlet) Utils.deserialize("gitlet", new File(cd_gitlet));
    }

    public void commit(String commitMessage) {
        File file = new File(cd_gitlet);
        File stageFile = new File(cd_stage);
        File commitFile = new File(commitPath);
        if (commitMessage.equals("")) {
            throw Utils.error("Please enter a commit message.");
        }
//        else if (Utils.readObject(stageFile, Stage.class).getStagedBlob().isEmpty() && Utils.readObject(untrackedFiles).isEmpty()) {
//            throw Utils.error("No changes added to the commit.");
//        } else {
//            Branch commitBranch = Utils.readObject(commitFile,Branch.class);
//            Commit currentCommit = Utils.readObject(____);
//            Stage stage = Utils.readObject(stageFile, Stage.class);
//            HashMap<String, Blob> files = currentCommit.getContent();
        Gitlet gitlet = deserialize();
        this.copy(gitlet);
        Commit currentCommit = (Commit) Commit.deserialize(); //* add deserialize w string id **/
        HashMap<String, Blob> committedBlob = currentCommit.getContent();
        Stage currentStage = new Stage(Stage.deserialize()); //*paste ds into Stage */
        HashMap<String, String> addedBlob = currentStage.getStagedBlob();
        }

    public static void add(String[] fileName) {
        File added = new File(String.valueOf(fileName));
        if (!added.exists()) { //https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            throw new IllegalArgumentException("File does not exist.");
        } else if (fileName.length != 1) {
            throw new IllegalArgumentException("Incorrect operands.");
        } else {
            //takeover from init
            Stage stage = Utils.readObject(added,Stage.class);
            //how to bring the fucking hash values along with it
            File addedwithid= Utils.join(added,Commit.getId());
        }
    }

    public static void log() {
        File committed = new File(commitPath);
        Branch allCommits = Utils.readObject(committed, Branch.class);
        ArrayList<Commit> commits = /* somehow get commits in branch here */;
        ListIterator commitsItr = commits.listITerator();
        while (commitsItr.hasNext()) {
            Commit commit = (Commit) commitsItr.next();
            System.out.println("===");
            System.out.println("commit " + commit.getId());
            System.out.println("Date: " + commit.getTimeStamp());
            System.out.println();
        }

//        File[] history=parents.listFiles();
//        for (File file: history){
//            Commit file=(Commit)file;
//            System.out.println("===");
//            System.out.println("commit"+file.getId);
//            System.out.println("Date:"+file.getTimeStamp);
//            System.out.println(file.getCommitMessage);
//        }
    } /** based on arin's recommendation but like idk? **/

}