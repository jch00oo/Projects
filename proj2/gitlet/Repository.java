package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Repository implements Serializable {

    /* branch name and id */
    private HashMap<String, String> branches;
    private Commit head;
    private String currBranch;
    HashMap<String, Commit> allCommits;
    ArrayList<Commit> allCommitscurrbranch;
    private HashSet<String> allTheCommits;

    private HashMap<String, Commit> trackCommits;
    private static String workingPath = System.getProperty("user.dir");
    static final File GEN_PATH = Utils.join(workingPath);

    static final File REPO_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "repo");

    /*init new repo */
    public Repository() {
        head = new Commit();
        currBranch = "master";
        branches = new HashMap<>();
        branches.put(currBranch, head.getId());
        allTheCommits = new HashSet<>();
        allTheCommits.add(head.getId());
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


    public HashSet<String> getAllCommitsIds() { return allTheCommits; }

    public HashMap<String, String> getBranches() {
        return branches;
    }

    public HashMap<String, String> getTracked() {
        return head.getContent();
    }

    String getFullId(String abbrId) {
        HashSet<String> allCommitIds = getAllCommitsIds();
        for (String id: allCommitIds) {
            if (id.startsWith(abbrId)) {
                return id;
            }
        }
        return "";
    }

    // Writes object into repo file and adds it.
    //@source https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
    public void addRepo() {
        File repoFile = Utils.join(REPO_PATH);
        Utils.writeObject(repoFile, this);
    }

    // @param: newly made commit
    // Change the head commit pointer to the newly made commit, and add it to the list of commits.
    void commitHelper1(Commit newCommit) {
        head = newCommit;
        branches.put(currBranch, newCommit.getId());
        allTheCommits.add(newCommit.getId());
    }

    // @param: name of branch, head Commit
    // Change head commit pointer and branch name when the third case of checkout is run.
    void checkout3Helper(String name, Commit head) {
        this.head = head;
        currBranch = name;
    }

    // @param: previous commit
    // Change head commit pointer, and use commit id of input previous commit.
    void resetHelper1(Commit previous) {
        head = previous;
        String prevID = previous.getId();
        branches.put(getCurrBranch(), prevID);
    }
}
