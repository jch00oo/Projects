package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commit implements Serializable {

    private static String workingPath = System.getProperty("user.dir");
    static final File COMMIT_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "commits");

    private String id;
    private String commitMessage;
    private String timeStamp;
    private String parentCommit;
    /* name of file and id */
    private HashMap<String, String> content;

    /* initializing instance variables and maybe initial commit? */
    public Commit() {
        commitMessage = "initial commit";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(0);
        content = new HashMap<>();
        parentCommit = "";
        id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); //https://stackoverflow.com/questions/1090556/java-how-to-convert-hashmapstring-object-to-array
    }

    /* normal commits */
    public Commit(String message, Commit parent, HashMap<String, String> newContent) {
        commitMessage = message;
        parentCommit = parent.getId();
        content = newContent;
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(currDate);
        id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp);
    }

    String getId() {
        return id;
    }

    String getTimeStamp() {
        return timeStamp;
    }

    String getCommitMessage() {
        return commitMessage;
    }

    HashMap<String, String> getContent() {
        return content;
    }

    String getParentCommitId() {
        return parentCommit;
    }

    void addCommit() {
        File commitFile = Utils.join(COMMIT_PATH, getId());
        Utils.writeObject(commitFile, this);
    }

    public Commit getParentCommitAlt() {
        Commit parentCommitNew = null;
        if (getParentCommitId().equals("")) {
            return null;
        } else {
            File parentFile = Utils.join(COMMIT_PATH, getParentCommitId());
            parentCommitNew = Utils.readObject(parentFile, Commit.class);
        }
        return parentCommitNew;
    }

    Commit getParentCommit(Repository repo) {
        Commit currParent = null;
        HashSet<String> allCommits = repo.getAllCommitsIds();
        if (getParentCommitId().equals("")) {
            return null;
        }
        if (allCommits.contains(getParentCommitId())) {
            File parentFile = Utils.join(COMMIT_PATH, getParentCommitId());
            currParent = Utils.readObject(parentFile, Commit.class);
        }
        return currParent;
    }
}