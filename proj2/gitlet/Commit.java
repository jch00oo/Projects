package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class Commit implements Serializable {

    private static String workingPath = System.getProperty("user.dir");
    private static final File COMMIT_PATH = Utils.join(workingPath, ".gitlet", "commits");
    String id;
    String commitMessage;
    String timeStamp;
    String parentCommit; /** String? **/
    /* name of file and id */
    HashMap<String, String> content;

    /* initializing instance variables and maybe initial commit? */
    public Commit() {
        commitMessage = "initial commit";
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(0);
        content = new HashMap<>();
        id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); /** questionable **/
        parentCommit = "";
    }

    /* normal commits */
    public Commit(String commitMessage, Commit parentCommit, HashMap<String, String> content) {
        this.commitMessage = commitMessage;
        this.parentCommit = parentCommit.getId();
        this.content = content;
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        this.timeStamp = dateFormat.format(currDate);
        this.id = Utils.sha1(content.toString(), parentCommit, commitMessage, timeStamp); /** questionable **/
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

    public String getParentCommit() {
        return parentCommit;
    }

    public void addCommit() {
        File commitFile = Utils.join(COMMIT_PATH, getId());
        Utils.writeObject(commitFile, this);
    }
}
