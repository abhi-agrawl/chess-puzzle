package chesspuzzle.state;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing the state of the puzzle.
 */
@Data
@Slf4j
public class ChessPuzzleState {

    /**
     * The 2D array representing the current state of
     * each chess piece.
     */
    private String[][] currentState = new String[2][3];

    /**
     * The array stores the current row and col
     * position of the one of the Bishop.
     */
    private int[] currentBlackBishop;

    /**
     * The array stores the current row and col
     * position of the other Bishop.
     */
    private int[] currentWhiteBishop;

    /**
     * The array stores the current row and col
     * position of the King.
     */
    private int[] currentKing;

    /**
     * The array stores the current row and col
     * position of the one of the Rook.
     */
    private int[] currentRook1;

    /**
     * The array stores the current row and col
     * position of the other Bishop.
     */
    private int[] currentRook2;

    /**
     * The array stores the current row and col
     * position of the empty place.
     */
    private int[] currentEmpty;

    /**
     * Creates a {@code ChessPuzzleState} object which gets
     * the values of all the chess pieces and call the
     * {@code setInitialState} method to set the value of {@code currentState}.
     * @param currentBlackBishop is the position of bishop.
     * @param currentWhiteBishop is the position of other bishop.
     * @param currentKing is the position of king.
     * @param currentRook1 is the position of rook.
     * @param currentRook2 is the position of other rook.
     * @param currentEmpty is the position of empty.
     */
    public ChessPuzzleState(int[] currentBlackBishop, int[] currentWhiteBishop, int[] currentKing,
                            int[] currentRook1, int[] currentRook2, int[] currentEmpty) {

        this.currentBlackBishop = currentBlackBishop;
        this.currentWhiteBishop = currentWhiteBishop;
        this.currentKing = currentKing;
        this.currentRook1 = currentRook1;
        this.currentRook2 = currentRook2;
        this.currentEmpty = currentEmpty;
        setInitialState();
    }

    /**
     * Creates a {@code ChessPuzzleState} object
     * and sets the default values of
     * each chess pieces.
     */
    public ChessPuzzleState() {
        this.currentBlackBishop = new int[]{0,2};
        this.currentWhiteBishop = new int[]{0,1};
        this.currentKing = new int[]{0,0};
        this.currentRook1 = new int[]{1,0};
        this.currentRook2 = new int[]{1,1};
        this.currentEmpty = new int[]{1,2};
    }

    /**
     * Set the initial state of the chess pieces into
     * an 2D array {@code currentState}
     */
    public void setInitialState(){
        currentState[currentKing[0]][currentKing[1]] = "    King     ";
        currentState[currentWhiteBishop[0]][currentWhiteBishop[1]] = "White Bishop ";
        currentState[currentBlackBishop[0]][currentBlackBishop[1]] = "Black Bishop ";
        currentState[currentRook1[0]][currentRook1[1]] = "   Rook 1    ";
        currentState[currentRook2[0]][currentRook2[1]] = "   Rook 2    ";
        currentState[currentEmpty[0]][currentEmpty[1]] = "     -       ";
    }

    /**
     * Checks whether the puzzle is solved.
     *
     * @return {@code true} if the puzzle is solved,
     *         {@code false} otherwise.
     */
    public boolean isGoalState(){

        String[][] goalState = new String[2][3];

        goalState[0][0] = "Bishop";
        goalState[0][1] = "Bishop";
        goalState[0][2] = "-";
        goalState[1][0] = "Rook";
        goalState[1][1] = "Rook";
        goalState[1][2] = "King";

        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                if(!currentState[i][j].contains(goalState[i][j]))
                    return false;
            }
        }
        return true;
    }


    /**
     * Gets which chess piece is clicked and call
     * the respective method. to check if the chess
     * piece can be move to empty place.
     * @param row the row value of selected chess piece.
     * @param col the col value of selected chess piece.
     * @return {@code true} if its a possible move,
     *         {@code false} otherwise.
     */
    public boolean canMoveToEmptySpace(int row, int col){

        String chessPieceClicked = currentState[row][col];

        if(chessPieceClicked.contains("King"))
            if (canMoveHorizontalAndVertical(currentKing) || canMoveDiagonal(currentKing)){
                return true;
            }

        if(chessPieceClicked.contains("White Bishop"))
            if (canMoveDiagonal(currentWhiteBishop)){
                return true;
            }
        if(chessPieceClicked.contains("Black Bishop"))
            if(canMoveDiagonal(currentBlackBishop)){
                return true;
            }

        if(chessPieceClicked.contains("Rook 1"))
            if(canMoveHorizontalAndVertical(currentRook1)) {
                return true;
            }
        if(chessPieceClicked.contains("Rook 2"))
            if(canMoveHorizontalAndVertical(currentRook2)) {
                return true;
            }

        return false;
    }

    /**
     * Checks if its possible for Rooks or King to move
     * Horizontally or Vertically to empty space.
     * @param selectedMove represents the chess piece
     *                     which is selected to move.
     * @return {@code true} if its a possible move,
     *         {@code false} otherwise.
     */
    private boolean canMoveHorizontalAndVertical(int[] selectedMove){

        int i = selectedMove[0] == 0 ? 1 : -1;

        if(selectedMove[0] + i == currentEmpty[0] && selectedMove[1] == currentEmpty[1])
            return true;

        if(selectedMove[0] == currentEmpty[0] && selectedMove[1] + 1 == currentEmpty[1])
            return true;

        return selectedMove[0] == currentEmpty[0] && selectedMove[1] - 1 == currentEmpty[1];
    }

    /**
     * Checks if its possible for Bishops or King to move
     * Diagonally to empty space.
     * @param selectedMove represents the chess piece
     *                     which is selected to move.
     * @return {@code true} if its a possible move,
     *         {@code false} otherwise.
     */
    private boolean canMoveDiagonal(int[] selectedMove){

        int i = selectedMove[0] == 0 ? 1 : -1;

        if(selectedMove[0] + i == currentEmpty[0] && selectedMove[1] + 1 == currentEmpty[1])
            return true;

        return selectedMove[0] + i == currentEmpty[0] && selectedMove[1] - 1 == currentEmpty[1];
    }

    /**
     * The method swap the places of the chess piece
     * clicked with the empty space.
     * @param row the row value of selected chess piece.
     * @param col the col value of selected chess piece.
     */
    public void moveToEmptySpace(int row, int col){

        String chessPieceClicked = currentState[row][col];

        log.info("Chess Piece({}) at ({},{}) is moved to empty place.",chessPieceClicked,row, col);

        int[] selectedMove = new int[2];

        if(chessPieceClicked.contains("King"))
            selectedMove = currentKing;

        if(chessPieceClicked.contains("White Bishop"))
            selectedMove = currentWhiteBishop;

        if(chessPieceClicked.contains("Black Bishop"))
            selectedMove = currentBlackBishop;

        if(chessPieceClicked.contains("Rook 1"))
            selectedMove = currentRook1;

        if(chessPieceClicked.contains("Rook 2"))
            selectedMove = currentRook2;


        int intTmp;

        intTmp = selectedMove[0];
        selectedMove[0] = currentEmpty[0];
        currentEmpty[0] = intTmp;

        intTmp = selectedMove[1];
        selectedMove[1] = currentEmpty[1];
        currentEmpty[1] = intTmp;

        String stringTmp = currentState[selectedMove[0]][selectedMove[1]];
        currentState[selectedMove[0]][selectedMove[1]] = currentState[currentEmpty[0]][currentEmpty[1]];
        currentState[currentEmpty[0]][currentEmpty[1]] = stringTmp;

    }

    /**
     * Overrides the {@code toString} method.
     * @return {@code String} of the {@code ChessPuzzleState}
     *          object.
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("----------------------------------------------\n");

        for (int i=0; i<2;i++){
            for(int j=0;j<3;j++){
                stringBuilder.append("| ").append(currentState[i][j]);
            }
            stringBuilder.append("| \n----------------------------------------------\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        ChessPuzzleState chessPuzzleState = new ChessPuzzleState();
        System.out.println(chessPuzzleState);
    }
}
