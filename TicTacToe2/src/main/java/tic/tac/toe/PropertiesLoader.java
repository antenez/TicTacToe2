package tic.tac.toe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import tic.tac.toe.interfaces.Player;
import tic.tac.toe.model.AutomaticPlayer;
import tic.tac.toe.model.HumanPlayer;
import tic.tac.toe.model.Position;
import tic.tac.toe.model.TicTacToeGame;

public class PropertiesLoader {
	public static String PC_PLAYER = "PC";
	public static String PLAYERS_KEY = "tic.tac.toe.players";
	public static String COLUMN_NUM_KEY = "tic.tac.toe.columns";
	public static String ROWS_NUM_KEY = "tic.tac.toe.rows";
	public static String PC_PLAYER_LETTER_KEY = "tic.tac.toe.pcplayletter";
	
	int rows;
	int columns;
	Player[]players;
	
	
	public PropertiesLoader( String configurationPath){
		Properties properties = loadFromFile(configurationPath);
		rows = Integer.valueOf(properties.get(ROWS_NUM_KEY)+"");
		columns = Integer.valueOf( properties.get(COLUMN_NUM_KEY)+"");
		String pcPlayerLetter = (String) properties.get(PC_PLAYER_LETTER_KEY);
		
		String[] playersString = ((String) properties.get(PLAYERS_KEY)).split(",");
		if(pcPlayerLetter!=null) {
			players = new Player[playersString.length+1];
		}else {
			players = new Player[playersString.length];
		}
		
		for(int i=0; i< playersString.length; i++) {
			System.out.println("Created regular player with char: "+playersString[i].charAt(0)
					);
			players[i]=new HumanPlayer(playersString[i].charAt(0));
		}		
		//appendPcPlayer
		if(pcPlayerLetter!=null) {
			System.out.println("Created automatic player with char: "+pcPlayerLetter.charAt(0));
			players[players.length-1] = new AutomaticPlayer(pcPlayerLetter.charAt(0));
		}
	}
	
	
	public Properties loadFromFile(String configurationPath) {
		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(configurationPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appProps;
	}


	public int getRows() {
		return rows;
	}


	public int getColumns() {
		return columns;
	}


	public Player[] getPlayers() {
		return players;
	}

}
