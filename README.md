# 2048FX

Built using Java and JavaFX only. <br>
Github:
https://github.com/zLyus/Java-2048FX <br>
based of the original Game:
https://play2048.co/

Check down below for Screenshots!

## Project Information
This game was created as a school project and I have come to find a liking in making projects like this where I can let my creativity run wild and manage everything myself.
<br>

I will still most likely stop working on this project after the deadline given by my school, but I might come back to it for Code Optimization or more Features.

## Description
Based on the original Game with a lot more Features for a better User Experience <br>
- Saves the Games you played via via Java Serialization
- You can change the size of the board
- Custom Themes (User input a Color in Hexcode)
- An Algorithm that plays the game for you, simulating 4 moves into the future and then deciding on the ideal Move,
  similar to an AI there´s also a Penalty and Reward System, which helps the Bot deciding on the best possible Action.

## Tutorial

You first need to create a new Game by 1.) deciding on a Theme that sets the Colors of the Game. <br>
The Program provides the User with two Options:
- Theme Presets that are already pre-defined
- Custom Themes where the User inputs a Hexcolor and the Fade for the Tiles is generated

After deciding on a Theme, the User needs to set the Boardsize, default Size is 4x4. <br>
The Upper Limit is 25, because the Playability heavily decreases after that.

Then the Game Starts and the User can play, additionaly the User has a few Button that all provide unique Features:
- "Start new Game" starts new Game with the same Theme and Boardsize
- "Reset Highscore" resets the Highscore (Highscore is serialized)
- "Last Games" shows the last 3 played Games (Last Games are serialized)
- "Change Theme" allows the User to change the Theme while playing
- "Start Bot" manages the Algorithm playing the Game for you

The Program also serializes the Boardstatus and Theme when the program is stopped, while being in the Game
and gives the User the Option to continue the Game they last played.



### Controls
Either use the Arrow Keys or WASD to move the tiles. <br>

## Screenshots
![alt Screenshot1](https://raw.githubusercontent.com/zLyus/Java-2048FX/master/src/main/resources/imgs/Sc1.png) <br>
![alt Screenshot2](https://raw.githubusercontent.com/zLyus/Java-2048FX/master/src/main/resources/imgs/Sc5.png) <br>
![alt Screenshot3](https://raw.githubusercontent.com/zLyus/Java-2048FX/master/src/main/resources/imgs/Sc3.png) <br>
![alt Screenshot4](https://raw.githubusercontent.com/zLyus/Java-2048FX/master/src/main/resources/imgs/Sc4.png) <br>
