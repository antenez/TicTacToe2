package tic.tac.toe;

import tic.tac.toe.model.TicTacToeGame;

public class Main {
	
	public static void main(String[] args) {
		PropertiesLoader propertiesLoader = new PropertiesLoader("/home/eno/development/workspaces/STS40/WTGWorkspace/TicTacToe2/src/main/resources/configuration.properties");
		TicTacToeGameFactory gameFactory = new TicTacToeGameFactory();
		
		TicTacToeGame game = gameFactory.createTicTacToe(propertiesLoader.getRows(), 
				propertiesLoader.getColumns(), propertiesLoader.getPlayers());
		game.playTheGame();
		

	}

}
