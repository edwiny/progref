# Git Notes


## Undoing things

### Discard local changes for single file

```
git checkout <filename>
```

### Discard local changes for all files

```
git reset --hard
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
