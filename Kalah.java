public class Kalah {
    private Player p1, p2;
    private int[] p1houses, p2houses;
    private int p1store, p2store;
    private boolean p1sTurn;

    public Kalah(boolean p1starts) {
        p1 = new AIPlayer1();
        p2 = new AIPlayer2();
        p1houses = new int[6];
        p2houses = new int[6];
        p1store = 0;
        p2store = 0;
        p1sTurn = p1starts;
    }

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

    private boolean isGameOver() {
        return allEmpty(p1houses)
            || allEmpty(p2houses);
    }

    private boolean sow(int index) {
        int numToSow = houses[index];
        // TODO
        return false;
    }

    private boolean allEmpty(int[] houses) {
        for(int i = 0; i < houses.length; i++) {
            if(houses[i] != 0) {
                return false;
            }
        }
        return true;
    }

    private void moveAllSeedsToStores() {
        if(allEmpty(p1houses)) {
            moveAllSeedsToStores(p2houses, false);
        } else if(allEmpty(p2houses)) {
            moveAllSeedsToStores(p1houses, true);
        } else {
            error("moveAllSeedsToStores() called with non-empty stores");
        }
    }

    private void moveAllSeedsToStores(int[] houses, boolean p1) {
        for(int i = 0; i < houses.length; i++) {
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

    private void error(String message) {
        System.err.println("Error: " + message);
    }
}
