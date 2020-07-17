package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import static gitlet.Gitlet.REPO_PATH;

public class Repository implements Serializable {

    /* branch name and id */
    HashMap<String, String> branches;
    Commit head;
    String currBranch;
    HashSet<String> allCommits;

    private HashMap<String, Commit> trackCommits;

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

//    public void log() {
//        Commit pointer = new Commit();
//        head.copyCommit(pointer);
//        while (pointer != null) {
//            logHelper(pointer);
//            pointer = pointer.getParentCommit();
//        }
//    }
//
//    public void logHelper(Commit curr) {
//        System.out.println("===");
//        System.out.println("commit " + curr.getId());
//        System.out.println("Date: " + curr.getTimeStamp());
//        System.out.println(curr.getCommitMessage());
//        System.out.println();
//    }

}