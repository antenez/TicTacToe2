/**
 * 
 */
package tic.tac.toe.model;

import java.util.Scanner;

import tic.tac.toe.TicTacToeGame;
import tic.tac.toe.interfaces.Player;

/**
 * @author eno
 *
 */
public class HumanPlayer implements Player {
	char plyerCharacter;
	
	public HumanPlayer(char plyerCharacter) {
		super();
		this.plyerCharacter = plyerCharacter;
	}

	/* (non-Javadoc)
	 * @see tic.tac.toe.interfaces.Player#play()
	 */
	public Position play(TicTacToeGame game) {
		Position p = null;
		while(p == null) {
			try {
				System.out.println(String.format("Player:%s, Enter new move in format 'row,column':", this.getPlayerCharacter()+""));
				Scanner sc = new Scanner(System.in);
				String stringPosition = sc.next();
				p = getPositionFromUserInput(stringPosition);
			}catch (IndexOutOfBoundsException e) {
				System.out.println("+++ Input is not vell formatted. Should be in 'row, column' format.");
			}
			catch (Exception e) {
				System.out.println("+++ Unexpected exception occured. "+e.getMessage());
			}
		}
		return p;
	}

	/* (non-Javadoc)
	 * @see tic.tac.toe.interfaces.Player#getPlayerCharacter()
	 */
	public char getPlayerCharacter() {
		return this.plyerCharacter;
	}
	
	/**
	 * @param userInput 'row,column' format
	 * @return
	 */
	private Position getPositionFromUserInput(String userInput) throws NumberFormatException, IndexOutOfBoundsException {
		int row=0, column=0;
		row = Integer.valueOf(userInput.substring(0,userInput.indexOf(",")));	
		column = Integer.valueOf(userInput.substring(userInput.indexOf(",")+1,userInput.length()));
		return new Position(row,column);
	}

}
