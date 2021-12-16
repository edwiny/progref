# Emacs notes

## Getting started

Starting in "no window mode" (by default it starts up as a X app)

```
emacs -nw
```


Notation:

* `C-x` - means Ctrl-x
* `M-x` - means Alt-x


Quiting: 

* `C-x C-c` - effectively hold the Ctrl key one hand then x and c in succession.


Moving around:
* `C-v` and `M-v` - page forward and backwards
* `C-l`           - first press centers text on cursor, second press moves text so cursor is at top 
of screen, and third time moves the text so cursor is at the bottom.
* `C-n`, `C-p`    - move to next or previous line
* `C-b`, `C-f`    - move cursor back or forward by one character
* `M-b`, `M-f`    - move cursor back or forward by one word
* `C-a`, `C-e`    - move to beginning or end of line
* `M-a`, 'M-e`    - move to beginning or end of sentence.
* `M-<`, `M->`    - move to beginning or end of file (note that you'll need to use shift)

Prefix commands:

* These modify the main command in some way, eg. repeat
* `C-u <number>`  - repeats the command the specified number of times, e.g. `C-u 8 C-b` moves the cursor back 8 chars. Can achieve the same with holding down Alt while typig the number.


Interrupting a long running command, or discarding a half-typed command:

* `C-g`


Navigating windows:

* `C-x 1`        - close all windows except the active one.
* `C-x 2`        - split screen vertically in two
* `C-x 3`	 - split screen horizontally
* `C-x 4 C-f     - open file and display in a new window

Deleting, cutting nand pasting:

* `<DEL>`, `C-d`   - delete char before or after cursor
* `M-<DEL>`, `M-d` - delete the word before or after cursor
* `C-k`, `M-k`     - "kill" characters to the end of the line or end of the sentence.
* `C-<SPACE>, move cursor then `C-w`  - highlght and delete text.
* `C-y`            - "yanks" (pastes) the deleted text. Yanking again copies it.
* `C-y` folloed by `M-y` : cycles the text you just pasted through the kill history.
* To yank a whole line you need to `C-a C-k C-y` :(

Undo:

`C-/` or `C-_` - undoes last change


Working with files:

* `C-x C-s`        - Save a file 
* `C-x C-f`        - opens file search, will start with current path, you can edit and use bash
completion to search the path
  * You can create a empty file by typing a name of a file that doesn't exist yet
  * Each file opened remains inside emacs in its own buffer
* `M-x recover-file` - to recover file from autosaved file

Buffers:

Any text you see in emacs is inside a buffer.


* `C-x C-b`        - shows buffers
* `C-x 1`          - hide all but 1 buffer
* `C-x b`          - switch buffers. Defaults to the last one visited. Use tab completion to see open buffers.
* `C-x s`          - prmopt which buffers to save (contrast with `C-x C-s`


Extending commands:

* Cannot represent all commands with single keystrokes
* There are 2 types of extensions:
  * short mode - most frequently used commands are extended with `C-x <cmd>`
  * long mode - type command words after `M-x`


Major modes:

* Each major mode has slightly different commands mapped to keys
* Default major mode is called Fundamental
* To change mode, type `M-x <mode-name>`.

* text-mode - for natural language

Minor modes:

* Like major modes, changes commands or behaviour slightly.
* Unlike major modes, can toggle each minor mode independently of other minor modes or major modes.
* Example: `M-x auto-fill` wraps words to next line as you approach 70 char width (set with `C-x f`)


Auto-filling / wrapping:

* `C-x f <num>` - set the auto-fill width
* `M-q`         - reformat the paragraph to new auto-fill width


Searching:





## Startup Config

