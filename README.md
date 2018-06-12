# Assignment 4:  Super Mario

1. The game is side-scrolling. The characters onscreen are viewed from a side-view camera angle. Our game make the onscreen characters move from the right to left side, like a mirror image of the original game. 
 2. The original game has several items. We implement the following: 
    - Coins: They are worth 200 points and are very common. 
    - Super Mushroom: They are worth 1000 points, and are less common. It gives Mario the ”Super” form.   
    - Fire Flower: They are also worth 1000 points and they give Mario the ability to shoot fireballs, by trans-forming him into ”Fire Mario” form. 
 3. Mario has different transformations. Mario transforms between those versions by touching an enemy or obstacle, or by acquiring a particular type of item. We implement the ”Super” and ”Fire Mario” versions, apart from the version of Mario at the beginning of the game.  
    -  ”Super" Mario have 1 extra life and a super size body.  It will go back to small size when hit by enemies and won't lose life for that.  
    - ”Fire Mario” has a fire skill, which could emit jumping fire balls.  It is also a large size Mario.  It will go back to small size and lose a life if hit by enemies.
 4. Among the many enemies that Mario has, you have to implement at least 3 types. The possible enemies are: 
    - Buzzy Beetle : It is small turtle that hides in its shell when jumped on or touched, and it is immune to fireballs. 
    -  Bloober: It is a squid-like sentry that persistently pursues the player. 
    - Koopa Paratroopa: They are small turtles with wings. Hostile green ones jump towards the player, and breezy red ones fly back and forth, or up and down. 
    -  Piranha Plant: It is a carnivorous plant that lives in pipes. It rises up trying to hit Mario and retreats. If Mario is near, it won’t rise up.  
 5. Mario is able to jump by hitting W key. He is able to break certain types of blocks. 
 6. Mario has 3 lives. We keep a running score and also keep track of the number of lives. 
 7. We are able to run it on desktop mode, and use WASD key to control Mario.   Once you arrive the flagpole in each level, you are entering the next level. 