public class AIPlayer2 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;

    private int gameCount = 0;	

    private int index;
    private Board currentBoard;
    private Board previousBoard;

    //Values of moves
    private int stealValue; //Move 1
    private int finishStoreValue; //Move 2
    private int noOppValue; //Move 3
    private int minOppValue; //Move 4
    
    private int lastMove; //Contains the number of the last move 1,2,3,4

    @Override
    public int getSowIndex(Board board, int lastMove) {
    
        this.currentBoard = board;
    
        //Remembering previous after chosen move and compare the new board
            //If same we have two moves in a row
    
    	//need a game count :)
    	//list of moves (steal,finish in own store, Not put seeds into opp houses, put least into opp houses)
    	//Each move starts a set value
    	//If a type of move succedes then add to value
    	//If it fails then reduce value
        
        
        gameCount++;
        previousBoard = currentBoard.getBoardForMove(index);
        
        return index;
    }
    
    //Return -1 if move is not possilbe
    private int steal()
    {
        //Check if opp has empty store, if no the return -1
        for(int i = 0; i < NUM_HOUSES; i++)
        {
            if(board.p2Side.getHouse(i) = 0)
            {
                //Sow from each house, checking if finish in empty house
                for(int j = 0; j < NUM_HOUSES; j++)
                {
                    if(board.getBoardForMove(j).p2Side.getHouse(i) = 1)
                    {
                        return j;
                    }
                }
            }       
        }     
        return -1;
    }
    
    private int finishOwnStore()
    {
        for(int i = 0;i < NUM_HOUSES;i++)
        {
            private int ownStoreInc = (board.getBoardForMove(i).getStore(P2)) - (board.getStore(P2));
            private int oppHouseInc = (board.getBoardForMove(i).p1Side.getHouse(0)) - (board.p1Side.getHouse(0));
            
            if(ownStoreInc > oppHouseInc)
            {
                return i;
            }            
        }
        
        return -1;  
    }
    
    private int noOpp()
    {
    }
    
    private int minOpp()
    {
    }
    
    private boolean ourLastMove()
    {
        return currentBoard == previousBoard;         
    }
    
    /**
     * A generic class for a move 
    */
    private class Move
    {
        private int value;
        
        public Move(int value)
        {
            this.value = value;
        }
        
        public void setValue(int value)
        {
            this.value = value;
        }
        
        public void incValue(int inc)
        {
            value += inc;
        }
        
        public void decValue(int dec)
        {
            value -= dec;
        }
        
        public int getValue(int vlaue)
        {
            return value;
        }
    }
    
    private class steal extends Move
    {
        public steal()
        {
            super();
        }
        
        public run()
        {
            
        }
    }
    
    private class finishOwnStore extends Move
    {
        public finishOwnStore()
        {
            super();
        }
        
        public run()
        {
            
        }
    }
    
    private class noOpp extends Move
    {
        public noOpp()
        {
            super();
        }
        
        public run()
        {
            
        }
    }
    
    private class minOpp extends Move
    {
        public minOpp()
        {
            super();
        }
        
        public run()
        {
            
        }
    }
    
}
