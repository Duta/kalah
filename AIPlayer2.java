public class AIPlayer2 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;

    public AIPlayer2(Player other) {
        super(other);
    }

    @Override
    public int getSowIndex(Board board, int lastMove) {
        return -1;
    }
}
