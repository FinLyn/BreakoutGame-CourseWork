## BASIC INFO
Lin CHEN (biylc2), Tested on Windows, Maven Build Scrip, Java 17, JavaFX 17
## BUILD AND RUN
- Using terminial
```
cd project/CHENLin_17
mvn clean javafx:run
```
- Using IntelliJ <br>

 View-> Tool Window -> maven -> execute maven goal ->mvn javafx:run

## REFACTOR 
| Change | Why |
|----------|:-------------:|
| <b>Change Swing to JavaFX:<br> `Remove AWT package`<br> `Add in JavaFX package` |Required specification|
|<b> Change the class to MVC model<br>`Separate class into Model,View,Controller` <br> `Use Controller and FXML with CSS` |<br> Model for basic attributes, View for stage layouts, Controller for actions <br> Controller for actions, FXML for stage layouts, CSS for layout's defined style |
|<b> Add [project convention file](https://projects.cs.nott.ac.uk/biylc2/comp2013linchen/-/blob/main/ProjectBasicInfo.md)| Defined convention for Using Git and Naming project items, and check each item after each modification |
|<b> Encaupsulate class<br> `private variables and methods` <br> `package constraints`| Improve code security<br>Control functionality modification |
| <b> Add Junit tests |Regression tests without breaking original functionalities |
| <b> Add JavaDoc and comments | Describe names and method functionalities |
| <b> Design Pattern <br>`Single`<br> `Observer`<br>`Factory`<br>`Builder`| <br> Allow <b>one game application instance</b> <br> Allow changes reflections in related class <br> Build specific game scene with bound properties <br> Build same component using different ways(image or geometric) |
| <b> Add Movable Interface| Define action for movable objects|
| <b> Add background for [pause menu](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/pauseMenu.png) and [buff menu](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/debugMenu.png)| `My:`[Changable style class when clicked](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/changableStyle.png)<br>`My:`[No next Level Message](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/nextLevelWarning.png)|
| <b> Add module info.java | Required specification |
| <b> Add Enum class `SoundEffect` <br>`BrickFactory`<br> `SceneOptions`<br>| Only defined game <b>Music sound </b><br><b> BrickView</b> for image<br><b> Scene settings with background and music</b><br> are allowed|
| <b> Add text files<br>`world building`<br>`scene sets` <br> `record`<br>`log`|Building <b>additional levels </b>and  are achieved by reading in different files<br> Record for<b> highest score </b>lists <br> log for <b>password verification</b>|
## NEW FEATURE
|Change | Note |
|----------|:-------------:|
|<b> Add a start screen| Buttons to each page|
|<b> Add pop up after each round|`My:` [Dynamic arrow animation](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/arrowTransition.png) on the rank board that points to the player's newest score<br> `My:`[Changable Status Title](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/Win.png)|
|<b> Add image for the ball,paddle,brick| More specific in `Add scene option page` |
|<b>Add addtional brick levels [Whole](https://projects.cs.nott.ac.uk/biylc2/comp2013linchen/-/issues/19)<br>|[City](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/cityLevel.png)<br>[Space](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/spaceLevel.png)<br>[Soccer](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/soccerLevel.png)|
|<b> My: Add sound for the background music, ball hitting and brick breaking| |
|<b>My : [Log in page](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/spaceSetting.png) with validation and warning dialog| `New user` : new password <br> `Old User` : first-created password<br> This fulfills the goal of enter player's name|
|<b> My: Add music bar controls the background music volume|Clicks the image can show the control Achieved using slider|
|<b> My: Add instruction page and guidance||
|<b>My: Add [scene option page](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/modeChoose.png) that allows the user to choose <br> [SpaceTheme](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/spaceSetting.png)<br> [CityTheme](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/citySetting.png)<br> [SoccerTheme](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/soccerSetting.png)<br> |This is the higher version of the color theme ro play with defined ball and paddle.<br>General setting contains both building of geometric and picture form of the scene and allows user to choose ball and paddle color|
|<b>My: Rank page displays the player's  highest score and TOP 5 gamers||
|<b>My: [Game check board](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/checkBoard.png) that displays the current score and show life status using pictures||
|<b>My: Resizable game scene|Each page except the game board is resizable. <br> Users are only allowed to change the game board size by choosing a new scene option|
|<b> My: [Confirmation dialog](project/CHENLin_17/src/main/resources/project/chenlin_17/Pictures/confirmation.png)  when the user clicks close button on the stage||
|<b> My: Add bobboing off effect for the ball when it hits the wall's upper bound, it shivers.||
