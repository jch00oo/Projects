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

    private String id;
    private String commitMessage;
    private String timeStamp;
    private Commit parentCommit;
    private HashMap<String, Blob> content;

    /* initializing instance variables and maybe initial commit? */
    public Commit() {
        commitMessage = "initial commit";
        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");
        timeStamp = dateFormat.format(currDate);
        content = new HashMap<>();
        id = Utils.sha1(content.keySet().toArray());
        parentCommit = this;
    }

    /** id = Utils.sha1(hashKeys.toArray()) for others */

    /* returns id of commit */
    public String getId() {
        return id;
    }

    /* returns time stamp of commit */
    public String getTimeStamp() {
        return timeStamp;
    }

    /* returns commit message of commit */
    public String getCommitMessage() {
        return commitMessage;
    }

    /* returns the content/file of commit */
    public HashMap getContent() {
        return content;
    }

    /* returns the parent commit of current commit */
    public Commit getParentCommit() {
        return parentCommit;
    }
}
