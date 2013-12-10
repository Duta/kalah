import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class AIPlayer1 extends Player {
    private static final int
        NUM_HOUSES = Kalah.NUM_HOUSES,
        MINIMAX_DEPTH = 5,
        MAX_NUM_RECENT_STATES = 500;
    private static final double
        MINIMAX_PCT = 1.0;

    private List<Board> winMoves;
    private List<Board> drawMoves;
    private List<Board> lossMoves;
    private List<Board> currMoves;
    private Random rgen;

    public AIPlayer1() {
        winMoves  = new ArrayList<Board>();
        drawMoves = new ArrayList<Board>();
        lossMoves = new ArrayList<Board>();
        currMoves = new ArrayList<Board>();
        rgen = new Random();
    }

    @Override
    public int getSowIndex(Board board, int lastMove) {
        currMoves.add(board);
        if(rgen.nextDouble() <= MINIMAX_PCT) {
            MoveInfo info = alphaBeta(board, MINIMAX_DEPTH);
            return info.getMoveIndex();
        } else {
            int choice;
            do {
                choice = rgen.nextInt(NUM_HOUSES);
            } while(board.getSide(this).isHouseEmpty(choice));
            return choice;
        }
    }

    @Override
    public void gameFinished(int winner) {
        if(winner == 1) {
            winMoves.addAll(currMoves);
            trimList(winMoves, MAX_NUM_RECENT_STATES);
        } else if(winner == 2) {
            lossMoves.addAll(currMoves);
            trimList(lossMoves, MAX_NUM_RECENT_STATES);
        } else {
            drawMoves.addAll(currMoves);
            trimList(drawMoves, MAX_NUM_RECENT_STATES);
        }
        currMoves.clear();
    }

    private void trimList(List<Board> list, int size) {
        while(list.size() > size) {
            list.remove(0);
        }
    }

    private MoveInfo alphaBeta(Board origin, int maxDepth) {
        return alphaBeta(origin, maxDepth,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true);
    }

    private MoveInfo alphaBeta(Board node, int depth, double alpha, double beta,
            boolean maximizing) {
        if(depth == 0 || node.isGameOver()) {
            return new MoveInfo(-1, heuristicValue(node));
        }
        if(maximizing) {
            int bestIndex = -1;
            for(int i = 0; i < NUM_HOUSES; i++) {
                if(node.getSide(this).isHouseEmpty(i)) {
                    continue;
                }
                Board child = node.getBoardForMove(i);
                boolean maxNext = node.getCurrentPlayer() == child.getCurrentPlayer();
                MoveInfo info = alphaBeta(child, depth-1, alpha, beta, maxNext);
                if(info.getMoveQuality() > alpha) {
                    alpha = info.getMoveQuality();
                    bestIndex = i;
                }
                if(beta <= alpha) {
                    break;
                }
            }
            return new MoveInfo(bestIndex, alpha);
        } else {
            int bestIndex = -1;
            for(int i = 0; i < NUM_HOUSES; i++) {
                if(node.getSide(other).isHouseEmpty(i)) {
                    continue;
                }
                Board child = node.getBoardForMove(i);
                boolean maxNext = node.getCurrentPlayer() != child.getCurrentPlayer();
                MoveInfo info = alphaBeta(child, depth-1, alpha, beta, maxNext);
                if(info.getMoveQuality() < beta) {
                    beta = info.getMoveQuality();
                    bestIndex = i;
                }
                if(beta <= alpha) {
                    break;
                }
            }
            return new MoveInfo(bestIndex, beta);
        }
    }

    /**
     * < 0 -> Loss
     *   0 -> Draw
     * > 0 -> Win
     */
    private double heuristicValue(Board node) {
        double heuristic = 0.0;
        // Basic heuristic
        heuristic += 0.2*(node.getSide(this).getStore() - node.getSide(other).getStore());
        // Factor in learning
        heuristic += 5.0*bestMatch(node, winMoves);
        heuristic -= 5.0*bestMatch(node, lossMoves);
        heuristic /= 1 + bestMatch(node, drawMoves);
        return heuristic;
    }

    private double bestMatch(Board board, List<Board> matches) {
        double bestMatch = 0.0;
        for(int i = 0; i < matches.size(); i++) {
            double match = compareBoards(board, matches.get(i));
            if(match > bestMatch) {
                bestMatch = match;   
            }
        }
        return bestMatch;
    }

    private double compareBoards(Board b1, Board b2) {
        return 0.5*compareSides(b1.getSide(this), b2.getSide(this))
            +  0.5*compareSides(b1.getSide(other), b2.getSide(other));
    }

    private double compareSides(Board.Side s1, Board.Side s2) {
        int diff = 0;
        for(int i = 0; i < NUM_HOUSES; i++) {
            if(s1.getHouse(i) != s2.getHouse(i)) {
                diff++;
            }
        }
        if(s1.getStore() != s2.getStore()) {
            diff++;
        }
        return 1 - diff/7.0;
    }

    private static class MoveInfo {
        private int moveIndex;
        private double moveQuality;

        public MoveInfo(int index, double quality) {
            moveIndex = index;
            moveQuality = quality;
        }

        public int getMoveIndex() {
            return moveIndex;
        }

        public double getMoveQuality() {
            return moveQuality;
        }
    }
}
