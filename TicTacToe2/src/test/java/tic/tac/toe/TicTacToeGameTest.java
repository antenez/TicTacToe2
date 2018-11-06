package tic.tac.toe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import tic.tac.toe.interfaces.Player;
import tic.tac.toe.model.HumanPlayer;
import tic.tac.toe.model.Position;

@RunWith(PowerMockRunner.class)
@PrepareForTest
public class TicTacToeGameTest {

	private TicTacToeGame game = null;

	@Before
	public void prepareGame() {

	}

	@Mock
	Player p1;
	@Mock
	Player p2;

	@Before
	public void initialize() {
		PowerMockito.when(p1.getPlayerCharacter()).thenReturn('X');
		PowerMockito.when(p2.getPlayerCharacter()).thenReturn('O');
	}

//	@After
//	public void clean() {
//		p1 = null;
//		p2 = null;
//	}

	@Test
	public void testTicTacToeGameParametersValidation() {
		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;

		try {
			game = new TicTacToeGame(5, 4, players);

		} catch (Exception e) {
			fail("Game construction fail ");
		}
		try {
			game = new TicTacToeGame(2, 5, players);
			fail("Game should have >2  and <=10 row/columns ");
		} catch (IllegalArgumentException e) {
		}

		try {
			game = new TicTacToeGame(11, 5, players);
			fail("Game should have >2  and <=10 row/columns ");
		} catch (IllegalArgumentException e) {
		}
		try {
			game = new TicTacToeGame(5, 2, players);
			fail("Game should have >2  and <=10 row/columns ");
		} catch (IllegalArgumentException e) {
		}
		try {
			game = new TicTacToeGame(5, 11, players);
			fail("Game should have >2  and <=10 row/columns ");
		} catch (IllegalArgumentException e) {
		}

		// Test less players then 2
		players = new Player[1];
		players[0] = p1;
		try {
			game = new TicTacToeGame(5, 11, players);
			fail("Game should have more then 1 player");
		} catch (IllegalArgumentException e) {
		}

	}

	// @Ignore("This test is ignored because mock and power mock issues on spyed
	// game.playgame method")
	@Test
	public void testTicTacToeGame() {
		// Sample
		// XOX
		// OXO
		// OXX

		Player[] players = new Player[2];
		players[0] = p1;
		players[1] = p2;

		game = spy(new TicTacToeGame(3, 3, players));

		char expectedWinner;
		if (game.getCurrentPlayer().getPlayerCharacter() == 'X') {
			expectedWinner = 'X';
			PowerMockito.when(p1.play(any())).thenReturn(new Position(2, 2), new Position(1, 1), new Position(1, 3),
					new Position(3, 2), new Position(3, 3));

			PowerMockito.when(p2.play(any())).thenReturn(new Position(2, 1), new Position(3, 1), new Position(2, 3),
					new Position(1, 2));
		} else {
			// because dynamic first player selection we switch player moves.
			expectedWinner = 'O';
			PowerMockito.when(p2.play(any())).thenReturn(new Position(2, 2), new Position(1, 1), new Position(1, 3),
					new Position(3, 2), new Position(3, 3));

			PowerMockito.when(p1.play(any())).thenReturn(new Position(2, 1), new Position(3, 1), new Position(2, 3),
					new Position(1, 2));
		}

		Optional<Player> winner = game.playTheGame();
		assertEquals(true, winner.isPresent());
		assertEquals("Winner is not as expected!", expectedWinner, winner.get().getPlayerCharacter());
	}

	@Test
	public void testGetGameField() {
		Player[] p = new Player[2];
		p[0] = new HumanPlayer('X');
		p[1] = new HumanPlayer('O');

		game = new TicTacToeGame(5, 4, p);

		assertEquals("Number of rows not correct", 5, game.getGameField().length);
		assertEquals("Number of rows not correct", 4, game.getGameField()[0].length);
		for (int r = 0; r < game.getGameField().length; r++) {
			for (int c = 0; c < game.getGameField()[0].length; c++) {
				assertEquals(game.getGameField()[r][c], TicTacToeGame.DEFAULT_VALUE);
			}
		}
	}

}
