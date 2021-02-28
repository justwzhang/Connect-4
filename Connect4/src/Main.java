

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[][] newBoard = new String[6][15];
		for(int i=0; i<newBoard.length; i++) {
			for(int j=0; j<newBoard[i].length; j++) {
				if(j%2 == 0) {
					newBoard[i][j] = "|";
				}else {
					newBoard[i][j] = " ";
				}
			}
		}
		Board game = new Board(newBoard);
		System.out.println("Enter a column from 0-6");
		System.out.println("Yellow Player Starts");
		System.out.println("  0   1   2   3   4   5   6");
		game.printBoard();
		boolean win = false;
		int column;
		while(win == false) {
			if(in.hasNextInt() == true) {
				column = in.nextInt();
				if(column <= 6 && column >=0) {	
					game.placeColorChoice(column);
					if(game.getCurrentPlayerColor() == "R") {
						System.out.println("Yellow player's turn");
					}else {
						System.out.println("Red player's turn");
					}						
					System.out.println("  0   1   2   3   4   5   6");
					game.printBoard();
					if(game.getCurrentPlayerColor() == "R") {
						game.setCurrentPlayerColor("Y");
					}else {
						if(game.getCurrentPlayerColor() == "Y") {
							game.setCurrentPlayerColor("R");
						}
					}
				}else {
					System.out.println("Invalid input, please try again");
					continue;
				}
			}else {
				System.out.println("Invalid input, please try again");
				in.next();
			}
			if(game.checkWinRed() == true) {
				win = true;
				System.out.println("Red player has won! GG");
			}
			if(game.checkWinYellow() == true) {
				win = true;
				System.out.println("Yellow player has won! GG");
			}
			if(game.checkDraw() == true) {
				win = true;
				System.out.println("The game is a draw");
			}
		}
		in.close();
	}

}
