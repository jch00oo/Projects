package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Gitlet.REPO_PATH;

public class Repository implements Serializable {

    /* branch name and id */
    HashMap<String, String> branches;
    Commit head;
    String currBranch;
    //    HashMap<String, Commit> allCommits;
    ArrayList<Commit> allCommitscurrbranch;
    HashSet<String> allTheCommits;

    private HashMap<String, Commit> trackCommits;
    private static String workingPath = System.getProperty("user.dir");
    static final File GEN_PATH = Utils.join(workingPath);

    /*init new repo */
    public Repository() {
        head = new Commit();
        currBranch = "master";
        branches = new HashMap<>();
        branches.put(currBranch, head.getId());
        allTheCommits = new HashSet<>();
        allTheCommits.add(head.getId());
//        allCommits = new HashMap<>();
//        allCommits.put(head.getId(), head); //dubious code, but I needed to get a string,commit Map
    }

    public Commit getHead() {
        return head;
    }

    public String getCurrBranch() {
        return currBranch;
    }

    public ArrayList<Commit> getcurrbranchcommit() {
        return allCommitscurrbranch;
    }

//    public HashMap<String, Commit> getAllCommits() {
//        return allCommits;
//    }

    public HashSet<String> getAllCommitsIds() { return allTheCommits; }

    public HashMap<String, String> getBranches() {
        return branches;
    }

    public HashMap<String, String> getTracked() {
        return head.getContent();
    }

    void repoResetHelper(Commit last) {
        head = last;
        String lastId = last.getId();
        branches.put(getCurrBranch(), lastId);
    }

    /**
     * add method to get modified files, deleted files, untracked files
     **/

//    public void newHMHead(Commit curr) {
//        head = curr;
//        branches.put(currBranch, curr.getId());
//        allCommits.put(head.getId(), head);
//        //allCommits.add(curr.getId()); I had to change to Hashmap
//    }

    public void newHead(Commit curr) {
        head = curr;
        branches.put(currBranch, curr.getId());
        allTheCommits.add(curr.getId());
    }

    public void newBranch(String name, Commit head) {
        this.head = head;
        currBranch = name;
    }

    public void addRepo() {
        File repoFile = Utils.join(REPO_PATH);
        Utils.writeObject(repoFile, this);
    }

//    /**
//     * edit this -- take in all staged files to add to next commit
//     **/
//    public ArrayList<String> getModified(HashMap<String, String> stagedFiles) {
//        /* get names of all files in current working directory */
//        List<String> allStaged = Utils.plainFilenamesIn(GEN_PATH);
//        boolean isStaged, isTracked;
//        ArrayList<String> modified = new ArrayList<>();
//
//        /* loop through file names */
//        for (String file : allStaged) {
//            isStaged = stagedFiles.containsKey(file);
//            isTracked = getTracked().containsKey(file);
//
//            if (!isStaged && isTracked) {
//                String wd = new Blob(file).getBlobId();
//                if (!getTracked().get(file).equals(wd)) {
//                    modified.add(file + " (modified)");
//                }
//            } else if (isStaged) {
//                String wd = new Blob(file).getBlobId();
//                if (!stagedFiles.get(file).equals(wd)) {
//                    modified.add(file + " (modified)");
//                }
//            }
//        }
//        return modified;
//    }

//    /**
//     * edit this
//     **/
//    ArrayList<String> getDeletedFiles(HashMap<String, String> allStaged, HashMap<String, String> allRemoved) {
//        List<String> allPresent = Utils.plainFilenamesIn(GEN_PATH);
//        ArrayList<String> deleted = new ArrayList<>();
//        for (String staged : allStaged.keySet()) {
//            if (!allPresent.contains(staged)) {
//                deleted.add(staged + " (deleted)");
//            }
//        }
//        for (String tracked : getTracked().keySet()) {
//            if (!allRemoved.containsKey(tracked)
//                    && !allPresent.contains(tracked)) {
//                deleted.add(tracked + " (deleted)");
//            }
//        }
//        return deleted;
//    }

    String getFullId(String abbrId) {
        HashSet<String> allCommitIds = getAllCommitsIds();
        for (String id: allCommitIds) {
            if (id.startsWith(abbrId)) {
                return id;
            }
        }
        return "";
    }

    /**
     * edit this
     **/
    public ArrayList<String> getUntracked(HashMap<String, String> stagedFiles) {
        List<String> allFiles = Utils.plainFilenamesIn(GEN_PATH);
        ArrayList<String> untrackedFiles = new ArrayList<>();
        for (String file : allFiles) {
            if (!(stagedFiles.containsKey(file)) && !(getTracked().containsKey(file))) {
                untrackedFiles.add(file);
            }
        }
        Collections.sort(untrackedFiles);
        return untrackedFiles;
    }
}