import java.util.ArrayList;
import java.util.Random;

public class AIPlayer2 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;
    
    private Board currentBoard;
    private Board previousBoard;
    private Random generator;
    
    private int ownStoreChance;
    private int stealChance;
    private int previousStore;
    
    private ArrayList<Integer> list = new ArrayList<Integer>();
    
    public AIPlayer2()
    {   
        previousStore = 0;  
        ownStoreChance = 150;
        stealChance = 150;
        generator = new Random();
    }

    //Here because we love Bertie
    public void gameFinished(GameResult result)
    {
         Winner winner = result.getWinner();
         int ownStoreCount = countMoveType(0);
         int stealCount = countMoveType(1);
         
         if(ownStoreCount < stealCount)
         {
            if(winner == Winner.PLAYER_TWO)
            {
               stealChance += 5;
            }
            if(getStoreOwn() > previousStore)
            {
               stealChance = stealChance + (getStoreOwn() - previousStore);
            }
            
         }
         else if(ownStoreCount > stealCount)
         {
            
            if(winner == Winner.PLAYER_TWO)
            {
               ownStoreChance += 5;
            }
            if(getStoreOwn() > previousStore)
            {
               ownStoreChance = ownStoreChance + (getStoreOwn() - previousStore);   
            }
         }
         
         list.clear();
         
         if(getStoreOwn() > previousStore)
         {
            previousStore = getStoreOwn();
         }
    }
    
    private int countMoveType(int i)
    {
      int total = 0;
      for(int j = 0; j < list.size(); j++)
      {
         if(list.get(j) == i)
         {
            total++;
         }
      }
      return total;
    }

    @Override
    public int getSowIndex(Board board, int lastMove) 
    {
        
        this.currentBoard = board;
        int random = generator.nextInt(ownStoreChance + stealChance);
        
        if(random < ownStoreChance)
        {
            if(test(0))
            {
               list.add(0);
               return run(0);
            }
            else if(test(1))
            {
               list.add(0);
               return run(1);
            }
            else
            {
               return run(2);
            }
        }
        else
        {
            if(test(1))
            {
               list.add(1);
               return run(1);
            }
            else if(test(0))
            {
               list.add(1);
               return run(0);
            }
            else
            {
               return run(2);
            }
        }
        
    }
   
    
    //Tests whether a move can be carried out
    private boolean test(int move)
    {
        if(run(move) == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    //Checks the increase we give to an opponent from sowing from i
    public int increase(int i)
    {              
        int increase = getHouseOwn(i) - (NUM_HOUSES - i);
        
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
    
    private int getStoreOwn()
    {
      return currentBoard.getSide(this).getStore();
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
        if(index == 0)
        {
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                if(willFinishInStore(i))
                {
                    return i;
                }
            }
            
            return -1;
        }
        
        //STEAL
        else if(index == 1)
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
                
            int min = 49;
            int minIndex = 0;
            
            for(int i = 0; i < NUM_HOUSES; i++)
            {
                int increase = increase(i);
                
                if((increase >= 0) && (increase < min) && checkHouseNotEmpty(i))
                {
                    min = increase;
                    minIndex = i;
                }
            }
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
                    
                    
                
    
    
    
    
    
    
    
    
    
