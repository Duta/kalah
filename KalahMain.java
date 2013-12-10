public class KalahMain {
    public static void main(String[] args) {
        int numP1wins = 0;
        int numP2wins = 0;
        int numDraws = 0;
        for(int i = 0; i < 1000; i++) {
            Kalah kalah = new Kalah(i % 2 == 0);
            int winner = kalah.run();
            if(winner == 1) {
                numP1wins++;
            } else if(winner == 2) {
                numP2wins++;
            } else {
                numDraws++;
            }
            printState(numP1wins, numP2wins, numDraws, i + 1);
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
    }

    private static void printState(int numP1wins, int numP2wins, int numDraws, int numGames) {
        double p1pct = (100.0*numP1wins)/numGames;
        double p2pct = (100.0*numP2wins)/numGames;
        double drpct = (100.0*numDraws)/numGames;
        System.out.printf(
            "P1: %7.3f%% | P2: %7.3f%% | Draw: %7.3f%% | No. Games: %4d\n",
            p1pct, p2pct, drpct, numGames);
    }

    private static void printStateOld(int numP1wins, int numP2wins, int numDraws, int numGames) {
        int width = 10;
        double factor = (double)width/numGames;
        int a = (int)Math.round(numP1wins * factor);
        int b = (int)Math.round(numP2wins * factor);
        int c = (int)Math.round(numDraws * factor);
        StringBuilder sb = new StringBuilder();
        sb.append('|');
        for(int i = 0; i < a; i++) {
            sb.append('1');
        }
        sb.append('|');
        for(int i = 0; i < b; i++) {
            sb.append('2');
        }
        sb.append('|');
        for(int i = 0; i < c; i++) {
            sb.append('d');
        }
        sb.append("| Num Games: ");
        sb.append(numGames);
        System.out.println(sb.toString());
    }
}
