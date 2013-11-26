/**
 * A class for a single game of Kalah
 */
public class Kalah {
    private static final int NUM_HOUSES = 6;

    private Player p1, p2;
    private int[] p1houses, p2houses;
    private int p1store, p2store;
    private boolean p1sTurn;

    /**
     * Constructor to set up the initial game board with 4 seeds in each house and creates the AI players
     * @param p1starts TRUE if player 1 is to have to first move, FALSE if player 2 is to have the first move
     */
    public Kalah(boolean p1starts) {
        p1 = new AIPlayer1();
        p2 = new AIPlayer2();
        p1houses = new int[NUM_HOUSES];
        p2houses = new int[NUM_HOUSES];

        for(int i = 0; i < NUM_HOUSES; i++) {
            p1houses[i] = 4;
            p2houses[i] = 4;
        }

        p1store = 0;
        p2store = 0;
        p1sTurn = p1starts;
    }

    /**
     * The method called when you want to run a game
     * @return result The result of the game, 0 if a draw, 1 if p1 wins, 2 if p2 wins.
     */
    public int run() {
        while(!isGameOver()) {
            // Equivalent to if(p1sTurn){current=p1;}else{current=p2;}
            Player current = p1sTurn ? p1 : p2;
            int[] ownHouses = p1sTurn ? p1houses : p2houses;
            int[] otherHouses = p1sTurn ? p2houses : p1houses;
            int ownStore = p1sTurn ? p1store : p2store;
            int otherStore = p1sTurn ? p2store : p1store;
            // Ask the player for their decision
            int sowIndex = current.getSowIndex(
                ownHouses, otherHouses, ownStore, otherStore);
            // Do the move
            boolean extraTurn = sow(sowIndex);
            // Unless they have an extra go
            if(!extraTurn) {
                // Swap turns
                p1sTurn = !p1sTurn;
            }
        }
        moveAllSeedsToStores();
        if(p1store > p2store) {
            // Player 1 won
            return 1;
        } else if(p2store > p1store) {
            // Player 2 won
            return 2;
        } else {
            // It was a draw
            return 0;
        }
    }

    /**
     * Checks if the game is over
     * @return gameOver return TRUE if either all of p1's or p2's houses are empty, else returns false
     */
    private boolean isGameOver() {
        return allEmpty(p1houses)
            || allEmpty(p2houses);
    }

    /**
     * Moves the seeds (sows) to the correct places
     * @param index The index to take the seeds from
     * @return anotherTurn return TRUE if the player requires another turn, else returns FALSE if the turn should be switched
     */
    private boolean sow(int index) {
        int[] ownHouses = p1sTurn ? p1houses : p2houses;
        int[] otherHouses = p1sTurn ? p2houses : p1houses;
    
        int numToSow = ownHouses[index];
        ownHouses[index] = 0;
        
        //For loop to add one up until end of p1 houses, if run out of seeds return false
        for(int i = index + 1; i < NUM_HOUSES; i++) {
            numToSow--;
            
            if(numToSow == 0) {
                if(ownHouses[i] == 0) {
                    int oppositeIndex = NUM_HOUSES - i - 1;
                    int num = otherHouses[oppositeIndex];
                    if(p1sTurn) {
                        p1store += num;
                    } else {
                        p2store += num;
                    }
                    otherHouses[oppositeIndex] = 0;
                } else {
                    ownHouses[i]++;
                }
                return false;
            } else {
                ownHouses[i]++;
            }
        }
            
        //While loop
        while(numToSow != 0) {
            if(p1sTurn) {
                p1store++;
            } else {
                p2store++;
            }
            numToSow--;
            
            if(numToSow == 0) {
                return true;
            }
            
            for(int i = 0; i < NUM_HOUSES; i++) {
                otherHouses[i]++;
                numToSow--;
        
                if(numToSow == 0) {
                    return false;
                }    
            }
                        
            for(int i = 0; i < NUM_HOUSES; i++) {
                numToSow--;
            
                if(numToSow == 0) {
                    if(ownHouses[i] == 0) {
                        int oppositeIndex = NUM_HOUSES - i - 1;
                        int num = otherHouses[oppositeIndex];
                        if(p1sTurn) {
                            p1store += num;
                        } else {
                            p2store += num;
                        }
                        otherHouses[oppositeIndex] = 0;
                    } else {
                        ownHouses[i]++;
                    }
                    return false;
                } else {
                    ownHouses[i]++;
                }
            }
        }
        
        throw new RuntimeException("Um, fix yo code.");
    }

    /**
     * Checks to see if all elements in an array are zero
     * @param houses The array the check
     * @return allEmpty returns TRUE if all elements in the array are zero, else returns FALSE if they are not all zero
     */
    private boolean allEmpty(int[] houses) {
        for(int i = 0; i < NUM_HOUSES; i++) {
            if(houses[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Decides which player has all the empty houses, then calls a method to move all the other players seeds
     */
    private void moveAllSeedsToStores() {
        if(allEmpty(p1houses)) {
            moveAllSeedsToStores(p2houses, false);
        } else if(allEmpty(p2houses)) {
            moveAllSeedsToStores(p1houses, true);
        } else {
            error("moveAllSeedsToStores() called with non-empty stores");
        }
    }

    /**
     * Moves all of a players seeds to their house
     * @param houses The array of houses to empty into a store
     * @param p1 TRUE if p1's houses need emptying, FALSE if p2's houses need emptying.
     */
    private void moveAllSeedsToStores(int[] houses, boolean p1) {
        for(int i = 0; i < NUM_HOUSES; i++) {
            // Get the number of seeds in the
            // current house and zero it
            int numSeeds = houses[i];
            houses[i] = 0;
            // Add those seeds to the store
            if(p1) {
                p1store += numSeeds;
            } else {
                p2store += numSeeds;
            }
        }
    }

    /**
     * Creates an error message
     * @param message The message you want to return in the error stream
     */
    private void error(String message) {
        System.err.println("Error: " + message);
    }
}
