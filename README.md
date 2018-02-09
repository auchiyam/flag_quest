# Flag Quest

## Using GitHub on Windows 10

#### 1. Create an account
In order to use GitHub, you must first create an account to use.  Creating an account in GitHub is self explanatory.  <br />
Simply fill out the form located in the home page of https://github.com and follow the instruction.  <br />

#### 2.  Install git
You must then install git on your computer.  Go to https://git-scm.com/downloads and download the Windows version.  Run the program and install it using default options.  <br />

#### 3.  Cloning a repository
Cloning a repository allows you to download the source code on the repository to work with.  <br />
Open git-bash.exe or Git Bash to open the command prompt designed for git.  <br />
Type in `cd <directory>`, where the `<directory>` is the folder you would like the project to be, such as "D:/Programming/Software Engineer".   You'll need to do this every time you close the Git Bashs.  <br />
Type `git clone https://github.com/auchiyam/flag_quest.git` to clone the flag quest repository to that directory.  <br />
It should make a folder named "flag_quest" with this README.md inside.  <br />

#### 4.  Creating a new branch
Branch is a different version of code you can use to update.  Creating a new branch for yourself is important, because pushing to master branch directly every time may introduce conflict (such as functions having same name but with different outcome), causing some bugs.  <br />
To create a new branch, simply type `git checkout -b <branch_name>` where `<branch_name>` is the branch name you would like to call it.  After the branch has been created, you can switch between branches by using `git checkout <branch_name>`.  <br />

#### 5.  Uploading changes to a repository
In the Git Bash command prompt, type `git add <file_name>`, where `<file_name>` is the file you would like to update.  If you want to add every file you've changed, use `git add .` instead.  <br />
Type `git commit -m <message>` where `<message>` is the detail of the change to schedule a change.  <br />
If this is the first time pushing the change, type `git remote add origin https://github.com/auchiyam/flag_quest.git` to tell git that's where you want to upload the change to.  <br />
Finally, type `git push -u origin <branch>` to upload the change.  <br />

#### 6.  Updating the project folder with newest version
In order to update your local copy of the project with the newest one on the repository, type `git pull` in the branch you want to update.  <br />
Keep in mind that you must commit and push every local changes you have before you can pull the repository.  <br />

#### 7.  Merging a branch with the master
Since master must be the complete version of the program, you'll need to merge the branch you are working with to master in order for your work to be applied.  <br />
Before merging, make sure the master branch is up to date by typing `git checkout master` then `git pull`.  <br />
Type `git merge <branch_name>` while in the master branch to merge the branch.  <br />
Finally, you can delete the branch you've merged locally by typing `git branch -d <branch_name>`  <br />
Then, type `git push origin --delete <branch_name>` to delete the branch from the repository.