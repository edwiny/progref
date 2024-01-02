# Git Notes

## Git model


 * workspace - is the directory tree of (source) files that you see and edit.
 * index - a single, large, binary file in <baseOfRepo>/.git/index, which lists all files in the current branch, their sha1 checksums, time stamps and the file name -- it is not another directory with a copy of files in it.
 * local repository - is a hidden directory (.git) including an objects directory containing all versions of every file in the repo (local branches and copies of remote branches) as a compressed "blob" file.



Moving changes from left to right:

* When you `git add` a file it goes to the index.
    * When you `git commit` it goes to the local repo
        * When you `git push` it goes to the remote repo



Moving changes from right to left:

* When you `git fetch` it pulls changes from the remote repo to the local repo
* `git pull` or `git rebase` brings from the remote repo to local repo, local index and local working tree.
* `git checkout HEAD` moves changes from local repo to index and working tree
* `git checkout` moves changes from index to working tree


`HEAD` is an alias for a branch (and implicitly, the last commit on that branch)

"Detached" HEAD is when HEAD points to an actual commit, not a branch.




## Undoing things

### Discard local changes for single file

```
git checkout <filename>
```

### Discard local changes for all files

```
git reset --hard
```


There's 3 variations:

```
    --soft: uncommit changes, changes are left staged (index).
    --mixed (default): uncommit + unstage changes, changes are left in working tree.
    --hard: uncommit + unstage + delete changes, nothing left.
```

### Amend last commit message prior to pushing to remove

```
git commit --amend
```

### Revert a merged PR


```
git revert -m 1 <merge-commit-hash>
```

The `-m 1` is selecting the main branch as the parent.

### Show default branch on remote repo

```
git rev-parse --abbrev-ref origin/HEAD
```

## Review PR changes


Find the commit where branch diverged from master or where master was merged into the branch, or the branch rebased onto master.

Typically look for merge commits (commits with two parents)
TODO: try using git log --oneline --graph


Create a `review` branch from that commit:

```
git checkout -b review/branchname 8c0718f68
```


### Method 1

Do a `no-cmmit` merge from the reviewing branch to the `review` branch:

```
git merge --no-commit --no-ff GAMMA-2166-Config-server-table-and-AP
```


### Method 2

* create a copy of the feature branch
* use `git reset --soft <common ancestor commit>`




### Explanation

 both methods will roll back commits made in the feature branch into the staging index. If you do a 'git status' at this point, it will appear as if you had made the changes in your local working directory and then staged the changes in git.

IDEs like Intellij will pick up on this status and will give you visual cues about which files were changed (in the Project tool window, you can select 'All changed files' to only show you the files that have changed). There's also the commit preview functionality that can give you side-by-side diff views.

### Problems

It won't work as expected if branch being reviewed has had a merge commit from master.

TODO: explore how git rebase can be helpful here.




## Branching

See visual representation of commits across branches:

```
 git log --oneline --graph 
```

### rebase

Replays the commits in the current branch onto the tip of the branch being rebased onto, creating new commits in the process.

Rebasing re-writes the project history by creating brand new commits for each commit in the original branch (typically the feature branch that you're rebasing)


When to use:
* when you need a linear history

When to avoid:
* shared or public branches
* you want to see when upstream changes were incorporated into the feature.


**NOTE!** The **golden rule** of git rebase is to never use it on public branches.


### merge

Git creates a new commit that has two parent commits—one from the current branch and one from the branch being merged

When to use:
* in most cases

When to avoid:
* you want a cleaner history on your feature branch
* you don't want extraneous merge commits in your history






### Diff between rebase and merge

* rebase copies commits (they will appear as new commits)
* merge does not copy, it merges existing commits
