package tic.tac.toe.interfaces;

import tic.tac.toe.TicTacToeGame;
import tic.tac.toe.model.Position;

public interface Player {
	
	public Position play(TicTacToeGame game);
	public char getPlayerCharacter();

}
