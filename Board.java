public class Board {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;

    private Side p1side, p2side;
    private Player p1, p2;
    private Player current;
    
    public Board(Player p1, Player p2, boolean p1starts) {
        p1side = new Side();
        p2side = new Side();
        this.p1 = p1;
        this.p2 = p2;
        current = p1starts ? p1 : p2;
    }

    private Board(Board board) {
        this.p1side = new Side(board.p1side);
        this.p2side = new Side(board.p2side);
        this.p1 = board.p1;
        this.p2 = board.p2;
        this.current = board.current;
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public Board getBoardForMove(int index) {
        Board board = new Board(this);
        
        Side ownSide = current == p1 ? board.p1side : board.p2side;
        Side otherSide = current == p1 ? board.p2side : board.p1side;
        
        int numToSow = ownSide.getHouse(index);
        ownSide.zeroHouse(index);
        
        if(numToSow == 0) {
            throw new RuntimeException("You can't sow from an empty house");
        }

        //For loop to add one up until end of p1 houses, if run out of seeds return false
        for(int i = index + 1; i < NUM_HOUSES; i++) {
            numToSow--;
            
            if(numToSow == 0) {
                if(ownSide.isHouseEmpty(i)) {
                    int oppositeIndex = NUM_HOUSES - i - 1;
                    int num = otherSide.getHouse(oppositeIndex);
                    ownSide.addStore(num);
                    otherSide.zeroHouse(oppositeIndex);
                } else {
                    ownSide.incHouse(i);
                }
                board.switchPlayers();
                return board;
            } else {
                ownSide.incHouse(i);
            }
        }
            
        //While loop
        while(numToSow != 0) {
            ownSide.incStore();
            numToSow--;
            
            if(numToSow == 0) {
                return board;
            }
            
            for(int i = 0; i < NUM_HOUSES; i++) {
                otherSide.incHouse(i);
                numToSow--;
        
                if(numToSow == 0) {
                    board.switchPlayers();
                    return board;
                }    
            }
                        
            for(int i = 0; i < NUM_HOUSES; i++) {
                numToSow--;
            
                if(numToSow == 0) {
                    if(ownSide.isHouseEmpty(i)) {
                        int oppositeIndex = NUM_HOUSES - i - 1;
                        int num = otherSide.getHouse(oppositeIndex);
                        ownSide.addStore(num);
                        otherSide.zeroHouse(oppositeIndex);
                    } else {
                        ownSide.incHouse(i);
                    }
                    board.switchPlayers();
                    return board;
                } else {
                    ownSide.incHouse(i);
                }
            }
        }

        throw new RuntimeException("Something's gone horribly, horribly wrong.");
    }

    public Side getSide(Player player) {
        if(player == p1) {
            return p1side;
        } else {
            return p2side;
        }
    }

    public boolean isGameOver() {
        return p1side.hasAllHousesEmpty()
            || p2side.hasAllHousesEmpty();
    }

    public void finalize() {
        if(!isGameOver()) return;
        p1side.moveAllHousesToStore();
        p2side.moveAllHousesToStore();
    }

    private void switchPlayers() {
        current = current == p1 ? p2 : p1;
    }

    public static class Side {
        private static final int INITIAL_SEEDS = 4;

        private int[] houses;
        private int store;

        public Side() {
            houses = new int[NUM_HOUSES];
            for(int i = 0; i < NUM_HOUSES; i++) {
                houses[i] = INITIAL_SEEDS;
            }
            store = 0;
        }

        public Side(Side side) {
            houses = new int[NUM_HOUSES];
            for(int i = 0; i < NUM_HOUSES; i++) {
                houses[i] = side.houses[i];
            }
            store = side.store;
        }

        public boolean hasAllHousesEmpty() {
            for(int house : houses) {
                if(house != 0) {
                    return false;
                }
            }
            return true;
        }

        public void setStore(int numSeeds) {
            store = numSeeds;
        }

        public void addStore(int numSeeds) {
            store += numSeeds;
        }

        public void incStore() {
            store++;
        }

        public void setHouse(int index, int numSeeds) {
            ensureValidIndex(index);
            houses[index] = numSeeds;
        }

        public void incHouse(int index) {
            setHouse(index, getHouse(index) + 1);
        }

        public void zeroHouse(int index) {
            setHouse(index, 0);
        }

        public int getStore() {
            return store;
        }

        public int getHouse(int index) {
            ensureValidIndex(index);
            return houses[index];
        }

        public boolean isHouseEmpty(int index) {
            return getHouse(index) == 0;
        }

        public void moveAllHousesToStore() {
            for(int i = 0; i < NUM_HOUSES; i++) {
                store += houses[i];
                houses[i] = 0;
            }
        }

        private static void ensureValidIndex(int index) {
            if(index < 0 || index >= NUM_HOUSES) {
                throw new RuntimeException("Invalid index (" + index + ")");
            }
        }
    }
}
