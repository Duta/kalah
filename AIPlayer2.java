import java.util.ArrayList;
import java.util.Random;

public class AIPlayer2 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;

    private int gameCount = 0;        

    private int index;
    private Board currentBoard;
    private Board previousBoard;
    
    private Random generator;
    
    private Steal steal = new Steal(12);
    private FinishOwnStore finishOwnStore = new FinishOwnStore(12);
    private NoOpp noOpp = new NoOpp(12);
    private MinOpp minOpp = new MinOpp(12);
    
    private ArrayList<Move> moveList = new ArrayList<Move>();
    
    public AIPlayer2()
    {
        moveList.add(finishOwnStore);
        moveList.add(steal);
        moveList.add(noOpp);
        moveList.add(minOpp);     
        
        generator = new Random();
        
    }

    //private Board.Side P2SideC = currentBoard.getSide(this);
    //private Board.Side P1SideC = currentBoard.getSide(other);
    
    //private Board.Side P2Side = getSide(this);
    //private Board.Side P1Side = getSide(other);
    
    private int lastMove; //Contains the number of the last move 1,2,3,4

    public void gameFinished(int i)
    {
    }

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
        
        //if random number is less than value in first position
        //if random number is less than runningTotal + value in second position
        //if random number is less than runningTotal + value in third position
        //if random number is less than runningTotal + value in forth position
        
        int totalValue = 0;
        
        for(int i = 0; i < moveList.size(); i++)
        {
            totalValue = totalValue + moveList.get(i).getValue();
        }
        
        int runningTotal = 0;
        
        int randomChoice = generator.nextInt(totalValue);
        
        for(int i = 0; i < moveList.size(); i++)
        {
            if(runningTotal + moveList.get(i).getValue() > randomChoice)
            {
                if(moveList.get(i).run() != -1)
                {
                
                    moveList.incValue(currentBoard.getBoardForMove(moveList.get(i).run()).p2side.getStore() - currentBoard.p2side.getStore());
                
                    return moveList.get(i).run();
                }
            }
            
            runningTotal = runningTotal + moveList.get(i).getValue();
        }
        
        return minOpp.run();
        
        //gameCount++;
        //previousBoard = currentBoard.getBoardForMove(index);
        
    }
    
    private int incStoreGivenMove(int index)
    {
        return currentBoard.getBoardForMove(index).p2side.getStore() - currentBoard.p2side.getStore();
    }
    
       
    private boolean ourLastMove()
    {
        return currentBoard == previousBoard;
    }
      
    private int countIncreaseSideOpp(Board predicted)
    {
        int total = 0;
        
        for(int i = 0; i < NUM_HOUSES;i++)
        {
            total += predicted.getSide(other).getHouse(i) - currentBoard.getSide(other).getHouse(i);
        }
        return total;
    }  
      
        
    /**
* A generic class for a move
*/
    private class Move extends AIPlayer2
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
        
        public int getValue()
        {
            return value;
        }      
    }
    
    private class Steal extends Move
    {
        public Steal(int value)
        {
            super(value);
        }
        
        public int run()
        {
            //Check if opp has empty store, if no the return -1
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                if(currentBoard.p2side.getHouse(i) == 0)
                {
                    //Sow from each house, checking if finish in empty house
                    for(int j = 0; j < NUM_HOUSES; j++)
                    {
                        if((currentBoard.getBoardForMove(j).p2side.getHouse(i)) == 1)
                        {
                            return j;
                        }
                    }
                }
            }
            return -1;
        }
    }
    
    private class FinishOwnStore extends Move
    {
        public FinishOwnStore(int value)
        {
            super(value);
        }
        
        public int run()
        {
            for(int i = 0;i < NUM_HOUSES;i++)
            {
                Board newBoard = currentBoard.getBoardForMove(i);
            
                int ownStoreInc = (newBoard.p2side.getStore()) - (currentBoard.p2side.getStore());
                int oppHouseInc = (currentBoard.getBoardForMove(i).getSide(other).getHouse(0)) - (currentBoard.getSide(other).getHouse(0));
                
                if(ownStoreInc > oppHouseInc)
                {
                    return i;
                }
            }           
            return -1;
        }
    }
    
    
    private class NoOpp extends Move
    {
        public NoOpp(int value)
        {
            super(value);
        }
        
        public int run()
        {
            int count = 6;
            
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                if (currentBoard.p2side.getHouse(i) <= count)
                {
                    return i;
                }
            }
            return -1;
        }
    }
    
    private class MinOpp extends Move
    {
        public MinOpp(int value)
        {
            super(value);
        }
        
        public int run()
        {
            int min = 49;
            int minIndex = 0;
        
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                int count = countIncreaseSideOpp(currentBoard.getBoardForMove(i));
                
                if(count < min)
                {
                    min = count;
                    minIndex = i;
                }
            }
            
            return minIndex;
            
        }
    }   
}
