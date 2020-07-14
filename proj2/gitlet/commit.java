package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class Commit implements Serializable {
    String id;
    String commitMessage;
    String timeStamp;
    Commit parentCommit;
    HashMap<String, Blob> content;

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

    public static void commit (String commitMessage) {
        return;
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

    public HashMap getContent() {
        return content;
    }

    public Commit getParentCommit() {
        return parentCommit;
    }
}