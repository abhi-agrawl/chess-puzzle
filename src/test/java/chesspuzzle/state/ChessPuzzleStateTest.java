package chesspuzzle.state;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ChessPuzzleStateTest {

    private void assertEmptySpace(int expectedEmptyRow, int expectedEmptyCol, ChessPuzzleState state) {
        assertAll(
                () -> assertEquals(expectedEmptyRow, state.getCurrentEmpty()[0]),
                () -> assertEquals(expectedEmptyCol, state.getCurrentEmpty()[1])
        );
    }

    @Test
    void testIsGoalState() {

        assertFalse(new ChessPuzzleState().isGoalState());

        assertTrue(new ChessPuzzleState(new int[]{0,0},
                new int[]{0,1}, new int[]{1,2},
                new int[]{1,0}, new int[]{1,1},
                new int[]{0,2}).isGoalState());

        assertTrue(new ChessPuzzleState(new int[]{0,0},
                new int[]{0,1}, new int[]{1,2},
                new int[]{1,1}, new int[]{1,0},
                new int[]{0,2}).isGoalState());

        assertTrue(new ChessPuzzleState(new int[]{0,1},
                new int[]{0,0}, new int[]{1,2},
                new int[]{1,0}, new int[]{1,1},
                new int[]{0,2}).isGoalState());

        assertTrue(new ChessPuzzleState(new int[]{0,1},
                new int[]{0,0}, new int[]{1,2},
                new int[]{1,1}, new int[]{1,0},
                new int[]{0,2}).isGoalState());
    }

    @Test
    void testCanMoveToEmptySpace() {
        ChessPuzzleState state = new ChessPuzzleState();

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

        assertEquals("ChessPuzzleState{" +
                " currentBlackBishop=" + Arrays.toString(state.getCurrentBlackBishop()) +
                ", currentWhiteBishop=" + Arrays.toString(state.getCurrentWhiteBishop()) +
                ", currentKing=" + Arrays.toString(state.getCurrentKing()) +
                ", currentRook1=" + Arrays.toString(state.getCurrentRook1()) +
                ", currentRook2=" + Arrays.toString(state.getCurrentRook2()) +
                ", currentEmpty=" + Arrays.toString(state.getCurrentEmpty()) +
                '}', state.toString());
    }
}