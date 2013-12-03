public class AIPlayer1 extends Player {
    private static final int NUM_HOUSES = Kalah.NUM_HOUSES;

    @Override
    public int getSowIndex(Board board, int lastMove) {
        MoveInfo info = alphaBeta(board, 7);
        return info.getMoveIndex();
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
                if(board.getSide(this).isHouseEmpty(i)) {
                    continue;
                }
                Board child = node.getBoardForMove(i);
                double quality = alphaBeta(child, depth-1, alpha, beta, false);
                if(quality > alpha) {
                    alpha = quality;
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
                if(board.getSide(other).isHouseEmpty(i)) {
                    continue;
                }
                Board child = node.getBoardForMove(i);
                double quality = alphaBeta(child, depth-1, alpha, beta, true);
                if(quality < beta) {
                    beta = quality;
                    bestIndex = i;
                }
                if(beta <= alpha) {
                    break;
                }
            }
            return new MoveInfo(bestIndex, beta);
        }
    }

    private double heuristicValue(Board node) {
        //
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
