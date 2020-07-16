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
    private Repository currBranch;

    //make and declare the directories
    private static String cd_gitlet = ".gitlet";
    private static String cd_stage = ".gitlet/.stage";
    private static String commitPath = ".gitlet/.commit";
    private static String workingPath = System.getProperty("user.dir");

    static final File REPO_PATH = Utils.join(workingPath, ".gitlet", "repo");
    static final File STAGE_PATH = Utils.join(workingPath, ".gitlet", "stage");
    static final File BLOB_PATH = Utils.join(workingPath, ".gitlet", "blobs");
    static final File COMMIT_PATH = Utils.join(workingPath, ".gitlet", "commits");
    static final File GEN_PATH = Utils.join(workingPath);

    public static void init() {
        File gitlet = Utils.join(GEN_PATH, ".gitlet");

        if (gitlet.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        else{
            gitlet.mkdir();

            Repository repo = new Repository();
            File inrepo= Utils.join(REPO_PATH);
            Utils.writeObject(inrepo,repo);

            Stage stage= new Stage();
            File instage= Utils.join(STAGE_PATH);
            Utils.writeObject(instage,stage);

            File inblob =Utils.join(BLOB_PATH);
            inblob.mkdir();

            File incommitting=Utils.join(COMMIT_PATH);
            incommitting.mkdir();
            repo.getHead().addCommit();
        }
    }

    public void commit(String commitMessage) {
//        File file = new File(cd_gitlet);
//        File stageFile = new File(cd_stage);
//        File commitFile = new File(commitPath);
        if (commitMessage.equals("")) {
            throw Utils.error("Please enter a commit message.");
        }
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class); /*this*/
        Commit head = currRepo.getHead();
        HashMap<String, String> headBlob =currRepo.getTracked(); /*this*/
        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class); /*this*/
        HashMap<String, String> stagedToAdd = currStage.getStagedToAdd();
        HashMap<String, String> stagedToRemove = currStage.getStagedToRemove();
        HashMap<String, String> newBlob = new HashMap<>(headBlob);
        if (stagedToAdd.keySet().isEmpty() && stagedToRemove.keySet().isEmpty()) {
            throw Utils.error("No changes added to the commit.");
        }
        for (String addedFileName: stagedToAdd.keySet()) {
            newBlob.put(addedFileName, stagedToAdd.get(addedFileName));
        }
        for (String removedFileName: stagedToRemove.keySet()) {
            newBlob.put(removedFileName, stagedToAdd.get(removedFileName));
        }
        currStage.clearStage();
        currStage.addStage();
        Commit newCommit = new Commit(commitMessage, head, newBlob);
        newCommit.addCommit();
        currRepo.newHead(newCommit);
        currRepo.addRepo();

//        else if (Utils.readObject(stageFile, Stage.class).getStagedBlob().isEmpty() && Utils.readObject(untrackedFiles).isEmpty()) {
//            throw Utils.error("No changes added to the commit.");
//        } else {
//            Branch commitBranch = Utils.readObject(commitFile,Branch.class);
//            Commit currentCommit = Utils.readObject(____);
//            Stage stage = Utils.readObject(stageFile, Stage.class);
//            HashMap<String, Blob> files = currentCommit.getContent();

        }

    public static void add(String fileName) {
        File toAdd = new File(fileName);
        if (!(toAdd.exists())) {
            throw Utils.error("File does not exist.");
        } else { /* check if stage contains file */
            File fileInRepo = Utils.join(REPO_PATH);
            Repository currRepo = Utils.readObject(fileInRepo, Repository.class);
            HashMap<String, String> headContent = currRepo.getTracked(); /* use */
            File fileInStage = Utils.join(STAGE_PATH);
            Stage currStage = Utils.readObject(fileInStage, Stage.class); /*use*/
            Blob blobToAdd = new Blob(fileName);
            if (currStage.getStagedToAdd().containsKey(fileName)) { /* any other test cases? */
                currStage.getStagedToAdd().remove(fileName);
                currStage.stageToAdd(fileName, blobToAdd.getBlobId());
                blobToAdd.trackBlob();
            } else {
                currStage.stageToAdd(fileName, blobToAdd.getBlobId());
                blobToAdd.trackBlob();
            }
            File toStageFile = Utils.join(STAGE_PATH);
            Utils.writeObject(toStageFile, currStage);
        }
    }

    public static void log() { //recommended method by aerin
        File committed = Utils.join(COMMIT_PATH);
        Repository allCommits = Utils.readObject(committed, Repository.class); //autograder says bad
        //https://howtodoinjava.com/java/collections/arraylist/hashset-to-arraylist/
        HashSet <String> commits = new HashSet<>(allCommits.getAllCommits());
        ArrayList <String> actualCommits = new ArrayList<>(commits);
        ListIterator commitsItr = actualCommits.listIterator();
        while (commitsItr.hasNext()) {
            Commit commit = (Commit) commitsItr.next();
            System.out.println("===");
            System.out.println("commit " + commit.getId());
            System.out.println("Date: " + commit.getTimeStamp());
            System.out.println();
        }
//        k's og code
//        File[] history=parents.listFiles();
//        for (File file: history){
//            Commit file=(Commit)file;
//            System.out.println("===");
//            System.out.println("commit"+file.getId);
//            System.out.println("Date:"+file.getTimeStamp);
//            System.out.println(file.getCommitMessage);
//        }
    }
}//i'm not sure what happened but detached head