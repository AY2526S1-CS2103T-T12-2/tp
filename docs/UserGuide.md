---
layout: page
title: User Guide
---

Are you an NUS Teaching Assistant (TA) in the School of Computing (SoC), struggling to keep track of your classes?
If you're looking for a centralised and user-friendly system to _keep TAbs on_ your TA duties, look no further: **TAbs** is here!
<br><br>
With so many students in so many tutorials, labs and recitations, it's easy to lose track of your students' attendance.
**TAbs** is a desktop app created just for you to manage your classes—while it's optimised for use via a Command Line Interface (CLI),
it still has the benefits of a Graphical User Interface (GUI).

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your computer.<br>
   **Mac users:** Ensure you have the precise JDK version
   prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file
   from [here](https://github.com/AY2526S1-CS2103T-T12-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for TAbs.

4. Open a command terminal, `cd` into the folder you put the `.jar` file in, and run the following command to start the application:
   ```
    java -jar TAbs.jar
   ```
   A GUI similar to the one below should appear in a few seconds. Note that the app may contain sample data.<br><br>
   ![Ui](images/Ui.png)<br><br>

5. Type your commands in the command box and press Enter to execute them, e.g., typing **`help`** and
   pressing Enter will open the help window.<br><br>
   Some example commands you can try:

    * `list`: Lists all tutorials.

    * `add_tutorial t/T123 m/CS2103T d/2025-01-01`: Adds a tutorial with ID `T123` for the module
      `CS2103T`.

    * `list_students t/T1`: Displays a list of all the students enrolled in the tutorial with ID
      `T1`.

    * `delete_tutorial t/T1`: Deletes the tutorial with ID `T1`.

    * `clear`: Deletes all tutorials.

    * `exit`: Exits the app.
   <br><br>

6. Refer to the [features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  (e.g., in `add_tutorial t/TUTORIAL_ID`, `TUTORIAL_ID` is a parameter which can be used as
  `add_tutorial t/T123`.)

* Items in square brackets are optional.<br>
  (e.g., `t/TUTORIAL_ID [id/STUDENT]…` can be used as `t/T123 id/A1234567X` or as `t/T123`.)

* Items with `…` after them can be used multiple times, including zero times.<br>
  (e.g., `t/TUTORIAL_ID [id/STUDENT]…` can be used as `t/T123` (i.e., 0 times), `t/T123 id/A1234567X`,
  `t/T123 id/A1234567X id/A2234567Y`, etc.)

* Parameters can be in any order.<br>
  (e.g., if the command specifies `t/TUTORIAL_ID m/MODULE_CODE`, `m/MODULE_CODE t/TUTORIAL_ID` is also acceptable.)

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`
  and `clear`) will be ignored.<br>
  (e.g., if the command specifies `help 123`, it will be interpreted as `help`.)

* If you are using a PDF version of this document, be careful when copying and pasting commands that
  span multiple lines as space characters surrounding line-breaks may be omitted when copied over to
  the application.

</div>

### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Listing all tutorials: `list`

Shows a list of all tutorials in TAbs.

Format: `list`

### Adding a tutorial: `add_tutorial`

Adds a tutorial to TAbs.

Format: `add_tutorial t/TUTORIAL_ID m/MODULE_CODE d/DATE [id/STUDENT_ID]…`

* Adds a tutorial with the specified tutorial ID, module code and date.
* `TUTORIAL_ID` must **start with a single alphabet followed by 1 to 8 digits** (e.g., `A1`, `B123`, `C12345678`).
* `TUTORIAL_ID` is case-insensitive (i.e., `t123` will be stored as `T123`).
* `MODULE_CODE` must **start with 2-4 uppercase letters followed by 4 digits and an optional uppercase letter** (e.g., `CS2103T`).
* `DATE` should be in **YYYY-MM-DD** format.
* Each `STUDENT_ID` must follow the format `AXXXXXXX&`, where:
    * The first letter (`A`) is uppercase,
    * Followed by 7 digits (`XXXXXXX`),
    * Ending with an uppercase letter (`&`).
* Multiple students can be added to the same tutorial.

Examples:

* `add_tutorial t/C456 m/CS2101 d/2025-01-01`
* `add_tutorial t/T123 m/CS2103T d/2025-01-01 id/A1231231Y id/A3213213Y`

### Deleting a tutorial: `delete_tutorial`

Deletes the specified tutorial from TAbs.

Format: `delete_tutorial t/TUTORIAL_ID`

* Deletes the tutorial with the specified `TUTORIAL_ID`.
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will delete the same tutorial).

Examples:

* `delete_tutorial t/T2` deletes the tutorial with ID `T2` in TAbs.

### Copying a tutorial: `copy_tutorial`

Creates a copy of an existing tutorial with a new tutorial ID and date.

Format: `copy_tutorial t/NEW_TUTORIAL_ID from/EXISTING_TUTORIAL_ID d/DATE`

* Copies an existing tutorial identified by the existing tutorial's ID and creates a new tutorial with a new ID and the specified date.
* `NEW_TUTORIAL_ID` must **start with a single alphabet followed by 1 to 8 digits** (e.g., `A1`, `B123`, `C12345678`).
* `NEW_TUTORIAL_ID` must not already exist in TAbs.
* `NEW_TUTORIAL_ID` and `EXISTING_TUTORIAL_ID` are case-insensitive (e.g., both `t/t123` and `t/T123` will be stored as/refer to the same tutorial).
* The existing tutorial ID must exist in TAbs.
* All students from the existing tutorial will be copied to the new tutorial with their attendance reset to being unmarked.
* The module code will be copied from the existing tutorial.
* `DATE` should be in **YYYY-MM-DD** format.

Examples:

* `copy_tutorial t/C2 from/C1 d/2025-04-10` copies the tutorial `C1` to create a new tutorial `C2` with
  the date `2025-04-10`.
* `copy_tutorial t/T202 from/T201 d/2025-05-15` copies the tutorial `T201` to create a new tutorial `T202`
  with the date `2025-05-15`.

Below is an example of the copy command being used:

![copy command](images/copyCommand.png)

### Editing a tutorial: `edit_tutorial`

Edits the details of an existing tutorial in TAbs.

Format: `edit_tutorial from/EXISTING_TUTORIAL_ID [t/NEW_TUTORIAL_ID] [m/NEW_MODULE_CODE] [d/NEW_DATE]`

* Updates the details of the tutorial identified by the tutorial ID specified after `from/`.
  * Attempting to edit a non-existent tutorial will result in an error: `Tutorial ID not found.`
* You may change **one or more** of the following:
    * Tutorial ID (`t/`)
    * Module code (`m/`)
    * Date (`d/`)
* `EXISTING_TUTORIAL_ID` and `NEW_TUTORIAL_ID` are case-insensitive (e.g., both `t/t123` and `t/T123` refer to the same tutorial).
* At least one editable field (`t/`, `m/`, or `d/`) must be specified.
* Editing student lists (i.e., using `id/`) is **not allowed** here — use `add_student` or `delete_student` instead.

Examples:
* `edit_tutorial from/T123 t/T456` renames the tutorial with ID `T123` to `T456`.
* `edit_tutorial from/T456 m/CS2103T d/2025-10-25` updates the tutorial with ID `T456` to have module code `CS2103T` and date `2025-10-25`.
* `edit_tutorial from/T123 t/T789 m/CS2040S d/2025-08-20` updates the tutorial with ID `T123` to:
    * Have new ID `T789`
    * Change its module code to `CS2040S`
    * Set its new date to `2025-08-20`

### Finding tutorials by keyword: `find`

Finds tutorials whose tutorial ID and/or module code contain any of the given keywords.

Format: 
1. **Search by one field:** `find t/KEYWORD [MORE_KEYWORDS]…` or `find m/KEYWORD [MORE_KEYWORDS]…`
2. **Search by both fields (AND condition):** `find m/KEYWORD [MORE_KEYWORDS]… t/KEYWORD [MORE_KEYWORDS]…`

* You can search by tutorial ID (`t/`), module code (`m/`), or both.
* Within each field, tutorials matching **at least one keyword** will be returned (i.e., `OR` search).
* When searching by **both fields**, tutorials must match at least one keyword from **each field** (i.e., `AND` between fields, `OR` within each field).
* The search is case-insensitive (e.g., `t01` will match `T01`).
* Tutorial IDs or module codes which contain the keyword partially will also be matched (e.g., `CS2103` will match `CS2103T`).

Examples:

* `find m/CS2103T CS2101` returns tutorials with module code `CS2103T` OR `CS2101`
* `find t/A101 B202` returns tutorials with tutorial ID `A101` OR `B202`
* `find m/CS2103T t/A101` returns tutorials with module code `CS2103T` AND tutorial ID `A101`

  ![result for 'find CS2103T'](images/findCommand.png)

### Listing all the students in a tutorial: `list_students`

Display a list of all the students enrolled in a specific tutorial on TAbs.

Format: `list_students t/TUTORIAL_ID`

* Lists all the students in a tutorial with the specified tutorial ID.
* It shows a numbered list of all the student IDs of the students in that tutorial (e.g., `1. A1234567X`).
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will list students from the same tutorial).

Examples:

* `list_students t/T2` lists all the students in the tutorial with ID `T2` in TAbs.

### Adding students to a tutorial: `add_student`

Adds one or more students to a specified tutorial in TAbs.

Format: `add_student id/STUDENT_ID… t/TUTORIAL_ID`

* Adds one or more students, identified by their student IDs, to the tutorial identified by the tutorial ID.
* You can specify multiple student IDs in a single command, separated by spaces.
* Each `STUDENT_ID` must follow the format `AXXXXXXX&`, where:
    * The first letter (`A`) is uppercase,
    * Followed by 7 digits (`XXXXXXX`),
    * Ending with an uppercase letter (`&`).
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will add students to the same tutorial).


Examples:
* `add_student id/A1231231Y t/T1` adds a student `A1231231Y` to tutorial `T1`. 
* `add_student id/A1231231Y id/A3213213Y id/A2223334B t/T2` adds students `A1231231Y`, `A3213213Y` and `A2223334B` to tutorial `T2`.

---

Behaviour and duplicate handling:

* If all specified students already exist in the tutorial, TAbs will reject the command and show
  an error message:

  ```
  The following student(s):
    [[A1231231Y], [A3213213Y]]
  are already in tutorial T2!
  ```
* If some students already exist but others are new, TAbs will:

    * Add the new students successfully.
    * Notify the user that certain students were already in the tutorial.
      Example:

  ```
  The following student(s):
    [[A2223334B]]
  were added to tutorial T2.

  The following student(s):
    [[A1231231Y], [A3213213Y]]
  are already in tutorial T2!
  ```

---

### Deleting a student from a tutorial: `delete_student`

Deletes the specified student from the specified tutorial from TAbs.

Format: `delete_student id/STUDENT_ID t/TUTORIAL_ID`

* Delete a single student, identified by the student ID, from the tutorial identified by the tutorial ID.
* The `STUDENT_ID` must follow the format `AXXXXXXX&`, where:
    * The first letter (`A`) is uppercase,
    * Followed by 7 digits (`XXXXXXX`),
    * Ending with an uppercase letter (`&`).
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will delete students from the same tutorial).

Examples:

* `delete_student id/A1231231Y t/T2` deletes the student with ID `A1231231Y` from the tutorial `T2`.

### Marking students in a tutorial as present: `mark`

Marks specified students in a tutorial in TAbs as present.

Format: `mark t/TUTORIAL_ID id/STUDENT_ID…`

* Marks one or more students, identified by their student ID, in the tutorial identified by the
  tutorial ID as present.
* You can specify multiple student IDs in a single command, separated by spaces.
* Each `STUDENT_ID` must follow the format `AXXXXXXX&`, where:
    * The first letter (`A`) is uppercase,
    * Followed by 7 digits (`XXXXXXX`),
    * Ending with an uppercase letter (`&`).
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will mark students from the same tutorial).

Examples:

* `mark t/C456 id/A1231231Y` marks student `A1231231Y` in tutorial `C456` as present.
* `mark t/T123 id/A1231231Y id/A3213213Y` marks students `A1231231Y` and `A3213213Y`
  in tutorial `T123` as present.

When a student has been marked as present, the GUI will update the student's ID within the tutorial to green as shown below.

![mark command](images/markCommand.png)

Behaviour:

* After the mark command is ran, the students in the input will be categorised into 3 groups: 
  successfully marked, already marked and not in the tutorial.
* If no students were successfully marked, TAbs will display the message as an error. 
* If at least one student was successfully marked, Tabs will display a success outcome.
* The following are messages that will be conditionally displayed based on whether any students
  fall in the respective groups.
   * Students who were successfully marked as present:
  ``` 
  The following student(s):
  [[A1231231Y], [A3213213Y]]
  were marked as present in tutorial T2.
  ```
    * Students who were already marked:
  ``` 
  The following student(s):
  [[A1231231Y], [A3213213Y]]
  were already marked as present.
  ```  
    * Students who do not exist in the tutorial:
  ``` 
  The following student(s):
  [[A1231231Y], [A3213213Y]]
  are not in tutorial T2.
  ```  
* If there are no students in that particular group the message for that group will not be shown.

### Marking ALL students in a tutorial as present: `mark_all`

Marks all students in a tutorial in TAbs as present.

Format: `mark_all t/TUTORIAL_ID`

* Marks all the students as present in the tutorial identified by the tutorial ID.
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will mark students from the same tutorial).

Examples:

* `mark_all t/C456` marks every student in the tutorial with ID `C456` as present.



### Unmarking students in a tutorial: `unmark`

Unmarks specified students in a tutorial in TAbs.

Format: `unmark t/TUTORIAL_ID id/STUDENT_ID…`

* Unmarks one or more students, identified by their student ID, in the tutorial identified by the
  tutorial ID.
* You can specify multiple student IDs in a single command, separated by spaces.
* Each `STUDENT_ID` must follow the format `AXXXXXXX&`, where:
    * The first letter (`A`) is uppercase,
    * Followed by 7 digits (`XXXXXXX`),
    * Ending with an uppercase letter (`&`).
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will unmark students from the same tutorial).

Examples:

* `unmark t/C456 id/A1231231Y` unmarks student `A1231231Y` in tutorial `C456`.
* `unmark t/T123 id/A1231231Y id/A3213213Y` unmarks student `A1231231Y` and student `A3213213Y`
  in tutorial `T123`.

Behaviour:
* The behaviour of unmark is similar to that of mark. Kindly refer to the behaviour section of the 
  mark command above.



### Unmarking ALL students in a tutorial: `unmark_all`

Unmarks all students in a tutorial in TAbs.

Format: `unmark_all t/TUTORIAL_ID`

* Unmarks all the students in the tutorial identified by the tutorial ID.
* `TUTORIAL_ID` is case-insensitive (e.g., both `t/t123` and `t/T123` will unmark students from the same tutorial).

Examples:

* `unmark_all t/C456` unmarks every student in the tutorial with ID `C456`.


### Clearing all tutorials: `clear`

Removes all tutorials from TAbs.

Format: `clear`

### Exiting the program: `exit`

Exits the TAbs application.

Format: `exit`

### Saving the data

TAbs data are saved in the hard disk automatically after any command that changes the data. There is
no need to save manually.

### Editing the data file

TAbs data are saved automatically as a JSON file `[JAR file location]/data/TAbs.json`. Advanced
users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
    If your changes to the data file makes its format invalid, TAbs will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
    Furthermore, certain edits can cause TAbs to behave in unexpected ways (e.g., if a value entered is beyond the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Future plans `[coming in v2.0]`

We intend to release an even more user-friendly version to the wider NUS community, 
featuring a proper user interface without the need of a CLI.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the
file that contains the data of your previous TAbs home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later
   switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete
   the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or
   the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new
   Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                          | Format, Examples                                                                                                                                          |
|---------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**                        | `help`                                                                                                                                                    |
| **List tutorials**              | `list`                                                                                                                                                    |
| **Add a tutorial**              | `add_tutorial t/TUTORIAL_ID m/MODULE_CODE d/DATE [id/STUDENT_ID]…` <br> (e.g., `add_tutorial t/T123 m/CS2103T d/2025-01-01 id/A1231231Y`)                 |
| **Delete a tutorial**           | `delete_tutorial t/TUTORIAL_ID`<br> (e.g., `delete_tutorial t/T1`)                                                                                        |
| **Copy a tutorial**             | `copy_tutorial t/NEW_TUTORIAL_ID from/EXISTING_TUTORIAL_ID d/DATE` <br> (e.g., `copy_tutorial t/C2 from/C1 d/2025-04-10`)                                 |
| **Edit a tutorial**             | `edit_tutorial from/EXISTING_TUTORIAL_ID [t/NEW_TUTORIAL_ID] [m/NEW_MODULE_CODE] [d/NEW_DATE]`<br> (e.g., `edit_tutorial from/T1 m/CS2103T d/2025-10-25`) |
| **Find tutorials**              | `find m/KEYWORD… t/KEYWORD…` (either or both fields)<br> (e.g., `find m/CS2103T`, `find t/A101`, `find m/CS2103T t/A101`)                                 |
| **List students in a tutorial** | `list_students t/TUTORIAL_ID`<br> (e.g., `list_students t/T1`)                                                                                            |
| **Add student(s)**              | `add_student id/STUDENT_ID… t/TUTORIAL_ID` <br> (e.g., `add_student id/A1231231Y id/A3213213Y t/T2`)                                                      |
| **Delete a student**            | `delete_student id/STUDENT_ID t/TUTORIAL_ID` <br> (e.g., `delete_student id/A3213213Y t/T123`)                                                            |
| **Mark a student**              | `mark id/STUDENT_ID… t/TUTORIAL_ID` <br> (e.g., `mark id/A1231231Y id/A3213213Y t/T123`)                                                                  |
| **Mark all students**           | `mark_all t/TUTORIAL_ID` <br> (e.g., `mark_all t/T123`)                                                                                                   |
| **Unmark a student**            | `unmark id/STUDENT_ID… t/TUTORIAL_ID` <br> (e.g., `unmark id/A1231231Y id/A3213213Y t/T123`)                                                              |
| **Unmark all students**         | `unmark_all t/TUTORIAL_ID` <br> (e.g., `unmark_all t/T123`)                                                                                               |
| **Clear all tutorials**         | `clear`                                                                                                                                                   |
| **Exit TAbs**                   | `exit`                                                                                                                                                    |
