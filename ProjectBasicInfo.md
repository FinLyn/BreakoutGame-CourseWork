# COMP2013LinCHEN 
(Updated on Nov 17,2021)

This is the coursework repository for Lin CHEN（20215609）'s Software Engineering project. 

Feel free to reach me at biylc2@nottingham.ac.uk if there are any questions.



## Project Basic Requirements:
- Project Checklists:
*  **COMP2013CHENLin folder**:
  * README.md
  * Design.pdf
  * CHENLinDemo.mp4
  * project/CHENLin_17.zip

- Project Analysis:
- Project Configurations:
  * Java SDK 17.00
  * Windows
  * IntellJ IDEA 2021.1


  ##  Code Check
  - Principles:
    * Single responsibility
    * Interface Segregation
    * Dependence Inversion
    * Open closed
    * Composite Reuse
    * Demeter
    * Liskov Substitution

  - Change to describle class name and function names
  - Remove harshcoded values(esepcially string) in the code and change it to final value defined in class.
  - Remove redundent code and encaupsulate it in the single function
  - Avoid use of static methods
  - Replace nested if statement with switch cases
  - Fix spelling errors
  - Name describle variable names
  - Replace constants with final defined string
  - Naming
    * `Naming variables`: Camel case / related to its purpose
    * `Naming file names`: Camel case
    * `Naming functions`: relate to the method's functionality
   -  Have method header format
   -  Add `comments` 
   -  Comment out unused functions
   -  No duplicate code in the same class or other class
   -  Declare global variables only if necessary to use in the other methods
   -  Double check creation of static variables inside a class


## Code conventions: 

 Referenced： Bob's 10 useful suggestions: (http://www.cs.nott.ac.uk/blaramee/research/codeConvention/laramee09codeConvention.pdf)

- All methods are 75 lines or less. All methods should be visible on a single screen/page. 

   Exception(s): Methods with case tables (switch statements) and perhaps the main method. 

-  No methods shall use more than five levels of indentation.

-  No line of code shall exceed 80 characters. It should not be necessary to expand the code editor to the entire screen width in order to read a single line of code.


-  All class variables start with the two character sequence “m ” (as in “member” variable) 

   Exception(s): symbolic constants. Symbolic constants should be written in ALL CAPITALS. 

-  All class variables are accessed through accessor methods, i.e. Get() and Set() methods. 


-  Accessor methods come at the top of both header files and implementation files.


-  All member class variables are private. 

   Exception(s): symbolic constants 

-  Private methods begin with a lower-case letter whereas public methods begin with an upper-case letter. 

   Exception(s): The Java Programming Language 

-  In general, methods should not take more than five parameters. 

   Exception(s): very rare 

-  Do not use numbers in your code, but rather symbolic constants. 

    Exception(s): 0 and 1.


##  `GitLab` Usage

- Issues
   * Issues are used for fixing bugs, adding functions, adding tests, updating readme files and releasing new version..
   * Each issue have a corresponding branch by assigning to a specific developing stages. .
   * When developer encounter a problem, he/ she may comment below issues and wait for others to offer a possible solution.
   
   * **[+ Create merge request and branch +]** : After an issue is fixed, it should be merged into the master branch after being checked.

- Labels
   * ~"" Lists to-do items as reminders.
   * ~Doing Ongoing events - should be finished before the due date.
   * ~Done Archives finished tasks.
   * ~Discussion Record meetings and discuss problems one encounters with the whole team.
   * ~Tips Share methodologies, tools, experiences with the whole team.
   * ~Bugs Report bugs find for later analysis.
- Issue Boards
   * An overview of all the tasks and progress.
- Commit messages
   * Commit messages mainly tells what modifications have been done on which class.
   * It should follow the git-commit conventions. <br>Referenced： Peer's useful suggestions: (https://chris.beams.io/posts/git-commit/#why-not-how) .
- Milestones
   * Be used to assign a subproject,which consists of lots of issues and it can be used to track the the completeness of subprocess.
- Merge Requests
   * All merge request should be approved by Lin CHEN after checking the correctness.
- Tags
   * Tags will be used for version control.
- Branches:
   * Branch will be created due to different implementation stages: Basic functionality checking, Advanced development, testing.

