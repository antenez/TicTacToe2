package tic.tac.toe.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import tic.tac.toe.interfaces.Player;

public class TicTacToeGame {
	public static final char DEFAULT_VALUE = '-';
	
	private char gameFiled[][]=null;
	private Player[] players;
	private List<Position>history = null;
	private int currentPlayerInd = 0;
	
	public TicTacToeGame(int numberOfRows, int numberOfColumns, Player[] players) throws IllegalArgumentException{
		super();
		
		if(!validateBoardDimensions(numberOfRows, numberOfColumns)) {
			throw new IllegalArgumentException("Columns and rows should >= 3 and <= 10");
		}
		if(!validateNumberOfPlayers(players)) {
			throw new IllegalArgumentException("Game should have at least two players");
		}

		initializeGameField(numberOfRows,numberOfColumns);

		history = new ArrayList<>();
		this.players = players;
		
		System.out.println("Game is configured.");
		Random rand = new Random();
		currentPlayerInd = rand.nextInt(players.length);	
		System.out.println("Current player is "+String.valueOf(getCurrentPlayer().getPlayerCharacter()));
	}
	
	private void initializeGameField(int numberOfRows, int numberOfColumns) {
		this.gameFiled =  new char[numberOfRows][numberOfColumns];

		for(int i=0 ; i< this.gameFiled.length; i++) {
			for(int j = 0 ; j < this.gameFiled[i].length ; j++) {
				this.gameFiled[i][j] = DEFAULT_VALUE;
			}
		}
		
	}
	
	public int getPlayedPositionCounter() {
		return this.history.size();
	}
	
	private boolean validateBoardDimensions(int rows, int columns) {
		if(rows<=2 || rows>10 || columns<=2 || columns>10) return false;
		return true;
	}
	
	private boolean validateNumberOfPlayers(Player[] p) {
		if(p.length< 2) return false;
		return true;
	}
	
	
	
	/**
	 * Was considering to traverse over positions in history and recursion
	 * Will not check first and last row because wee are checking one up and down left right and diagonal.
	 * @param rowNum
	 * @param columnNum
	 * @return
	 */
	private boolean checkEqualsAroundPosition(int rowNum , int columnNum) {
		
		if(rowNum<=1 || rowNum>=this.gameFiled.length || columnNum <= 0 || columnNum >= this.gameFiled[0].length ) return false;
		if(this.gameFiled[rowNum-1][columnNum-1] == DEFAULT_VALUE) return false;
		
		//horizontal
		if(gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum-1][columnNum-2] 
				&& gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum-1][columnNum]) return true;
		//vertical
		if(gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum-2][columnNum-1] 
				&& gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum][columnNum]-1) return true;
		//lDiagonal
		if(gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum-2][columnNum-2] 
				&& gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum][columnNum]) return true;
		//rDiagonal
		if(gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum][columnNum-2] 
				&& gameFiled[rowNum-1][columnNum-1] == gameFiled[rowNum-2][columnNum]) return true;
		
		return false;
		
	}
	
	public boolean isGameCompleted() {
		for (Position p : history) {
			checkEqualsAroundPosition(p.getRow(), p.getColumn());
		}
		return false;
	}
	
	public void printBoard() {
		System.out.println("++++++++++Printing full board+++++++++++++");
		for(int i=0 ; i< this.gameFiled.length; i++) {
			System.out.println(this.gameFiled[i]);
		}
	}
	
	public void addValueOnBoard(Position p, char value) {
		this.gameFiled[p.row-1][p.column-1] = value;
		this.history.add(p);
	}
	
	public void playTheGame() {
		System.out.println("+++Game is started+++");
		while(!this.isGameCompleted()){
			Player player = getCurrentPlayer();
			Position position = player.play(this);
			try {
				validatePlayedPosition(position);
				addValueOnBoard(position, player.getPlayerCharacter());
				printBoard();
				incrementPlayerIndex();
			}catch (IllegalArgumentException e) {
				System.out.println("+++Invalid input, +++"+e.getMessage());
				System.out.println("+++Invalid input, play again+++");
			}			
		}
	}
	
	private Player getCurrentPlayer() {
		if(currentPlayerInd == 0) {
			return this.players[currentPlayerInd];
		}else return this.players[currentPlayerInd-1];
		
	}
	
	private Player getPreviousPlayer() {
		if( currentPlayerInd == 0 ) {
			return this.players[players.length-1];
		}
		return this.players[currentPlayerInd-1];
	}
	
	private void incrementPlayerIndex() {
		if( currentPlayerInd == players.length-1 ) {
			currentPlayerInd = 0;
		}
		currentPlayerInd++;
	}
	
	private void validatePlayedPosition(Position position)throws IllegalArgumentException {
		if(!isPosotionInScope(position)) throw new IllegalArgumentException("Position "+position+" exceeds board size");
		if(DEFAULT_VALUE != this.gameFiled[position.row-1][position.column-1]) throw new IllegalArgumentException("Position "+position+" is already used"); 
	}

	private boolean isPosotionInScope(Position position) {
		if(position.getRow()<0 || position.getRow()> gameFiled.length-1
				|| position.getColumn() < 0 
				|| position.getColumn() > gameFiled[0].length-1) {
			return false;
		}
		return true;
	}

	public char[][] getGameFiled() {
		return gameFiled;
	}
}
