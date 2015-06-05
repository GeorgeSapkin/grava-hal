# Grava Hal

Grava Hal is a simple Backgammon-like game for two players.

## Running

`./gradlew appStart` and go to [http://localhost:8080](http://localhost:8080)

## Game Rules

### Board Setup

Each of the two players has six pits with six stones each plus and additional, larger pit, called a Grava Hal.

### Game Play

The player who begins with the first move picks up all the stones in any of his own six pits, and sows the stones to the right, one in each of the following pits, including his own Grava Hal. No stones are put in the opponent's Grava Hal. If the player's last stone lands in his own Grava Hal, he gets another turn. This can be repeated several times before it is the other player's turn.

### Capturing Stones

When the last stone lands in an own empty pit, the player captures his own stone and all stones in the opposite pit (the other player's pit) and puts them in his own Grava Hal.

### Game Ends

A game ends when one of the Grava Hals has more than half of the stones (6 x 6 = 36) or when one of the sides run out of stones. The player who still has stones in his pits keeps them and puts them in his/hers Grava Hal. Winner of the game is the player who has the most stones in his Grava Hal.

## Note

This has been used/tested with OpenJDK 1.8 using Chrome 43 on Fedora 22 x64.
