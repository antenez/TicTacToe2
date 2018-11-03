/**
 * 
 */
package tic.tac.toe.model;

import java.util.Scanner;

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
		
		Scanner sc = new Scanner(System.in);
		String stringPosition = sc.next();
		sc.close();
		
		System.out.println("Enter new move in format 'row,column':");
		Position response = null; //TODO implement next move logic according to board
		
		return response;
	}

	private Position calculateBestMove(TicTacToeGame game) {
		
		
		Position p = null;
		
		return p;
		
	}

	/* (non-Javadoc)
	 * @see tic.tac.toe.interfaces.Player#getPlayerCharacter()
	 */
	public char getPlayerCharacter() {
		return this.plyerCharacter;
	}
	
private	class EvaluatedPosition{
		int value;
		Position position;
		public EvaluatedPosition(int value, Position position) {
			super();
			this.value = value;
			this.position = position;
		}
	}

}

