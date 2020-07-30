package gitlet;

import java.util.*;
import java.io.*;
import java.util.Formatter;
import java.util.Arrays;

public class Gitlet {

<<<<<<< HEAD
=======
    //@source https://www.atlassian.com/git/tutorials/setting-up-a-repository
    // ^ for information on how real git works
    //@source http://szhang7.github.io/java/2013/08/29/java-handbook/
    //@source https://stackoverflow.com/questions/27409718/java-reading-multiple-objects-from-a-file-as-they-were-in-an-array
    // ^ both for using system.getproperty("user.dir"), utils.join, and utils.readobject to read & obtain object from files


    //make and declare the directories
    private static String cd_gitlet = ".gitlet";
    private static String cd_stage = ".gitlet/.stage";
    private static String commitPath = ".gitlet/.commit";
    private static String workingPath = System.getProperty("user.dir");
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b

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
            Repository newRepo = new Repository();
            Stage newStage = new Stage();
            File repo = Utils.join(Repository.REPO_PATH);
            Utils.writeObject(repo, newRepo);
            File stage = Utils.join(Stage.STAGE_PATH);
            Utils.writeObject(stage, newStage);
            File blobs = Utils.join(Blob.BLOB_PATH);
            blobs.mkdir();
            File commits = Utils.join(Commit.COMMIT_PATH);
            commits.mkdir();
            newRepo.getHead().addCommit();
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
            Repository currRepo = Repository.readRepo();
            HashMap<String, String> headContent = currRepo.getTracked();
            Stage currStage = Stage.readStage();

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
<<<<<<< HEAD
    public void commit(String commitMessage) throws GitletException {
        Repository currRepo = Repository.readRepo();
=======
    public void commit(String commitMessage) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
        Commit head = currRepo.getHead();
        HashMap<String, String> headBlob = currRepo.getTracked();

        Stage currStage = Stage.readStage();
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
            newBlob.put(removedFileName, stagedToAdd.get(removedFileName));
        }
        currStage.clearStage();
        currStage.addStage();

        Commit newCommit = new Commit(commitMessage, head, newBlob);
        newCommit.addCommit();

        currRepo.commitHelper1(newCommit);
        currRepo.addRepo();
    }

    /* Prints out information about commits in the current branch in given format.
     */
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
<<<<<<< HEAD

=======
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
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

    /* @param branch name
    * Check if input branch exists or is the current branch; if not either, remove the branch.
     */
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

<<<<<<< HEAD
    public static void global(){
=======
    /* Similar to log, except printing out every single commit made.
     */
    public static void global(){ //doesn't have to be in order, screw hashmap
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
        File allcommitsfolder = Utils.join(Commit.COMMIT_PATH);
        File [] eacommit= allcommitsfolder.listFiles(); //https://www.geeksforgeeks.org/file-listfiles-method-in-java-with-examples/
        for (File i : eacommit){
            logHelper2(Utils.readObject(i,Commit.class));
        }
    }

    /* @param commit message
    * Prints out the commit id(s) of the commit(s) with that corresponding commit message.
     */
    public static void find(String message){
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        Commit found;
<<<<<<< HEAD
        Formatter uh = new Formatter();
=======

>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
        boolean exists = false;

        for (String commitId: currRepo.getAllCommitsIds()) {
            File allcommitsfolder = Utils.join(Commit.COMMIT_PATH, commitId);
            found = Utils.readObject(allcommitsfolder, Commit.class);
            if (found.getCommitMessage().equals(message)) {
                exists = true;
                System.out.println(found.getId());
            }
        }
<<<<<<< HEAD
=======

>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
        if (exists == false){
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
    }

    /* Case 1:
     * @param file name
     * Same thing as checkout 2, except take in file name. Just find id using the head commit
     * and use checkout 2.
     */
    public void checkout1 (String fileName) {
        File repoFile = Utils.join(Repository.REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);
        String headCommitId = currRepo.getHead().getId();
        checkout2(headCommitId, fileName);
    }

    /* Case 2:
     * @param commit id and file name
     * Check if commit and file exist; if they do, then take file version with given
     * id, and overwrite file version in working directory if it exists with the
     * given version. Do not stage.
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
     * Check if it's current branch and if working file is untracked; if not, take files in head commit
     * of given branch and overwrite file versions in current working directory with given
     * version.
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

    /* @param commit id
    * Checkout file tracked by given commit, remove tracked file not in that given commit,
    * and move head pointer. Pretty similar to checkout. Need to check abbreviated id.
     */
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
<<<<<<< HEAD
                if (lastCommit.getContent().containsKey(fileName) && !currRepo.getTracked().containsKey(fileName)) {
                    throw Utils.error("There is an untracked file in the way;" + "delete it, or add and commit it first.");
=======
                boolean tracked = currRepo.getTracked().containsKey(fileName);
                boolean lastExists = lastCommit.getContent().containsKey(fileName);
                if (lastExists && !tracked) {
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
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
<<<<<<< HEAD
=======

    /* @param branch name
    * Merge files from given branch into current branch. Started with implementing the failure cases.
     */
    public void merge(String branchName) {
        File repoFile = Utils.join(REPO_PATH);
        Repository currRepo = Utils.readObject(repoFile, Repository.class);

        File stageFile = Utils.join(STAGE_PATH);
        Stage currStage = Utils.readObject(stageFile, Stage.class);

        /* things to check */
        boolean exists = currRepo.getBranches().containsKey(branchName);
        boolean isCurrent = currRepo.getCurrBranch().equals(branchName);
        boolean uncommittedChanges = !currStage.getStagedToAdd().isEmpty() || !currStage.getStagedToRemove().isEmpty();
        boolean isAncestor;
        boolean fastForwarded;

        if (!exists) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (isCurrent) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
        if (uncommittedChanges) {
            System.out.println("You have uncommitted changes.");
        }

        String newId = currRepo.getBranches().get(branchName);
        String currId = currRepo.getHead().getId();
    }
>>>>>>> 65f8bbaa54ad6c929b16ed3a3af7b4455e0f161b
}
