/**
 * A class for a single game of Kalah
 */
public class Kalah {
    public static final int NUM_HOUSES = 6;

    private Player p1, p2;
    private boolean p1starts;

    /**
     * Constructor to set up the initial game board with 4 seeds in each house and creates the AI players
     * @param p1starts TRUE if player 1 is to have to first move, FALSE if player 2 is to have the first move
     */
    public Kalah(boolean p1starts) {
        p1 = new AIPlayer1();
        p2 = new AIPlayer2();
        this.p1starts = p1starts;
    }

    public int run() {
        Board board = new Board(p1, p2, p1starts);
        int lastIndex = -1;
        while(!board.isGameOver()) {
            Player current = board.getCurrentPlayer();
            int index = current.getSowIndex(board, lastIndex);
            board = board.getBoardForMove(index);
            lastIndex = index;
        }
        board.finalize();
        int p1store = board.getStore(p1);
        int p2store = board.getStore(p2);
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
