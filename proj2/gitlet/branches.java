package gitlet;
import java.io.*;
import java.util.HashMap;

public class branches {
    //Branches
    public void GitletRepo() {
        File gitlet = new File(".gitlet");
        gitlet.mkdir();
        File adding = new File(".gitlet/adding");
        adding.mkdir();
        File stage = new File(".gitlet/stage");
        stage.mkdir();
        //branches and untracked files variables must go here
    }
    public void add(String alphanumeric) { //filename variable
        File added = new File(alphanumeric);
        if (!added.exists()) { //https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java
            throw new IllegalArgumentException("File does not exist.");
        } else if (args.length != 1) {
            throw new IllegalArgumentException("Incorrect operands.");
        } else {
            //if no parent file,

        }
    }
}
