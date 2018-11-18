package tic.tac.toe;

import java.util.Optional;

import tic.tac.toe.interfaces.Player;

public class Main {
	
	public static void main(String[] args) {
		PropertiesLoader propertiesLoader = new PropertiesLoader("/home/eno/development/workspaces/STS40/Demo/TicTacToe2/src/main/resources/configuration.properties");
		TicTacToeGame game  = null;
		
		if(propertiesLoader.isCornerGame()) {
			game = TicTacToeGameFactory.createTicTacToeCornerCase(propertiesLoader.getRows(), 
					propertiesLoader.getColumns(), propertiesLoader.getPlayers());
			
		}else{
		game = TicTacToeGameFactory.createTicTacToe(propertiesLoader.getRows(), 
				propertiesLoader.getColumns(), propertiesLoader.getPlayers());
		}
		Optional<Player>winner =game.playTheGame();
		if(winner.isPresent()) {
			System.out.println("+++ WINNER IS PLAYER  :"+winner.get().getPlayerCharacter()+"  +++");
		}else {
			System.out.println("+++ GAME ENDED WITHOUT WINNER+++");
		}
		
		
		

	}

}
