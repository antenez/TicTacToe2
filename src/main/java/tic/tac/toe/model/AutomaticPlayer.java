/**
 * 
 */
package tic.tac.toe.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.plaf.ListUI;

import tic.tac.toe.TicTacToeGame;
import tic.tac.toe.interfaces.Player;

/**
 * @author eno
 *
 */
public class AutomaticPlayer implements Player {
	char plyerCharacter;

	public AutomaticPlayer(char plyerCharacter) {
		super();
		this.plyerCharacter = plyerCharacter;
	}

	/* (non-Javadoc)
	 * @see tic.tac.toe.interfaces.Player#play()
	 */
	public Position play(TicTacToeGame game) {
		Optional<Position> response = getNextMove(game);
		if(response.isPresent()) {
			Position p = response.get();
			System.out.println("Automatic player select position "+ p);
			return new Position(p.getRow()+1 , p.getColumn()+1); //game handler expect index to start from 1
		}
		System.out.println("Automatic player was not able to chose position.");
		return null;
	}

	private Optional<Position> getNextMove(TicTacToeGame game) {
		Optional<Position> p =  this.playMyWinningMove(game);
		if(!p.isPresent()) { p = this.playDefense(game);}
		if(!p.isPresent()) { p = this.playMyRegularMove(game);}
		return p;
	}
	
	private Set<String> tmpVisitedPositions; //We check all empty around played field
	/**
	 * Will return defense position if it is necessary, otherwise null;
	 * @param game
	 * @return
	 */
	private Optional<Position> playDefense(final TicTacToeGame game) {
		Optional<Position> response = Optional.empty();

		tmpVisitedPositions = new HashSet<>();
		for (int r = 0; r < game.getGameField().length; r++  ) {
			for (int c = 0; c < game.getGameField()[0].length; c++  ) {
				char currentChar = game.getGameField()[r][c];
				
				//in defense mode we are searching for used fields
				if(currentChar==TicTacToeGame.DEFAULT_VALUE || currentChar == this.plyerCharacter) {
					continue;
				}
				
				for(Position p : getPositionsInFieldAround(new Position(r, c), game.getGameField())) {
					if(game.getGameField()[p.getRow()][p.getColumn()]==TicTacToeGame.DEFAULT_VALUE
							&& !tmpVisitedPositions.contains(p.toString())){
						
						tmpVisitedPositions.add(p.toString());
						if(checkWinningConditionsForChar(currentChar, p, game.getGameField())) {
							response = Optional.of(p);
							break;
						}	
						
					}else {
						continue;
					}
				}
			}
		}
		
		return response;
	}
	
	/**
	 * Will return defense position if it is necessary, otherwise null;
	 * @param game
	 * @return
	 */
	private Optional<Position> playMyWinningMove(final TicTacToeGame game) {
		Optional<Position> response = Optional.empty();
		tmpVisitedPositions = new HashSet<>();
		
		for (int r = 0; r < game.getGameField().length; r++  ) {
			for (int c = 0; c < game.getGameField()[0].length; c++  ) {
				char currentChar = game.getGameField()[r][c];
				
				//in offense mode we are searching for this player char
				if(currentChar != this.plyerCharacter) {
					continue;
				}
				
				for(Position p : getPositionsInFieldAround(new Position(r, c), game.getGameField())) {
					if(!tmpVisitedPositions.contains(p.toString())){
						tmpVisitedPositions.add(p.toString());
						if(game.getGameField()[p.getRow()][p.getColumn()]==TicTacToeGame.DEFAULT_VALUE
								&& checkWinningConditionsForChar(currentChar, p, game.getGameField())) {
							response = Optional.of(p);
							break;
						}					
					}else {
						continue;
					}
				}
			}
		}
		return response;
	}
	
	/**
	 * Will return regular move position if it is necessary, otherwise null;
	 * @param game
	 * @return
	 */
	private Optional<Position> playMyRegularMove(final TicTacToeGame game) {
		Optional<Position> response = Optional.empty();
		tmpVisitedPositions = new HashSet<>();
		
		for (int r = 0; r < game.getGameField().length; r++  ) {
			for (int c = 0; c < game.getGameField()[0].length; c++  ) {
				char currentChar = game.getGameField()[r][c];
				
				//In regular move we are searching for current player values
				if(currentChar != this.plyerCharacter) {
					continue;
				}
				
				for(Position p : getPositionsInFieldAround(new Position(r, c), game.getGameField())) {
					if(game.getGameField()[p.getRow()][p.getColumn()] == TicTacToeGame.DEFAULT_VALUE
							&& !tmpVisitedPositions.contains(p.toString())){
						
						tmpVisitedPositions.add(p.toString());
						if(checkWinningConditionsForChar(currentChar, p, game.getGameField())) {
							response = Optional.of(p);
							break;
						}					
					}else {
						continue;
					}
				}
			}
		}
		
		//get first free from center
		while(!response.isPresent()) {
			Random rand = new Random();
			Position p = new Position(rand.nextInt(game.getGameField().length), rand.nextInt(game.getGameField()[0].length));
			if(game.getGameField()[p.getRow()][p.getColumn()] == TicTacToeGame.DEFAULT_VALUE) {
				response = Optional.of(p);
			}
		}	
		
		return response;
	}

	private List<Position> getPositionsInFieldAround(Position p, char gameField[][]){
		//if position is out of scope
		if(p.getRow()<0 || p.getRow()>gameField.length-1
				|| p.getColumn() < 0 || p.getColumn() > gameField[0].length-1 ) {
			return Collections.EMPTY_LIST;
		}
		List<Position> response = new ArrayList<>();
		if(p.getRow()!=0) {
			//add left and right diagonal corners
			if(p.getColumn()!=0) {
				response.add(new Position(p.getRow()-1, p.getColumn()-1));
			}
			if(p.getColumn()!=gameField[0].length-1) {
				response.add(new Position(p.getRow()-1, p.getColumn()+1));
			}
			//Add one above
			response.add(new Position(p.getRow()-1, p.getColumn()));
			
		}
		if(p.getRow()!=gameField.length-1) {
			//add left and right diagonal corners
			if(p.getColumn()!=0) {
				response.add(new Position(p.getRow()+1, p.getColumn()-1));
			}
			if(p.getColumn()!=gameField[0].length-1) {
				response.add(new Position(p.getRow()+1, p.getColumn()+1));
			}
			//add one below
			response.add(new Position(p.getRow()+1, p.getColumn()));
		}
		
		//add left and right
		if(p.getColumn()!=0) {
			response.add(new Position(p.getRow(), p.getColumn()-1));
		}
		if(p.getColumn()!=gameField[0].length-1) {
			response.add(new Position(p.getRow(), p.getColumn()+1));
		}
		return response;
	}
	
	private boolean checkWinningConditionsForChar(final char playerChar,final Position position, final char[][] gamefield) {
		return isTwoBottomLeftDiagonal(playerChar, position, gamefield) 
				|| isTwoBottomRightDiagonal(playerChar, position, gamefield)
				|| isTwoFromDown(playerChar, position, gamefield)
				|| isTwoFromUp(playerChar, position, gamefield)
				|| isTwoFromLeft(playerChar, position, gamefield)
				|| isTwoFromRight(playerChar, position, gamefield)
				|| isTwoBottomRightDiagonal(playerChar, position, gamefield)
				|| isTwoFromDown(playerChar, position, gamefield)
				|| isTwoUpperLeftDiagonal(playerChar, position, gamefield)
				|| isTwoUpperRightDiagonal(playerChar, position, gamefield)
				|| isBetweenHorizontal(playerChar, position, gamefield)
				|| isBetweenLeftDiagonal(playerChar, position, gamefield)
				|| isBetweenRightDiagonal(playerChar, position, gamefield)
				|| isBetweenVertical(playerChar, position, gamefield);
	}
	private boolean isTwoFromRight(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()][position.getColumn()+1] 
					&& playerChar == gamefield[position.getRow()][position.getColumn()+2]) {
				return true;
			}
		}catch(Exception e) {}
		return false;
	}
	
	private boolean isTwoFromLeft(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()][position.getColumn()-1] 
					&& playerChar == gamefield[position.getRow()][position.getColumn()-2]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
	private boolean isTwoFromUp(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()] 
					&& playerChar == gamefield[position.getRow()-2][position.getColumn()]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
//+++++++++++++Botom diagonals	
	private boolean isTwoBottomRightDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()+1][position.getColumn()+1] 
					&& playerChar == gamefield[position.getRow()+2][position.getColumn()+2]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
	private boolean isTwoBottomLeftDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()+1][position.getColumn()-1] 
					&& playerChar == gamefield[position.getRow()+2][position.getColumn()-2]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
//++++++++++++++Upper diagonals
	private boolean isTwoUpperRightDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()+1] 
					&& playerChar == gamefield[position.getRow()-2][position.getColumn()+2]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
	private boolean isTwoUpperLeftDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()-1] 
					&& playerChar == gamefield[position.getRow()-2][position.getColumn()-2]) {
				return true;
			}
		}catch(Exception e) {}

		return false;
	}
	
	private boolean isTwoFromDown(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()+1][position.getColumn()] 
					&& playerChar == gamefield[position.getRow()+2][position.getColumn()]) {
				return true;
			}
		}catch(Exception e) {}

		return false;
	}
	
	private boolean isBetweenHorizontal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()][position.getColumn()+1] 
					&& playerChar == gamefield[position.getRow()][position.getColumn()-1]) {
				return true;
			}
		}catch(Exception e) {}

		return false;
	}
	
	private boolean isBetweenVertical(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()] 
					&& playerChar == gamefield[position.getRow()+1][position.getColumn()]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
	private boolean isBetweenLeftDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()-1] 
					&& playerChar == gamefield[position.getRow()+1][position.getColumn()+1]) {
				return true;
			}
		}catch(Exception e) {}
		
		return false;
	}
	
	private boolean isBetweenRightDiagonal(final char playerChar,final Position position, final char[][] gamefield) {
		try {
			if(playerChar == gamefield[position.getRow()-1][position.getColumn()+1] 
					&& playerChar == gamefield[position.getRow()+1][position.getColumn()-1]) {
				return true;
			}
		}catch(Exception e) {}

		return false;
	}

	/* (non-Javadoc)
	 * @see tic.tac.toe.interfaces.Player#getPlayerCharacter()
	 */
	public char getPlayerCharacter() {
		return this.plyerCharacter;
	}
	
//private	class EvaluatedPosition{
//		int value;
//		Position position;
//		public EvaluatedPosition(int value, Position position) {
//			super();
//			this.value = value;
//			this.position = position;
//		}
//	}

}

