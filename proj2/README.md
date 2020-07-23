# **Gitlet**
## Classes and Methods
### a. Gitlet 

* File REPO_PATH: create new Repo file in Repo folder
* File STAGE_PATH: create new Stage file in Stage folder
* File BLOB_PATH: create new Blob file in Blob folder
* File COMMIT_PATH: create new Commit in Commit folder

##### init()
* Create a new file .gitlet
* If file exists, throw error message already exists 
* Otherwise make a directory and commit it there. Direct to staging class to stage. 
##### commit(String commitMessage)
* add to commitHistory, update Head branch, clear/make new stage 
* if null commit mesage, then null and throw error message to enter a commit message
* if no changes made, throw message. 
* otherwise commit branch and read file from branch class
##### add(String fileName)
* Makes a new file, if this new file does not eist, throw illegal argumetn exception file DNE
* if args.length is not enough, throw Incorrect operands 
* else add the files *** 
##### log()
* makes a new file under adding directory 
* The history of this file (stored in array) will be listed 
* The history will be printed in a for loop using .getName 
* Note: this method can be improved on because spec says not necessarily recommended 
##### logHelper(Commit curr)
* helper method for log() that tracks each commit
##### branch(String branchName)
* create a new branch pointer with the input branch name
* add branch name and head commit id to hash map of all branches
##### rm(String fileName)
* if file is tracked by the current head commit and staged for addition, unstage and delete from working directory
* if not tracked, unstage but do not delete from working directory
##### rmBranch(String branchName)
* remove branch with given branch name from hash map keeping track of all branches
##### status()
* print out branches, staged files, removed files, modified files, and untracked files
##### global()
* log() but with every single commit made so far
##### find (String[] message)
* print out commit ids of all commits with the input commit message
##### checkout1 (String fileName)
* takes the version of the file in the head commit and puts it in the working directory
* overwrite the version of the file that’s already there if there is one
##### checkout2 (String id, String fileName)
* takes the version of the file with the given id and puts it in the working directory
* overwrite the version of the file that’s already there if there is one
##### checkout3 (String branchName)
* Takes all files in the commit at the head of the given branch, and puts them in the working directory, overwriting the versions of the files that are already there if they exist. Also, at the end of this command, the given branch will now be considered the current branch (HEAD). Any files that are tracked in the current branch but are not present in the checked-out branch are deleted. The staging area is cleared, unless the checked-out branch is the current branch
##### reset(String fileLetter)
* check out all files tracked by given commit
  
### b. Blob
* String fileName
* String id
* byte[] fileContents
* boolean tracked

##### getContents()
##### getFileName()
##### getBlobId()
##### trackBlob()

### c. Repository

* HashMap<String, String> branches: tracks every branch by branch name and commit id
* Commit head: pointer for the head commit
* String currBranch: name of current branch
* HashSet<String> allCommits: stores every commit by commit id

##### get Functions:

##### getHead()
##### getCurrBranch()
##### getAllCommits()
##### getBranches()
##### getTracked()

##### newHead()
* changes the head commit pointer
##### newBranch()
* changes/add branch by changing head commit pointer and changing name
##### addRepo()
* write Repo object into file
##### getFullId(String abbrId)
* return full commit id when abbreviated commit id is input
##### getUntracked(HashMap<String, String> stagedFiles)
* return list of all currently untracked files


### d. Commit

* String id: hashID for each commit
* String commitMessage: input commit message
* String timeStamp: local time stamp
* String parentCommit: previous commit to current head commit, DNE if initial commit

##### get Functions:

##### getId()
##### getTimeStamp()
##### getCommitMessage()
##### getContent()
##### getPArentCommitId()
##### getParentCommit()

##### addCommit()
* call after each new commit
* save Commit object into file
* commit id as file name

### e. Stage 

* HashMap<String, String> stagedToAdd: store all files staged to be added
* HashMap<String, String> stagedToRemove store all files staged to be removed

##### get Functions:

##### getSTagedToAdd()
##### getStagedToRemove()

##### stageToAdd(String fileName, String id)
* add Blob to be added into stagedToAdd HashMap
##### stageToRemove(String fileName, String id)
* add Blob to be removed into stagedToAdd HashMap
##### clearStage()
* clears HashMaps stagedToAdd and stagedToRemove
##### addStage()
* write Stage object into file

## Persistence

### things that need to be tracked:
##### all commits so far
* HashMap allCommits in Repository.java
##### current commit/head/master
* Commit head in Repository.java
##### current branch
* String currBranch in Repository.java
##### modified vs staged files
* HashMap stagedToRemove & stagedToAdd in Stage.java
