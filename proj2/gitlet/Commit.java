package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.*;

public class Commit implements Serializable {

    private static String workingPath = System.getProperty("user.dir");
    private static final File COMMIT_PATH = Utils.join(workingPath, ".gitlet", "commits");
    String id;
    String commitMessage;
    String timeStamp;
    String parentCommit;
    /* name of file and id */
    HashMap<String, String> content;

    /* initializing instance variables and maybe initial commit? */
    public Commit() {
        commitMessage = "initial commit";
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(0);
//        timeStamp = new Date(0);
        content = new HashMap<>();
//        id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp);
        id= Utils.sha1(content.keySet().toArray()); //https://stackoverflow.com/questions/1090556/java-how-to-convert-hashmapstring-object-to-array
//        id = makeId();
        parentCommit = "";
    }

    public Commit copyCommit(Commit copiedCommit) {
        copiedCommit.id = this.id;
        copiedCommit.commitMessage = this.commitMessage;
        copiedCommit.timeStamp = this.timeStamp;
        copiedCommit.parentCommit = this.parentCommit;
        copiedCommit.content = this.content;
        return copiedCommit;
    }

    private String makeId() {
        byte[] converted = Utils.serialize(this);
        return Utils.sha1((Object) converted);
    }

    /* normal commits */
    public Commit(String commitMessage, Commit parentCommit, HashMap<String, String> content) {
        this.commitMessage = commitMessage;
        this.parentCommit = parentCommit.getId();
        this.content = content;
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        this.timeStamp = dateFormat.format(currDate);
//        this.id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); /** questionable **/
        id= Utils.sha1(content.keySet().toArray());
//        id = makeId();
    }

    public String getId() {
        return id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public HashMap<String, String> getContent() {
        return content;
    }

    public String getParentCommitId() {
        return parentCommit;
    }

    public Commit getParentCommit(Repository repo) {
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

    public void addCommit() {
        File commitFile = Utils.join(COMMIT_PATH, getId());
        Utils.writeObject(commitFile, this);
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

    public void firstLogHelper() {
        System.out.println("===");
        System.out.println("commit " + this.getId());
        System.out.println("Date: " + this.getTimeStamp());
        System.out.println(getCommitMessage());
        System.out.println();
    }
}