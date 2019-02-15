This program created by:
W. Ross White
A00975239
Set B

On or around:
Decempber 12, 2018

The random number generator class provided by my instructor:
Dennis Richards

This is a game of life program designed to model interaction between eating and 
moving herbivores,and seeding plants.

A tour of the program:

There is an enumerator class to define my colours which are associated with
contents of cells.

There is a random number generator provided by my instructor.

There is an edible interface to implement in my plant class, so an herbivore
recognizes it as something it can eat; they will recognize as edible anything
which implements the herbivore edible interface.

My main simply instantiates a game, and calls the play function on it.

Game function instantiates a new world, and handles my GUI, including the 
start function which populates a gridpane with buttons defined in world, 
the launchGUI function which launches the GUI after instantiating a 2D button 
array, and handles button presses, which call the turn function, which calls
world turn on the newly created world.  Here also button presses to save and 
load a game are handled.

In the world class, a 2D array of cells is declared, then in the world
constructor, a createWorld function is called, which instantiates the 2D array,
and according to the rules presented by Dennis populates the array with herbivores, 
omnivores, carnivores, and plants (or null), calling their constructors sequentially in cells.

This class also has a turn function for when the buttons are clicked.  It calls
a live function on all cells which contain a lifeform, then sets a moved variable
to false after the move (a variable which ensures lifeforms only perform a behaviour
once per turn, move in the case of herbivores, and plants not participate in a spawn
on the turn of their creation.  It then calls a colorize function on every cell, which
reasserts the colours of the lifeform in a cell, and a default colour for cells with
no lifeform.

The world class also has an inner cell class, with a constructor which passes in 
horizontal and vertical coordinates, as well as a lifeform.

On turn in the world class, it calls a live function on the lifeforms.  This is
referencing the lifeform class, which has template functions used by all life forms,
with more particular functions specified for the more different plants.
Also in lifeform class there are references to a moves interface which contains positions
relative to the lifeform in question and indicate all the positions around any given cell, 
which all children can use in their processing their operations.  Also included are other 
primary methods used by plants and herbivores.

In the herbivore, omnivore, and carnivore classes, I have a constructor which passes in 
location variables and sets values for a series of variables particular to that life form.
When the live function is called on lifeform, all animals automatically perform the life
form's functions, with animal specific abstract functions defined by the class children.
The animal picks a spot at random around it.  It then performs a variety of checks, 
chooses a food to move to if it is available and resets last feed variable to zero, 
if not moves to a null if it is available.  If certain conditions are met, it will breed and 
put a new life form in an adjacent cell.  If lastfeed reaches 5, on it's next turn it calls 
a die function which sets its cell's lifeform variable to null.  This all happens in a while 
loop which uses the same moved variable as mentioned earlier, to keep attempting to 
move until a spot to move can be found. 

My plant class overrides the live function and in it calls a seed function, which does a
neighbour check, and if certain conditions are met, places a new plant in an adjacent null
cell chosen at random.