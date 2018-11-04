package tic.tac.toe;

import tic.tac.toe.interfaces.Player;

public class TicTacToeGameFactory {
	public static TicTacToeGame createTicTacToe(int rows, int columns, Player[] players){
		return new TicTacToeGame(rows, columns, players);
	}

}
