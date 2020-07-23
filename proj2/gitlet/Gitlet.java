package gitlet;

import java.util.*;
import java.io.*;
import java.util.Formatter;
import java.util.Arrays;

public class Gitlet implements Serializable {

    private File startergit;
    private File stage;
    HashMap<String, String> branchHistory;// branch name as key, id of current branch as value
    //make and declare the directories

    private HashMap<String, Commit> commitHistory; //stores all the commits, id as key and Commit as value
    private HashMap<String, String> tracked;// stores all tracked files, id as key and blob as value
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

    /* Initialize the version-control system. Create directories, master branch,
     * and initiate initial commit. */
    public static void init() {
        File gitlet = Utils.join(GEN_PATH, ".gitlet");

        if (gitlet.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        } else {
            gitlet.mkdir();

            Repository repo = new Repository();
            File inrepo = Utils.join(REPO_PATH);
            Utils.writeObject(inrepo, repo);

            Stage stage = new Stage();
            File instage = Utils.join(STAGE_PATH);
            Utils.writeObject(instage, stage);

            File inblob = Utils.join(BLOB_PATH);
            inblob.mkdir();

            File incommitting = Utils.join(COMMIT_PATH);
            incommitting.mkdir();
            repo.getHead().addCommit();
        }
    }

    /* @param commit message
     * Commit files in staging area to the repository.
     */
    public void commit(String commitMessage) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        HashMap<String, String> headBlob = currRepo.getTracked();
        Commit head = currRepo.getHead();
        HashMap<String, String> stagedToAdd = currStage.getStagedToAdd();
        HashMap<String, String> stagedToRemove = currStage.getStagedToRemove();
        HashMap<String, String> newBlob = new HashMap<>(headBlob);

        if (commitMessage.equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        else if (stagedToAdd.keySet().isEmpty() && stagedToRemove.keySet().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        for (String addedFileName : stagedToAdd.keySet()) {
            newBlob.put(addedFileName, stagedToAdd.get(addedFileName));
        }
        for (String removedFileName : stagedToRemove.keySet()) {
            newBlob.put(removedFileName, stagedToAdd.get(removedFileName));
        }
        currStage.clearStage();
        Commit newCommit = new Commit(commitMessage, head, newBlob);
        currRepo.newHead(newCommit);
        newCommit.addCommit();
        currStage.addStage();
        currRepo.addRepo();
    }

    /* @param fileName;
     * Stage current file in the working directory to be tracked
     * to add to the next commit. Throw error if file with such name doesn't exist.
     */
    public static void add(String fileName) {
        File toAdd = new File(fileName);
        if (!(toAdd.exists())) {
            System.out.println("File does not exist.");
            System.exit(0);
        } else {
            File fileInRepo = Utils.join(REPO_PATH);
            Repository currRepo = Utils.readObject(fileInRepo, Repository.class);
            File fileInStage = Utils.join(STAGE_PATH);
            Stage currStage = Utils.readObject(fileInStage, Stage.class);

            HashMap<String, String> headContent = currRepo.getTracked();
            Blob blobToAdd = new Blob(fileName);

            if (currStage.getStagedToRemove().containsKey(fileName)) {
                currStage.getStagedToRemove().remove(fileName);
            } else if (headContent.containsKey(fileName) && headContent.get(fileName).equals(blobToAdd.getBlobId())) {
                if (currStage.getStagedToAdd().containsKey(fileName)) {
                    currStage.getStagedToAdd().remove(fileName);
                }
            } else {
                currStage.stageToAdd(fileName, blobToAdd.getBlobId());
                blobToAdd.trackBlob();
            }
            File toStageFile = Utils.join(STAGE_PATH);
            Utils.writeObject(toStageFile, currStage);
        }
    }

    /* Prints out commit hashID, date, and commit message in order
     * from the head commit to initial commit.
     */
    public static void log() {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit pointer = currRepo.head;
        while (! pointer.getParentCommitId().equals("")) {
            logHelper(pointer);
            pointer = pointer.getParentCommit();
        }
        logHelper(pointer);
    }

    /* @param current pointer commit
     * Helper method for log() that takes in a Commit object and prints out
     * its information in format.
     */
    public static void logHelper(Commit curr) {
        System.out.println("===");
        System.out.println("commit " + curr.getId());
        System.out.println("Date: " + curr.getTimeStamp());
        System.out.println(curr.getCommitMessage());
        System.out.println();
    }

    /* @param branch name
     * Creates a new branch pointer with the input name and point it at the
     * current head. Does NOT switch to a new branch, it merely creates a pointer.
     */
    public static void branch(String branchName) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        /* store all branches by branch name and id in hash map. */
        HashMap<String, String> allBranches = currRepo.getBranches();
        if (allBranches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        } else {
            String headId = currRepo.getHead().getId();
            /* creates pointer using new branch name, but still pointing to same head id. */
            allBranches.put(branchName, headId);
        }
        currRepo.addRepo();
    }

    /* @param filename
     * If file is tracked by the current head commit & staged for
     * addition, unstage and delete from working directory.
     * If not tracked, unstage but do not delete from working directory.
     */
    public static void rm(String fileName) {
        /* create booleans to check if file is tracked and/or staged. */
        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);
        boolean isStaged = currStage.getStagedToAdd().containsKey(fileName);

        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        boolean isTracked = currRepo.getTracked().containsKey(fileName);

        if (!isStaged && !isTracked) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        /* file should be unstaged whether it's tracked or not. */
        if (isStaged) {
            currStage.getStagedToAdd().remove(fileName);
        }
        /* staged for removal and/or delete from working directory. */
        if (isTracked) {
            String idToRemove = currRepo.getTracked().get(fileName);
            if (!currStage.getStagedToRemove().containsKey(fileName)) {
                currStage.stageToRemove(fileName, idToRemove);
            }
            Utils.restrictedDelete(fileName);
        }
        currStage.addStage();
    }

    public void rmBranch (String branchName) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);

        HashMap<String, String> branches = currRepo.getBranches();

        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        } else if (branchName.equals(currRepo.getCurrBranch())) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        } else {
            branches.remove(branchName);
        }
        currRepo.addRepo();
    }

    /* Prints out all branches' names with * by the master branch, staged files,
     * removed files, modified files that have not been staged, and untracked files.
     */
    public static void status() {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);

        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        System.out.println("=== Branches ===");
        int numOfBranches = currRepo.getBranches().size();
        Object[] branchNames = currRepo.getBranches().keySet().toArray();
        Arrays.sort(branchNames);
        for (Object branch: branchNames) {
            if (currRepo.getCurrBranch().equals(branch)) {
                branch = "*" + branch;
            }
            System.out.println(branch);
        }
        System.out.println();

        System.out.println("=== Staged Files ===");
        Object[] allStaged = currStage.getStagedToAdd().keySet().toArray();
        Arrays.sort(allStaged);
        for (Object file: allStaged) {
            System.out.println(file);
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        Object[] allRemoved = currStage.getStagedToRemove().keySet().toArray();
        Arrays.sort(allRemoved);
        for (Object file: allRemoved) {
            System.out.println(file);
        }
        System.out.println();

        /** DEFINITELY EDIT **/
        System.out.println("=== Modifications Not Staged For Commit ===");
        HashSet<String> notStaged = new HashSet<>();

        /****/
        List<String> stagedFileNames = Utils.plainFilenamesIn(GEN_PATH);
        boolean isStaged, isTracked;
        ArrayList<String> modified = new ArrayList<>();

        /* loop through file names */
        for (String file : stagedFileNames) {
            isStaged = currStage.getStagedToAdd().containsKey(file);
            isTracked = currRepo.getTracked().containsKey(file);

            if (!isStaged && isTracked) {
                String wd = new Blob(file).getBlobId();
                if (!currRepo.getTracked().get(file).equals(wd)) {
                    modified.add(file + " (modified)");
                }
            } else if (isStaged) {
                String wd = new Blob(file).getBlobId();
                if (!currStage.getStagedToAdd().get(file).equals(wd)) {
                    modified.add(file + " (modified)");
                }
            }
        }
        /***/
        List<String> allPresent = Utils.plainFilenamesIn(GEN_PATH);
        ArrayList<String> deleted = new ArrayList<>();
        for (String staged : currStage.getStagedToAdd().keySet()) {
            if (!allPresent.contains(staged)) {
                deleted.add(staged + " (deleted)");
            }
        }
        for (String tracked : currRepo.getTracked().keySet()) {
            if (!currStage.getStagedToRemove().containsKey(tracked)
                    && !allPresent.contains(tracked)) {
                deleted.add(tracked + " (deleted)");
            }
        }
        /***/

        notStaged.addAll(modified);
        notStaged.addAll(deleted);

        ArrayList<String> notStagedList = new ArrayList<>(notStaged);
        Collections.sort(notStagedList);
        for (String modifiedFile: notStagedList) {
            System.out.println(modifiedFile);
        }
        System.out.println();

        /** DEFINITELY EDIT **/
        System.out.println("=== Untracked Files ===");

        /***/
        List<String> allFiles = Utils.plainFilenamesIn(GEN_PATH);
        ArrayList<String> untrackedFiles = new ArrayList<>();
        for (String file : allFiles) {
            if (!(currStage.getStagedToAdd().containsKey(file)) && !(currRepo.getTracked().containsKey(file))) {
                untrackedFiles.add(file);
            }
        }
        Collections.sort(untrackedFiles);
        /***/

        for (String untracked: untrackedFiles) {
            System.out.println(untracked);
        }
        System.out.println();
    }

    public static void global(){ //doesn't have to be in order, screw hashmap
        File allcommitsfolder = Utils.join(COMMIT_PATH);
        File [] eacommit= allcommitsfolder.listFiles(); //https://www.geeksforgeeks.org/file-listfiles-method-in-java-with-examples/
        for (File i : eacommit){
            logHelper(Utils.readObject(i,Commit.class));
        }
    }

    public static void find(String message){
        /**File repoFile3 = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile3, Repository.class);
        HashMap<String,Commit> pointer = currRepo.getAllCommits(); .getAllCommits isn't working??? **/
        //temp fix is to use files and folders
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit found;
        Formatter uh = new Formatter();

//        File allcommitsfolder = Utils.join(COMMIT_PATH);
//        File[] eacommit= allcommitsfolder.listFiles();
        Boolean exists = false;

        for (String commitId: currRepo.getAllCommitsIds()) {
            File allcommitsfolder = Utils.join(COMMIT_PATH, commitId);
            found = Utils.readObject(allcommitsfolder, Commit.class);
            if (found.getCommitMessage().equals(message)) {
                exists = true;
                uh.format("%s\n", found.getId());
            }
        }
//            Commit j=Utils.readObject(i,Commit.class);
//            if (j.getCommitMessage().equals(message)){
//                exists=true;
//                System.out.println(j.getId());
//            }
//            else{
//                exists=false;
//            }
        if (exists == false){
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
        System.out.println(uh.toString());
    }

    /* Case 1:
     * @param file name
     */
    public void checkout1 (String fileName) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        String headCommitId = currRepo.getHead().getId();
        checkout2(headCommitId, fileName);
    }

    /* Case 2:
     * @param commit id and file name
     */
    public void checkout2 (String id, String fileName) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        String fullId = currRepo.getFullId(id);

        if (!currRepo.getAllCommitsIds().contains(fullId)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        } else {
            File commitFile = Utils.join(COMMIT_PATH, fullId);
            Commit currCommit = Utils.readObject(commitFile, Commit.class);

            boolean found = currCommit.getContent().containsKey(fileName);

            if (!found) {
                System.out.println("File does not exist in that commit.");
                System.exit(0);
            } else {
                String fileId = currCommit.getContent().get(fileName);
                Blob.blobCheckoutHelper(fileId, fileName);
            }
        }
    }

    /* Case 3:
     * @param branch name
     */
    public void checkout3 (String branchName) throws GitletException {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);

        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        if (!currRepo.getBranches().containsKey(branchName)) {
            System.out.println("No such branch exists.");
            System.exit(0);
        } else if (currRepo.getCurrBranch().equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        } else {
            String branchCommitId = currRepo.getBranches().get(branchName);
            File commitFile = Utils.join(COMMIT_PATH, branchCommitId);
            Commit branchHeadCommit = Utils.readObject(commitFile, Commit.class);

            /** modify rest of this method **/
            HashMap<String, String> prevTracked = branchHeadCommit.getContent();
            HashMap<String, String> currTracked = currRepo.getTracked();
            List<String> workingFiles = Utils.plainFilenamesIn(GEN_PATH);

            for (String working : workingFiles) {
                if (prevTracked.containsKey(working)
                        && !currRepo.getTracked().containsKey(working)) {
                    System.out.println("There is an untracked file in the way;"
                            + "delete it or add it first.");
                    System.exit(0);
                }
            }

            for (String tracked : currTracked.keySet()) {
                if (!prevTracked.containsKey(tracked)
                        && workingFiles.contains(tracked)) {
                    Utils.restrictedDelete(tracked);
                }
            }
            for (String prev : prevTracked.keySet()) {
                String blobID = prevTracked.get(prev);
                Blob.blobCheckoutHelper(blobID, prev);
            }

            currRepo.newBranch(branchName, branchHeadCommit);
            currStage.clearStage();
            currRepo.addRepo();
            currStage.addStage();
        }
    }

    public static void reset(String commitId) { //user enters shortened sha1 name
        File repoFile4 = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile4, Repository.class);

        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        String fullId = currRepo.getFullId(commitId);
        File lastCommitFile = Utils.join(COMMIT_PATH, fullId);
        Commit lastCommit = Utils.readObject(lastCommitFile, Commit.class);
        List<String> filesInWD = Utils.plainFilenamesIn(GEN_PATH);

        if (!currRepo.getAllCommitsIds().contains(fullId)) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        } else {
            for (String fileName : filesInWD) {
                if (lastCommit.getContent().containsKey(fileName) && !currRepo.getTracked().containsKey(fileName)) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }

            for (String trackedFile : currRepo.getTracked().keySet()) {
                if (!lastCommit.getContent().containsKey(trackedFile) && filesInWD.contains(trackedFile)) {
                    Utils.restrictedDelete(trackedFile);
                }
            }

            for (String lastTracked : lastCommit.getContent().keySet()) {
                String fileId = lastCommit.getContent().get(lastTracked);
                Blob.blobCheckoutHelper(fileId, lastTracked);
            }
            currRepo.repoResetHelper(lastCommit);
            currStage.clearStage();
            currRepo.addRepo();
            currStage.addStage();
        }
    }
    /**new reset using the repository method
    public static void reset_new(String fileLetter){ //user enters shortened sha1 name
        File repoFile4 = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile4, Repository.class);
        ArrayList<Commit> curr= currRepo.getcurrbranchcommit();
        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);
        ArrayList<String> allUntracked = currRepo.getUntracked(currStage.getStagedToAdd());
        for (Commit commit:curr){
            if (commit.getFullId(fileLetter)){
                if(allUntracked==null){
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                } else {
                    fileLetter=commit.getId();//if starts with the entered 5 letter string, is the same thing
                    currRepo.newHead(commit);//moves the head
                    currStage.clearStage();
                }
            }
            else{
                System.out.println(Utils.error("No commit with that id exists."));
                System.exit(0);
            }
        }
    } **/
    /**public static void checkout(String [] args){
        try{
            String fileName;
            if (args[0]=="--"&&args.length==2){
                //first case revert to original and make pointer go back to head
                Commit curr = Utils.readObject(Utils.join(REPO_PATH),Commit.class);
                HashMap<String,String> fileContents= curr.getContent();
                if (fileContents.containsKey(args[1])){
                    //just overwrite the file no need to move head will just stay here
                    File copied= new File(args[1]);
                    Utils.writeContents(copied,curr.getContent()); //dubious code
                }else{
                    throw Utils.error("File does not exist in that commit.");
                }
            }
            else if(args[1]=="--"&&args.length==3){
                //probably fix this code
                Commit curr = Utils.readObject(Utils.join(REPO_PATH),Commit.class);
                HashMap<String,String> fileContents= curr.getContent();
                if (fileContents.containsKey(args[2])){
                    //just overwrite the file no need to move head
                    File copied= new File(args[2]);
                    Utils.writeContents(copied,curr.getContent());
                }else{
                    throw Utils.error("File does not exist in that commit.");
                }
            }else {
                //were multiple files in a commit. end of the command, overwrite everything. If tracked, deleted, clear stage.
                Commit curr = Utils.readObject(Utils.join(REPO_PATH),Commit.class);
                HashMap<String,String> fileContents= curr.getContent();
                //definitely must fix the rest
            }
        } catch(Exception e){
            System.out.println(Utils.error("No checkout command like that exists."));
        }
    }**/
}

