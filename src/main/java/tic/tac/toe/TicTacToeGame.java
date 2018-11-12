package tic.tac.toe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import tic.tac.toe.interfaces.Player;
import tic.tac.toe.model.Position;

public class TicTacToeGame {
	public static final char DEFAULT_VALUE = '-';

	private char gameField[][] = null;
	private Player[] players;
	private List<Position> history = null;
	private int currentPlayerInd = -1;

	public TicTacToeGame(int numberOfRows, int numberOfColumns, Player[] players) throws IllegalArgumentException {
		super();

		if (!validateBoardDimensions(numberOfRows, numberOfColumns)) {
			throw new IllegalArgumentException("Columns and rows should >= 3 and <= 10");
		}
		if (!validateNumberOfPlayers(players)) {
			throw new IllegalArgumentException("Game should have at least two players");
		}

		initializeGameField(numberOfRows, numberOfColumns);

		history = new ArrayList<>();
		this.players = players;

		if (currentPlayerInd == -1) {
			currentPlayerInd = this.getRandomInitialPlayerIndex();
			System.out.println("Initial Current player is " + String.valueOf(getCurrentPlayer().getPlayerCharacter()));
		}

		System.out.println("Game is configured.");
	}

	/**
	 * @return Optional<Player> winner. if does not exist then GAME ended without
	 *         winner.
	 */
	public Optional<Player> playTheGame() {
		System.out.println("+++Game is started+++");
		while (!this.isGameCompleted()) {
			Player player = getCurrentPlayer();
			Position positionHumanReadable = player.play(this);
			Position position = new Position(positionHumanReadable.getRow() - 1, positionHumanReadable.getColumn() - 1);
			try {
				validatePlayedPosition(position);
				if (position == null) {
					System.out.println("Played position is null - or game is over or result is not determined.");
					break;
				}
				addValueOnBoard(position, player.getPlayerCharacter());
				printBoard();
				incrementPlayerIndex();
			} catch (IllegalArgumentException e) {
				System.out.println("+++Invalid input, +++" + e.getMessage());
				System.out.println("+++Invalid input, play again+++");
			}
		}
		return getWinner();
	}

	public boolean isGameCompleted() {
		if (history.size() == (this.gameField.length * this.gameField[0].length)) {
			return true;
		}
		for (int i = history.size() - 1; 0 <= i; i--) {
			Position p = history.get(i);
			if (checkEqualsAroundPosition(p.getRow(), p.getColumn())) {
				return true;
			}
		}
		return false;
	}

	public void printBoard() {
		System.out.println("++++++++++Printing full board+++++++++++++");
		for (int i = 0; i < this.gameField.length; i++) {
			System.out.println(this.gameField[i]);
		}
	}

	private void addValueOnBoard(Position p, char value) {
		this.gameField[p.getRow()][p.getColumn()] = value;
		this.history.add(p);
	}

	/**
	 * Was considering to traverse over positions in history Method ignore any index
	 * out of bounds exception and check do we have three in row in any direction.
	 * 
	 * @param rowNum
	 * @param columnNum
	 * @return
	 */
	private boolean checkEqualsAroundPosition(int rowNum, int columnNum) {

		if (rowNum < 0 || rowNum > this.gameField.length - 1 || columnNum < 0
				|| columnNum > this.gameField[0].length - 1)
			return false;
		if (this.gameField[rowNum][columnNum] == DEFAULT_VALUE)
			return false;

		// horizontal
		try {
			if (gameField[rowNum][columnNum] == gameField[rowNum][columnNum - 1]
					&& gameField[rowNum][columnNum] == gameField[rowNum][columnNum + 1])
				return true;
		} catch (IndexOutOfBoundsException e) {
		}
		// vertical
		try {
			if (gameField[rowNum][columnNum] == gameField[rowNum - 1][columnNum]
					&& gameField[rowNum][columnNum] == gameField[rowNum + 1][columnNum])
				return true;
		} catch (IndexOutOfBoundsException e) {
		}
		// lDiagonal
		try {
			if (gameField[rowNum][columnNum] == gameField[rowNum - 1][columnNum - 1]
					&& gameField[rowNum][columnNum] == gameField[rowNum + 1][columnNum + 1])
				return true;
		} catch (IndexOutOfBoundsException e) {
		}
		// rDiagonal
		try {
			if (gameField[rowNum][columnNum] == gameField[rowNum + 1][columnNum - 1]
					&& gameField[rowNum][columnNum] == gameField[rowNum - 1][columnNum + 1])
				return true;
		} catch (IndexOutOfBoundsException e) {
		}

		return false;

	}

	public Player getCurrentPlayer() {
		return this.players[currentPlayerInd];
	}

	public Optional<Player> getWinner() {
		Optional<Player> response = Optional.empty();
		for (int i = history.size() - 1; 0 <= i; i--) {
			Position p = history.get(i);
			boolean isWinner = checkEqualsAroundPosition(p.getRow(), p.getColumn());
			if (isWinner) {
				return Optional.of(getPlayerByChar(this.gameField[p.getRow()][p.getColumn()]));
			}
		}
		return response;
	}

	private Player getPlayerByChar(final char c) {
		for (int i = 0; i < players.length; i++) {
			if (c == players[i].getPlayerCharacter()) {
				return players[i];
			}
		}
		return null;
	}

	private void incrementPlayerIndex() {
		if (currentPlayerInd >= players.length - 1) {
			currentPlayerInd = 0;
		} else {
			currentPlayerInd++;
		}
	}

	private void validatePlayedPosition(Position position) throws IllegalArgumentException {
		if (!isPosotionInScope(position))
			throw new IllegalArgumentException(
					"Position " + new Position(position.getRow() + 1, position.getColumn()) + " exceeds board size");
		if (DEFAULT_VALUE != this.gameField[position.getRow()][position.getColumn()])
			throw new IllegalArgumentException(
					"Position " + new Position(position.getRow() + 1, position.getColumn() + 1) + " is already used");
	}

	private boolean isPosotionInScope(Position position) {
		if (position.getRow() < 0 || position.getRow() > gameField.length - 1 || position.getColumn() < 0
				|| position.getColumn() > gameField[0].length - 1) {
			return false;
		}
		return true;
	}

	private int getRandomInitialPlayerIndex() {
		Random rand = new Random();
		currentPlayerInd = rand.nextInt(players.length);
		return currentPlayerInd;
	}

	private boolean validateBoardDimensions(int rows, int columns) {
		if (rows <= 2 || rows > 10 || columns <= 2 || columns > 10)
			return false;
		return true;
	}

	private boolean validateNumberOfPlayers(Player[] p) {
		if (p.length < 2)
			return false;
		return true;
	}

	private void initializeGameField(int numberOfRows, int numberOfColumns) {
		this.gameField = new char[numberOfRows][numberOfColumns];

		for (int i = 0; i < this.gameField.length; i++) {
			for (int j = 0; j < this.gameField[i].length; j++) {
				this.gameField[i][j] = DEFAULT_VALUE;
			}
		}

	}

	public char[][] getGameField() {
		return gameField;
	}

	public int getPlayedPositionCounter() {
		return this.history.size();
	}
}
