# **Gitlet**
## Classes and Data Structures 
### a. Main implement serializable too 
If no arguments entered, print out exception error 
Create big switch blocks- init, add, commit, log - and create the others after first checkpoint. Default block is error staetment- no command exists.
* private HashMap<String,Commit> commitHistory; //stores all the commits, id as key and Commit as value
* private HashMap <String, String> tracked;// stores all tracked files, id as key and blob as value
* private ArrayList <String> removedFiles; //stores fileName of removed files in an array list
* private ArrayList <String> untrackedFiles; //stores fileName of untracked files
* private Commit recentCommit;
* Create the formulas for the switch statements- init, add, log, commit
#### Init/add/log/commit
##### add takes in a string
*Makes a new file, if this new file does not eist, throw illegal argumetn exception file DNE
* if args.length is not enough, throw Incorrect operands 
* else add the files *** 
##### log doesn't take in anything 
* makes a new file under adding directory 
* The history of this file (stored in array) will be listed 
* The history will be printed in a for loop using .getName 
* Note: this method can be improved on because spec says not necessarily recommended 
##### commit
* add to commitHistory, update Head branch, clear/make new stage 
* if null commit mesage, then null and throw error message to enter a commit message
* if no changes made, throw message. 
* otherwise commit branch and read file from branch class
##### init 
* Create a new file .gitlet
* If file exists, throw error message already exists 
* Otherwise make a directory and commit it there. Direct to staging class to stage. 
### b. Commit implement serializable 
Initial commit; get local date/time/content formatting for log. 
### c. Branch
String name, pointer. current Branch. Set current branch to be head, make get methods for names, branches
### d. Blob
Create a blob class to make a blob object. Begin tracking the object if it exists, pring error statement if it does not exist. Set current file to be a new file- if this file exists, read contents and track file
Change file to alphanumeric code using SHA1. 
### e. Stage 
Create a stage faster and more efficiently with hash maps. Easier to clear the stage and implement. 
Public stage, add files, delete files, get files 
