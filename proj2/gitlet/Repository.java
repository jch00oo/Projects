
package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Repository implements Serializable {

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

    String getFullId(String abbrId) {
        HashSet<String> allCommitIds = getAllCommitsIds();
        for (String id: allCommitIds) {
            if (id.startsWith(abbrId)) {
                return id;
            }
        }
        return "";
    }


    static Repository readRepo() {
        File repoFile = Utils.join(REPO_PATH);
        Repository repo = Utils.readObject(repoFile, Repository.class);
        return repo;
    }

    public void addRepo() {
        File repoFile = Utils.join(REPO_PATH);
        Utils.writeObject(repoFile, this);
    }

    void updateHead(Commit latest) {
        head = latest;
        branches.put(currBranch, latest.getId());
        allTheCommits.add(latest.getId());
    }

    void newBranch(String name, Commit head) {
        this.head = head;
        currBranch = name;
    }

    void resetHead(Commit previous) {
        head = previous;
        String prevID = previous.getId();
        branches.put(getCurrBranch(), prevID);
    }

    void fetchCommit(String id) {
        allTheCommits.add(id);
    }

    /* branch name and id */
    private HashMap<String, String> branches;
    private Commit head;
    private String currBranch;
    //    HashMap<String, Commit> allCommits;
    ArrayList<Commit> allCommitscurrbranch;
    private HashSet<String> allTheCommits;

    private HashMap<String, Commit> trackCommits;
    private static String workingPath = System.getProperty("user.dir");
    static final File GEN_PATH = Utils.join(workingPath);

    static final File REPO_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "repo");

}