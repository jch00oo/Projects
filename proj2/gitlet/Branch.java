package gitlet;

import java.io.Serializable;

public class Branch implements Serializable {

    private String name;
    private String pointer;
    private String currBranch;

    public Branch(String name, String id) {
        this.name = name;
        pointer = id;
    }

    /* HEAD branch */
    public Branch(String branchName) {
        this.name = "HEAD";
        currBranch = branchName;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return currBranch;
    }

    

}
