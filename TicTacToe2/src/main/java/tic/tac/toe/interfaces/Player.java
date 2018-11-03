package tic.tac.toe.interfaces;

import tic.tac.toe.model.Position;
import tic.tac.toe.model.TicTacToeGame;

public interface Player {
	
	public Position play(TicTacToeGame game);
	public char getPlayerCharacter();

}
