/**
 * A class for a single game of Kalah
 */
public class Kalah {    
    public static final int NUM_HOUSES = 6;
    private static final Player
        P1 = new AIPlayer1(),
        P2 = new AIPlayer2();
    private boolean p1starts;

    static {
        P1.setOpponent(P2);
        P2.setOpponent(P1);
    }

    /**
     * Constructor to set up the initial game board with 4 seeds in each house and creates the AI players
     * @param p1starts TRUE if player 1 is to have to first move, FALSE if player 2 is to have the first move
     */
    public Kalah(boolean p1starts) {
        this.p1starts = p1starts;
    }

    public int run() {
        Board board = new Board(P1, P2, p1starts);
        int lastIndex = -1;
        while(!board.isGameOver()) {
            Player current = board.getCurrentPlayer();
            int index = current.getSowIndex(board, lastIndex);
            board = board.getBoardForMove(index);
            lastIndex = index;
        }
        board.finalize();
        int p1store = board.getSide(P1).getStore();
        int p2store = board.getSide(P2).getStore();
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
}
