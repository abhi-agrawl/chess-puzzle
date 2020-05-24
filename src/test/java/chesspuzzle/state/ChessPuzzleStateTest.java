package chesspuzzle.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPuzzleStateTest {

    private void assertEmptySpace(int expectedEmptyRow, int expectedEmptyCol, ChessPuzzleState state) {
        assertAll(
                () -> assertEquals(expectedEmptyRow, state.getCurrentEmpty()[0]),
                () -> assertEquals(expectedEmptyCol, state.getCurrentEmpty()[1])
        );
    }

    private void assertRandomGoalState(ChessPuzzleState chessPuzzleState){
        chessPuzzleState.setInitialState();
        assertTrue(chessPuzzleState.isGoalState());
    }

    @Test
    void testIsGoalState() {

        ChessPuzzleState state = new ChessPuzzleState();
        state.setInitialState();
        assertFalse(state.isGoalState());

        state = new ChessPuzzleState(new int[]{0,0},
                new int[]{0,1}, new int[]{1,2},
                new int[]{1,0}, new int[]{1,1},
                new int[]{0,2});
        assertRandomGoalState(state);

        state = new ChessPuzzleState(new int[]{0,0},
                new int[]{0,1}, new int[]{1,2},
                new int[]{1,1}, new int[]{1,0},
                new int[]{0,2});
        assertRandomGoalState(state);

        state = new ChessPuzzleState(new int[]{0,1},
                new int[]{0,0}, new int[]{1,2},
                new int[]{1,0}, new int[]{1,1},
                new int[]{0,2});
        assertRandomGoalState(state);

        state = new ChessPuzzleState(new int[]{0,1},
                new int[]{0,0}, new int[]{1,2},
                new int[]{1,1}, new int[]{1,0},
                new int[]{0,2});
        assertRandomGoalState(state);
    }

    @Test
    void testCanMoveToEmptySpace() {
        ChessPuzzleState state = new ChessPuzzleState();
        state.setInitialState();

        assertEmptySpace(1,2, state);
        assertFalse(state.canMoveToEmptySpace(0,0));
        assertTrue(state.canMoveToEmptySpace(0,1));
        assertFalse(state.canMoveToEmptySpace(0,2));
        assertFalse(state.canMoveToEmptySpace(1,0));
        assertTrue(state.canMoveToEmptySpace(1,1));
        assertFalse(state.canMoveToEmptySpace(1,2));
    }

    @Test
    void testMoveToEmptySpace() {

        ChessPuzzleState state = new ChessPuzzleState();
        state.setInitialState();

        assertEmptySpace(1,2, state); // just to make sure empty space is in right bottom corner

        state.moveToEmptySpace(1,1); // let's move the rook to empty space
        assertEmptySpace(1,1, state); // the empty space now should be (1,1)

        state.moveToEmptySpace(0,0); // let's move the king to empty space
        assertEmptySpace(0,0, state); // the empty space now should be (0, 0)

        state.moveToEmptySpace(1,0); // let's move the rook to empty space
        assertEmptySpace(1,0, state); // the empty space now should be (1,0)

        state.moveToEmptySpace(0,1); // let's move the bishop to empty space
        assertEmptySpace(0,1, state); // the empty space now should be (0,1)

        state.moveToEmptySpace(1,1); // let's move the king to empty space
        assertEmptySpace(1,1, state); // the empty space now should be (1,1) again

        state.moveToEmptySpace(0,2); // let's move the bishop to empty space
        assertEmptySpace(0,2, state); // the empty space now should be (0,2)

        state.moveToEmptySpace(1,2); // let's move the rook to empty space
        assertEmptySpace(1,2, state); //// the empty space now should be (1,2) again

    }

    @Test
    void testToString() {
        ChessPuzzleState state = new ChessPuzzleState();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("----------------------------------------------\n");
        for (int i=0; i<2;i++){
            for(int j=0;j<3;j++){
                stringBuilder.append("|").append(state.getCurrentState()[i][j]);
            }
            stringBuilder.append("|\n----------------------------------------------\n");
        }

        assertEquals(stringBuilder.toString(), state.toString());
    }
}