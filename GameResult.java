public class GameResult {
    private Winner winner;
    private int p1store;
    private int p2store;

    public GameResult(Winner winner, int p1store, int p2store) {
        this.winner = winner;
        this.p1store = p1store;
        this.p2store = p2store;
    }

    public Winner getWinner() {
        return winner;
    }

    public int getP1store() {
        return p1store;
    }

    public int getP2store() {
        return p2store;
    }
}
