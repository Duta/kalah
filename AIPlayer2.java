import java.util.ArrayList;
import java.util.Random;

public class AIPlayer2 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;
    
    private Board currentBoard;
    private Board previousBoard;
    private Random generator;
    
    public AIPlayer2()
    {     
        generator = new Random();
    }

    //Here because fuck Bertie
    public void gameFinished(int i)
    {
    }

    @Override
    public int getSowIndex(Board board, int lastMove) 
    {
        System.out.println("PLAYER 2 MOVE ");
        this.currentBoard = board;
        if(test(0))
        {
            System.out.println(moveString(0));
            return run(0);    
        }
        else if(test(1))
        {
            System.out.println(moveString(1));
            return run(1);
        }
        else
        {
            System.out.println(moveString(2));
            return run(2);
        }
    }
    
    //Tests whether a move can be carried out
    private boolean test(int move)
    {
        System.out.println("TESTING MOVE " + move);
        if(run(move) == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    //Creates a test string for finding out which move is being used
    private String moveString(int move)
    {
        return "Running Move " + move;
    }
    
    //Checks the increase we give to an opponent from sowing from i
    public int increase(int i)
    {              
        int increase = getHouseOwn(i) - (NUM_HOUSES - i);
        //System.out.println("NUMBER IN HOUSE IS " + currentBoard.getSide(this).getHouse(i));
        
        if(increase >= 0)
        {
            return increase;
        }
        else
        {
        return 0;
        }
    }

    //Returns true if house is empty    
    private boolean checkHouseNotEmpty(int i)
    {   
        if(currentBoard.getSide(this).getHouse(i) > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Returns the number of seeds in a house
    private int getHouseOwn(int i)
    {
        return currentBoard.getSide(this).getHouse(i);
    }
    
    //works out if a move will finish in our own store
    private boolean willFinishInStore(int i)
    {   
        //Works out the distance from the store in a house
        int distance = NUM_HOUSES - i;
        
        if(((getHouseOwn(i) - distance) % 13) == 0) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
   
    //Runs a sow method based on an int   
    public int run(int index)
    {
        //FINISH IN STORE
        if(index == 1)
        {
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                if(willFinishInStore(i))
                {
                    //System.out.println("SOWING FROM " + i);
                    return i;
                }
            }
            
            return -1;
        }
        
        //STEAL
        else if(index == 0)
        {
            //Check if we have empty store or a store with 13, if no the return -1
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                if((getHouseOwn(i) == 13))
                {
                   return i;
                }
                else if(getHouseOwn(i) == 0)
                {
                    for(int j = 0; j < NUM_HOUSES; j++)
                    {
                        if(j > i)
                        {
                            if(( (j + getHouseOwn(i)) - i) == 13)
                            {
                                return j;
                            }
                        }
                        else if(j < i)
                        {
                            if(( (j + getHouseOwn(i)) - i) == 0)
                            {
                                return j;
                            }
                        }
                    }
                }
            }
            return -1;
        }      

        //MINOPP
        else if(index == 2)
        {
            //System.out.println("RUNNING MINOPP");
                
            int min = 49;
            int minIndex = 0;
            
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                int increase = increase(i);
                //System.out.println("INCREASE IS " + increase);
                
                if((increase >= 0) && (increase < min) && checkHouseNotEmpty(i))
                {
                    min = increase;
                    //System.out.println("MIN IS " + min);
                    minIndex = i;
                }
            }
            //System.out.println("SOWING FROM " + minIndex);
            return minIndex;
        }
        
        for(int i = 0; i < NUM_HOUSES; i++)
        {
            if(getHouseOwn(i) != 0)
            {
               return i; 
            }
        }
        
        return 1;
        
     }
}
                    
                    
                
    
    
    
    
    
    
    
    
    
