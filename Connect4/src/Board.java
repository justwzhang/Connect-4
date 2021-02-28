//Justin Zhang 112615200

public class Board {
	private String[][] board;
	private String currentPlayerColor;
	public Board() {		
	}
	public Board(String[][] newBoard) {
		this.board = newBoard;
		this.currentPlayerColor = "Y";
	}
	public void printBoard() {//prints current board with pieces in play
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println();
		}
	}
	public String[][] getBoard(){
		return this.board;
	}
	public String getCurrentPlayerColor() {
		return this.currentPlayerColor;
	}
	public void setCurrentPlayerColor(String color) {
		this.currentPlayerColor = color;
	}
	public void placeColorChoice(int columnChoice){
		int[] indexes = indexOfChoice(columnChoice);
		if(indexes[0] != 1000) {
			board[indexes[0]][indexes[1]] = this.currentPlayerColor;
		}else {
			System.out.println("The column selected is full please try again");//detects a full column choice
			if(this.currentPlayerColor == "R") {
				this.currentPlayerColor = "Y";
			}else {
				this.currentPlayerColor = "R";
			}
		}
	}
	public int[] indexOfChoice(int columnChoice) {
		int newColumnChoice = columnChoice * 2 + 1;
		int[] indexes = new int[2];
		for(int i=0; i<board.length; i++) {
			if((i == 0) && (this.board[0][newColumnChoice] == "R") || (this.board[0][newColumnChoice] == "Y")) { //detects if the column is full
				indexes[0] = 1000;
				return indexes;
			}
			if((this.board[i][newColumnChoice] == "R") || (this.board[i][newColumnChoice] == "Y")) {//detects if there is a color at the lowest level
				indexes[0]=i - 1;
				indexes[1]=newColumnChoice;
				break;
			}
			if(i == this.board.length-1 && (this.board[i][newColumnChoice] != "R" && this.board[i][newColumnChoice] != "Y")) {//detects if there is no color in a column
				indexes[0]=board.length - 1;
				indexes[1]=newColumnChoice;
				break;
			}
		}		
		return indexes;
	}
	//everything after this is to check for win conditions
	public void printArray(String[][] arr) {
		for(int i=0; i< arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	public void print1DArray(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.println(arr[i] + " ");
		}
	}
	public String[][] removeBars() {
		String[][] removedBars = new String[6][7];
		String[] tempRow = new String[7];
		int count=0;
		for(int i=0; i<this.board.length; i++) {
			for(int j=0; j<this.board[i].length; j++) {
				if(this.board[i][j] != "|") {
					tempRow[count] = this.board[i][j];
					count++;
					
				}
			}
			for(int k=0; k<removedBars[i].length; k++) {
				removedBars[i][k] = tempRow[k];
			}
			count = 0;
		}
		return removedBars;
	}
	public boolean checkWinRed() {
		String[][] removedBars = removeBars();
		if(checkVertical(removedBars, "R") == true || checkHorizontal(removedBars, "R") == true || checkRightDiagonal(removedBars, "R") == true || checkLeftDiagonal(removedBars, "R") == true) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkWinYellow() {
		String[][] removedBars = removeBars();
		if((checkVertical(removedBars, "Y") == true )||( checkHorizontal(removedBars, "Y") == true )||( checkRightDiagonal(removedBars, "Y") == true )||( checkLeftDiagonal(removedBars, "Y") == true)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkDraw() {
		String [][] removedBars = removeBars();
		int count = 0;
		for(int i=0; i<removedBars.length; i++) {
			for(int j=0; j<removedBars[i].length; j++) {
				if(removedBars[i][j] == "R" || removedBars[i][j] == "Y") {
					count++;
				}
			}
		}
		if(count == 42) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkVertical(String[][] removedBars, String color) {
		boolean test = false;
		int counter = 0;
		for(int i=0; i<removedBars[0].length; i++) {
			for(int j=0; j<removedBars.length; j++) {
				if(removedBars[j][i] == color) {
					counter++;
				}
				if(counter == 4) {
					test = true;
					break;
				}
				if(removedBars[j][i] != color) {
					counter = 0;
				}				
			}
			
			counter = 0;
		}
		return test;
	}
	public boolean checkHorizontal(String[][] removedBars, String color) {
		boolean test = false;
		int counter = 0;
		for(int i=0; i<removedBars.length; i++) {
			for(int j=0; j<removedBars[i].length; j++) {
				if(removedBars[i][j] == color) {
					counter++;
				}
				if(counter == 4) {
					test = true;
					break;
				}
				if(removedBars[i][j] != color) {
					counter = 0;
				}				
			}
			
			counter = 0;
		}
		return test;
	}
	public boolean checkRightDiagonal(String[][] removedBars, String color) {//places the right diagonal into an array and checks for win condition
		boolean test = false;
		int counter = 0;
		String[] diagonal = new String[6];		
		for(int i=0; i<removedBars.length; i++) {
			for(int j=0; j<removedBars[i].length; j++) {
				int tempI = i;
				int tempJ = j;
				clearDiagonalArray(diagonal);
				while((tempJ >= 0 && tempI >= 0) && (tempJ<removedBars[0].length&&tempI<removedBars.length)) {
					diagonal[counter] = removedBars[tempI][tempJ];
					counter++;
					tempI++;
					tempJ++;
				}
				if(checkDiagonalArray(diagonal, color) == true) {
					return true;
				}
				counter = 0;				
			}
		}
		return test;
	}
	public boolean checkLeftDiagonal(String[][] removedBars, String color) {//places the left diagonal into an array and checks for win condition
		boolean test = false;
		int counter = 0;
		String[] diagonal = new String[6];		
		for(int i=0; i<removedBars.length; i++) {
			for(int j=0; j<removedBars[i].length; j++) {
				int tempI = i;
				int tempJ = j;
				clearDiagonalArray(diagonal);
				while((tempJ >= 0 && tempI >= 0) && (tempJ<removedBars[0].length&&tempI<removedBars.length)) {
					diagonal[counter] = removedBars[tempI][tempJ];
					counter++;
					tempI++;
					tempJ--;
				}
				if(checkDiagonalArray(diagonal, color) == true) {
					return true;
				}
				counter = 0;				
			}
		}
		return test;
	}
	public void clearDiagonalArray(String[] diagonal) {
		for(int i=0; i<diagonal.length; i++) {
			diagonal[i] = " ";
		}
	}
	public boolean checkDiagonalArray(String[] diagonal, String color) {//checks for win condition in the diagonal
		boolean test = false;
		int counter = 0;
		for(int i=0; i<diagonal.length; i++) {
			if(diagonal[i] == color) {
				counter++;
			}
			if(counter == 4) {
				test = true;
				break;
			}
			if(diagonal[i] != color) {
				counter = 0;
			}
			
		}
		return test;
	}
}
