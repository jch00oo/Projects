package gitlet;

import java.util.*;
import java.io.*;
import java.util.Formatter;
import java.util.Arrays;

public class Gitlet {

    //make and declare the directories
    private static String cd_gitlet = ".gitlet";
    private static String cd_stage = ".gitlet/.stage";
    private static String commitPath = ".gitlet/.commit";
    private static String workingPath = System.getProperty("user.dir");

    static final File REPO_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "repo");
    static final File STAGE_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "stage");
    static final File BLOB_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "blobs");
    static final File COMMIT_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "commits");
    static final File GEN_PATH = Utils.join(System.getProperty("user.dir"));

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

    /* @param fileName;
     * Stage current file in the working directory to be tracked
     * to add to the next commit. Throw error if file with such name doesn't exist.
     */
    public void add(String fileName) {
        File toAdd = new File(fileName);
        if (!toAdd.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        } else {
            File repoFile = Utils.join(REPO_PATH);
            Repository currRepo = Utils.readObject(repoFile, Repository.class);
            HashMap<String, String> headContent = currRepo.getTracked();
            File stageFile = Utils.join(STAGE_PATH);
            Stage currStage = Utils.readObject(stageFile, Stage.class);

            Blob blobToAdd = new Blob(fileName);

            if (currStage.getStagedToRemove().containsKey(fileName)) {
                currStage.deleteFromRemove(fileName);
            } else if (headContent.containsKey(fileName) && headContent.get(fileName).equals(blobToAdd.getBlobId())) {
                if (currStage.getStagedToAdd().containsKey(fileName)) {
                    currStage.deleteFromAdd(fileName);
                }
            } else {
                currStage.stageToAdd(fileName, blobToAdd.getBlobId());
                blobToAdd.trackBlob();
            }
            currStage.addStage();
        }
    }

    /* @param commit message
     * Commit files in staging area to the repository.
     */
    public void commit(String commitMessage) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit head = currRepo.getHead();
        HashMap<String, String> headBlob = currRepo.getTracked();

        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);
        HashMap<String, String> stagedToAdd = currStage.getStagedToAdd();
        HashMap<String, String> stagedToRemove = currStage.getStagedToRemove();

        if (commitMessage.isEmpty()) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        if (stagedToAdd.keySet().isEmpty() && stagedToRemove.keySet().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        HashMap<String, String> newBlob = new HashMap<>(headBlob);
        for (String addedFileName : stagedToAdd.keySet()) {
            newBlob.put(addedFileName, stagedToAdd.get(addedFileName));
        }
        for (String removedFileName : stagedToRemove.keySet()) {
            newBlob.remove(removedFileName);
        }
        currStage.clearStage();
        currStage.addStage();

        Commit newCommit = new Commit(commitMessage, head, newBlob);
        newCommit.addCommit();

        currRepo.commitHelper1(newCommit);
        currRepo.addRepo();
    }

    public void log() {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit pointer = currRepo.getHead();
        while (!pointer.getParentCommitId().equals("")) {
            logHelper2(pointer);
            pointer = pointer.getParentCommit(currRepo);
        }
        logHelper2(pointer);
    }

    /* @param current pointer commit
     * Helper method for log() that takes in a Commit object and prints out
     * its information in format.
     */
    public static void logHelper2(Commit curr) {
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
        File repoFile = Utils.join(Repository.REPO_PATH);
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
        File stageFile = Utils.join(Stage.STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);
        boolean isStaged = currStage.getStagedToAdd().containsKey(fileName);

        File repoFile = Utils.join(Repository.REPO_PATH);
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
        File repoFile = Utils.join(Repository.REPO_PATH);
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
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);

        File stageFile = Utils.join(Stage.STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        System.out.println("=== Branches ===");
        int numOfBranches = currRepo.getBranches().size();
        Object[] branchNames = currRepo.getBranches().keySet().toArray();
        Arrays.sort(branchNames);
        for (Object branch: branchNames) {
            boolean isMaster = currRepo.getCurrBranch().equals(branch);
            if (isMaster) {
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
            boolean staged = currStage.getStagedToAdd().containsKey(file);
            boolean tracked = currRepo.getTracked().containsKey(file);

            if (!staged && tracked) {
                String wd = new Blob(file).getBlobId();
                if (!currRepo.getTracked().get(file).equals(wd)) {
                    modified.add(file + " (modified)");
                }
            } else if (staged) {
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
            boolean hasBeenStRm = currStage.getStagedToRemove().containsKey(tracked);
            boolean presentInFile = allPresent.contains(tracked);
            if (!hasBeenStRm
                    && !presentInFile) {
                deleted.add(tracked + " (deleted)");
            }
        }
        /***/

        /**
         Object[] allStaged = currStage.getStagedToAdd().keySet().toArray();
         Arrays.sort(allStaged);
         **/

        notStaged.addAll(modified);
        notStaged.addAll(deleted);

        Object[] notStagedArray = notStaged.toArray();
        Arrays.sort(notStagedArray);
        for (Object modifiedFile: notStagedArray) {
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
        File allcommitsfolder = Utils.join(Commit.COMMIT_PATH);
        File [] eacommit= allcommitsfolder.listFiles(); //https://www.geeksforgeeks.org/file-listfiles-method-in-java-with-examples/
        for (File i : eacommit){
            logHelper2(Utils.readObject(i,Commit.class));
        }
    }

    public static void find(String message){
        /**File repoFile3 = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile3, Repository.class);
        HashMap<String,Commit> pointer = currRepo.getAllCommits(); .getAllCommits isn't working??? **/
        //temp fix is to use files and folders
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit found;

        boolean exists = false;

        for (String commitId: currRepo.getAllCommitsIds()) {
            File allcommitsfolder = Utils.join(Commit.COMMIT_PATH, commitId);
            found = Utils.readObject(allcommitsfolder, Commit.class);
            if (found.getCommitMessage().equals(message)) {
                exists = true;
                System.out.println(found.getId());
            }
        }

        if (exists == false){
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
    }

    /* Case 1:
     * @param file name
     */
    public void checkout1 (String fileName) {
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        String headCommitId = currRepo.getHead().getId();
        checkout2(headCommitId, fileName);
    }

    /* Case 2:
     * @param commit id and file name
     */
    public void checkout2 (String id, String fileName) {
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        String fullId = currRepo.getFullId(id);
        boolean commitExists = currRepo.getAllCommitsIds().contains(fullId);

        if (!commitExists) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        } else {
            File commitFile = Utils.join(Commit.COMMIT_PATH, fullId);
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
    public void checkout3 (String branchName) {
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        boolean exists = currRepo.getBranches().containsKey(branchName);
        boolean isCurrent = currRepo.getCurrBranch().equals(branchName);

        if (isCurrent) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        } else if (!(isCurrent) && exists){
            File stageFile = Utils.join(Stage.STAGE_PATH);
            Stage currStage = Utils.readObject(stageFile, Stage.class);

            String branchCommitId = currRepo.getBranches().get(branchName);
            File commitFile = Utils.join(Commit.COMMIT_PATH, branchCommitId);
            Commit branchHeadCommit = Utils.readObject(commitFile, Commit.class);

            HashMap<String, String> prev = branchHeadCommit.getContent();
            HashMap<String, String> curr = currRepo.getTracked();
            List<String> workingFiles = Utils.plainFilenamesIn(GEN_PATH);

            for (String working : workingFiles) {
                boolean isTracked = currRepo.getTracked().containsKey(working);
                if (prev.containsKey(working)
                        && !isTracked) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }

            for (String tracked : curr.keySet()) {
                if (!prev.containsKey(tracked)
                        && workingFiles.contains(tracked)) {
                    Utils.restrictedDelete(tracked);
                }
            }

            for (String prevFile : prev.keySet()) {
                String blobID = prev.get(prevFile);
                Blob.blobCheckoutHelper(blobID, prevFile);
            }

            currRepo.checkout3Helper(branchName, branchHeadCommit);
            currStage.clearStage();
            currRepo.addRepo();
            currStage.addStage();
        } else {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
    }

    public static void reset(String commitId) { //user enters shortened sha1 name
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        boolean exists = currRepo.getAllCommitsIds().contains(currRepo.getFullId(commitId));

        if (!exists) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        } else {
            File stageFile = Utils.join(Stage.STAGE_PATH);
            Stage currStage = Utils.readObject(stageFile, Stage.class);
            String fullId = currRepo.getFullId(commitId);
            File lastCommitFile = Utils.join(Commit.COMMIT_PATH, fullId);
            Commit lastCommit = Utils.readObject(lastCommitFile, Commit.class);
            List<String> filesInWD = Utils.plainFilenamesIn(GEN_PATH);

            for (String fileName : filesInWD) {
                boolean tracked = currRepo.getTracked().containsKey(fileName);
                boolean lastExists = lastCommit.getContent().containsKey(fileName);
                if (lastExists && !tracked) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }

            for (String trackedFile : currRepo.getTracked().keySet()) {
                boolean lastExists2 = lastCommit.getContent().containsKey(trackedFile);
                boolean inWD = filesInWD.contains(trackedFile);
                if (!lastExists2 && inWD) {
                    Utils.restrictedDelete(trackedFile);
                }
            }

            for (String lastTracked : lastCommit.getContent().keySet()) {
                String fileId = lastCommit.getContent().get(lastTracked);
                Blob.blobCheckoutHelper(fileId, lastTracked);
            }
            currRepo.resetHelper1(lastCommit);
            currStage.clearStage();
            currRepo.addRepo();
            currStage.addStage();
        }
    }
}
