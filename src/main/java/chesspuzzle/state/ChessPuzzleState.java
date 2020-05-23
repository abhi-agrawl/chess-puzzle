package chesspuzzle.state;

import lombok.Data;

import java.util.Arrays;

@Data
public class ChessPuzzleState {

    private String[][] currentState = new String[2][3];

    private int[] currentBlackBishop;
    private int[] currentWhiteBishop;
    private int[] currentKing;
    private int[] currentRook1;
    private int[] currentRook2;
    private int[] currentEmpty;

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

    public ChessPuzzleState() {
        this.currentBlackBishop = new int[]{0,2};
        this.currentWhiteBishop = new int[]{0,1};
        this.currentKing = new int[]{0,0};
        this.currentRook1 = new int[]{1,0};
        this.currentRook2 = new int[]{1,1};
        this.currentEmpty = new int[]{1,2};
        setInitialState();
    }

    private void setInitialState(){
        currentState[currentKing[0]][currentKing[1]] = "King";
        currentState[currentWhiteBishop[0]][currentWhiteBishop[1]] = "White Bishop";
        currentState[currentBlackBishop[0]][currentBlackBishop[1]] = "Black Bishop";
        currentState[currentRook1[0]][currentRook1[1]] = "Rook 1";
        currentState[currentRook2[0]][currentRook2[1]] = "Rook 2";
        currentState[currentEmpty[0]][currentEmpty[1]] = "-";
    }

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

    public boolean canMoveToEmptySpace(String chessPieceClicked){

        if(chessPieceClicked.equals("King"))
            if (canMoveHorizontalAndVertical(currentKing) || canMoveDiagonal(currentKing)){
                return true;
            }

        if(chessPieceClicked.equals("White Bishop"))
            if (canMoveDiagonal(currentWhiteBishop)){
                return true;
            }
        if(chessPieceClicked.equals("Black Bishop"))
            if(canMoveDiagonal(currentBlackBishop)){
                return true;
            }

        if(chessPieceClicked.equals("Rook 1"))
            if(canMoveHorizontalAndVertical(currentRook1)) {
                return true;
            }
        if(chessPieceClicked.equals("Rook 2"))
            if(canMoveHorizontalAndVertical(currentRook2)) {
                return true;
            }

        return false;
    }

    private boolean canMoveHorizontalAndVertical(int[] selectedMove){

        int i = selectedMove[0] == 0 ? 1 : -1;

        if(selectedMove[0] + i == currentEmpty[0] && selectedMove[1] == currentEmpty[1])
            return true;

        if(selectedMove[0] == currentEmpty[0] && selectedMove[1] + 1 == currentEmpty[1])
            return true;

        return selectedMove[0] == currentEmpty[0] && selectedMove[1] - 1 == currentEmpty[1];
    }

    private boolean canMoveDiagonal(int[] selectedMove){

        int i = selectedMove[0] == 0 ? 1 : -1;

        if(selectedMove[0] + i == currentEmpty[0] && selectedMove[1] + 1 == currentEmpty[1])
            return true;

        return selectedMove[0] + i == currentEmpty[0] && selectedMove[1] - 1 == currentEmpty[1];
    }

    public void moveToEmptySpace(String chessPieceClicked){

        int[] selectedMove = new int[2];

        if(chessPieceClicked.equals("King"))
            selectedMove = currentKing;

        if(chessPieceClicked.equals("White Bishop"))
            selectedMove = currentWhiteBishop;

        if(chessPieceClicked.equals("Black Bishop"))
            selectedMove = currentBlackBishop;

        if(chessPieceClicked.equals("Rook 1"))
            selectedMove = currentRook1;

        if(chessPieceClicked.equals("Rook 2"))
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

    @Override
    public String toString() {
        return "ChessPuzzleState{" +
                " currentBlackBishop=" + Arrays.toString(currentBlackBishop) +
                ", currentWhiteBishop=" + Arrays.toString(currentWhiteBishop) +
                ", currentKing=" + Arrays.toString(currentKing) +
                ", currentRook1=" + Arrays.toString(currentRook1) +
                ", currentRook2=" + Arrays.toString(currentRook2) +
                ", currentEmpty=" + Arrays.toString(currentEmpty) +
                '}';
    }
}
