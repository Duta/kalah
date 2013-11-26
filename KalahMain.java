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
}
