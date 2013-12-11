public class KalahMain {
    public static void main(String[] args) {
        int numP1wins = 0;
        int numP2wins = 0;
        int numDraws = 0;
        int totalP1store = 0;
        int totalP2store = 0;
        for(int i = 0; i < 1000; i++) {
            Kalah kalah = new Kalah(i % 2 == 0);
            GameResult result = kalah.run();
            Winner winner = result.getWinner();
            if(winner == Winner.PLAYER_ONE) {
                numP1wins++;
            } else if(winner == Winner.PLAYER_TWO) {
                numP2wins++;
            } else {
                numDraws++;
            }
            totalP1store += result.getP1store();
            totalP2store += result.getP2store();
            printState(
                numP1wins,
                numP2wins,
                numDraws,
                totalP1store,
                totalP2store,
                i + 1);
        }
        String victor;
        if(numP1wins > numP2wins) {
            victor = "Player One";
        } else if(numP1wins < numP2wins) {
            victor = "Player Two";
        } else {
            victor = "Draw!";
        }
        System.out.println("P1 wins: " + numP1wins);
        System.out.println("P2 wins: " + numP2wins);
        System.out.println("Draws:   " + numDraws);
        System.out.println("Victor:  " + victor);
        System.out.println("P1 store total: " + totalP1store);
        System.out.println("P2 store total: " + totalP2store);
    }

    private static void printState(int numP1wins, int numP2wins, int numDraws,
            int totalP1store, int totalP2store, int numGames) {
        double p1pct = (100.0*numP1wins)/numGames;
        double p2pct = (100.0*numP2wins)/numGames;
        double drpct = (100.0*numDraws)/numGames;
        System.out.printf(
            "P1: %7.3f%% | P2: %7.3f%% | Draw: %7.3f%% | Total P1 store: %5d | Total P2 store: %5d | No. Games: %4d\n",
            p1pct, p2pct, drpct, totalP1store, totalP2store, numGames);
    }
}
