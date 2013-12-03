public abstract class Player {
    protected Player other;
    
    public Player(Player other) {
        this.other = other;
    }

    public abstract int getSowIndex(Board board, int lastMove);
}
