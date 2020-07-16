package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Gitlet.REPO_PATH;

public class Repository implements Serializable {

    /* branch name and id */
    private HashMap<String, String> branches;
    private Commit head;
    private String currBranch;
    private HashSet<String> allCommits;

    /*init new repo */
    public Repository() {
        head = new Commit();
        currBranch = "master";
        branches = new HashMap<>();
        branches.put(currBranch, head.getId());
        allCommits = new HashSet<>();
        allCommits.add(head.getId());
    }

    public Commit getHead() {
        return head;
    }

    public String getCurrBranch() {
        return currBranch;
    }

    public HashSet<String> getAllCommits() {
        return allCommits;
    }

    public HashMap<String, String> getBranches() {
        return branches;
    }

    public HashMap<String, String> getTracked() {
        return head.getContent();
    }

    /** add method to get modified files **/
    /** add method for untracked files **/

    public void newHead(Commit curr) {
        head = curr;
        branches.put(currBranch, curr.getId());
        allCommits.add(curr.getId());
    }

    public void newBranch(String name, Commit head) {
        this.head = head;
        currBranch = name;
    }

    public void addRepo() {
        File repoFile = Utils.join(REPO_PATH);
        Utils.writeObject(repoFile, this);
    }
}
