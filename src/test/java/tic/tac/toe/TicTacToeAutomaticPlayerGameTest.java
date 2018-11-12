package tic.tac.toe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import tic.tac.toe.interfaces.Player;
import tic.tac.toe.model.AutomaticPlayer;

@RunWith(PowerMockRunner.class)
@PrepareForTest
public class TicTacToeAutomaticPlayerGameTest {

	private TicTacToeGame game = null;

	@Before
	public void prepareGame() {

	}

	@Test
	public void testTicTacToeAutomaticGamePlaying() {
		Player[] players = new Player[3];
		players[0] = new AutomaticPlayer('X');
		players[1] = new AutomaticPlayer('O');
		players[2] = new AutomaticPlayer('P');
		
		
		final AtomicInteger nuberOfCompletedGames = new AtomicInteger(0);
		final AtomicInteger nuberOfTiedGames = new AtomicInteger(0);
		final AtomicInteger nuberOffails = new AtomicInteger(0);
		
		TicTacToeGame game1 = new TicTacToeGame(5, 5, players);
		Optional<Player> winner1 = game1.playTheGame();
		
		for (int i = 0; i < 10; i++) {
				try {
					TicTacToeGame game = new TicTacToeGame(5, 5, players);
					Optional<Player> winner = game.playTheGame();
					nuberOfCompletedGames.incrementAndGet();
					if(winner.isPresent()) {
						System.out.println("Automatic game test. Winner is: "+winner.get().getPlayerCharacter());;
					}else {
						nuberOfTiedGames.incrementAndGet();
					}
				}catch (Exception e) {
					nuberOffails.incrementAndGet();
				}
			
		}
			
		assertEquals("Number of failed games does not match!!!", 0,nuberOffails.get());
		assertEquals("Number of completed games does not match!!!", 10,nuberOfCompletedGames.get());
		assertTrue("Tied games suppose to be less then 4!!!",nuberOfTiedGames.get()<4);
		
		

	}
}
