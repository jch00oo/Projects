package gitlet;

import java.io.File;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

public class Commit implements Serializable {

    /* initializing instance variables and maybe initial commit? */
    public Commit() {
        commitMessage = "initial commit";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(0);
        content = new HashMap<>();
        parentCommit = "";
        id= Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); //https://stackoverflow.com/questions/1090556/java-how-to-convert-hashmapstring-object-to-array
    }

    /* normal commits */
    public Commit(String message, Commit parent, HashMap<String, String> newContent) {
        commitMessage = message;
        parentCommit = parent.getId();
        content = newContent;
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(currDate);
//        this.id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); /** questionable **/
        id= Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp);
//        id = makeId();
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
//        Commit parentCommitNew = null;
//        if (getParentCommitId().equals("")) {
//            return null;
//        }
//        else {
//            File parentFile = Utils.join(COMMIT_PATH, getParentCommitId());
//            parentCommitNew = Utils.readObject(parentFile, Commit.class);
//        }
//        return parentCommitNew;
    }

//    /* Prints out commit hashID, date, and commit message in order
//     * from the head commit to initial commit.
//     */
//    public void log() {
//        Commit pointer = this;
//        while (! pointer.getParentCommitId().equals("")) {
//            pointer.firstLogHelper();
//            pointer = getParentCommit();
//        }
//        firstLogHelper();
//        System.exit(0);
//    }

//    public void firstLogHelper() {
//        System.out.println("===");
//        System.out.println("commit " + this.getId());
//        System.out.println("Date: " + this.getTimeStamp());
//        System.out.println(getCommitMessage());
//        System.out.println();
//    }

    /** Return specific format of commit information. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        Format dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        out.format("===\n");
        out.format("commit %s\n", getId());
        out.format("Date: %s\n", dateFormat.format(getTimeStamp()));
        out.format("%s\n", getCommitMessage());
        return out.toString();
    }

    private static String workingPath = System.getProperty("user.dir");
    static final File COMMIT_PATH = Utils.join(System.getProperty("user.dir"), ".gitlet", "commits");

    private String id;
    private String commitMessage;
    private String timeStamp;
    private String parentCommit;
    /* name of file and id */
    private HashMap<String, String> content;
}