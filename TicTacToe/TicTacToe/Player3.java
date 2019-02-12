import java.util.Arrays;
public class Player3{
	static int[] total = {12,3,9,15,21}; 
	private static int[][] board = new int[3][3];
	private static final int SECOND_ROW = 3;
	private static final int THIRD_ROW = 6;
	final int playerNo;
	final char playerSign;
	int[] movesMade = new int[5];
	int moveCounter=0;
	Player3(int playerNo){
		this.playerNo = playerNo;
		if(playerNo == 1)
			playerSign = 'X';
		else
			playerSign = 'O';
	}
	public boolean doesPlayerWin(){
		return (checkHorizontally() || checkVertically() || checkDiagonally());
	}
	private void addToBoard(int buttonNo){
		if(buttonNo<SECOND_ROW)
			board[0][buttonNo] = this.playerNo;
		else if(buttonNo>=SECOND_ROW && buttonNo<THIRD_ROW)
			//eg buttonNO = 3 -> 3-SECOND_ROW->3-3->0
			board[1][buttonNo-SECOND_ROW] = this.playerNo;
		else
			board[2][buttonNo-THIRD_ROW] = this.playerNo;
	}
	public void plays(int buttonNo){
		movesMade[moveCounter++]=buttonNo;
		this.addToBoard(buttonNo);
		printBoard();

	}
	public int nextMove(){
		int possibleMove =-1;
		if(moveCounter == 0)
			possibleMove = firstMove();
		else{
			possibleMove = nextPossibleMove();
		}
		return possibleMove;
	}
	public int firstMove(){
		if(board[1][1] == 0)
			return indexConverter(1,1);		
return indexConverter(2,0);
	}

	public int nextPossibleMove(){
		Coordinate position = indexConverter(movesMade[moveCounter-1]);
		System.out.println("whereCanIWin : "+whereCanIWin());
		System.out.println("whereCanHeWin : "+whereCanHeWin());
		System.out.println("nextEmptyBlock : "+nextEmptyBlock());

		int possibleMove = whereCanIWin();
		if(possibleMove == -1)
		 	possibleMove = whereCanHeWin();
		if(possibleMove == -1)
		 	possibleMove = nextEmptyBlock();
		if(possibleMove == -1)
			possibleMove = anyEmptyBlock();
		return possibleMove;
	}
	public int whereCanHeWin(){
		int[] moves=findHisMove();
		int possibleMove = moveFinder(moves);
		return possibleMove;
	}
	public int whereCanIWin(){
		int[] moves = returnCroppedArray(movesMade,moveCounter);
		int possibleMove = moveFinder(moves);
		return possibleMove;
	}
	public static int moveFinder(int[] moves){
		int possibleMove;
		for(int i = 0;i<moves.length-1;i++){
			for(int j=i+1;j<moves.length;j++){
				if(i == j)
					continue;
				possibleMove = getNextMove(moves[i],moves[j]);
				if(possibleMove != -1 && isEmpty(possibleMove))
					return possibleMove;
			}
		}
		return -1;
	}
	public int anyEmptyBlock(){
		int possibleMove = -1;
		for(int i=0;i<9;i++){
			if(isEmpty(i))
				return i;
		}
		return -1;
	}
	public int nextEmptyBlock(){
		int possibleMove =-1;
		for(int i=moveCounter-1;i>=0;i--){
			possibleMove = nextEmptyBlock(movesMade[i]);
			if(possibleMove!= -1)
				return possibleMove;
		}
		return -1;
	}
	public static int nextEmptyBlock(int buttonNo){
		Coordinate position = indexConverter(buttonNo);
		int possibleMove = -1;
		if(position.row + position.column == 2){
			for(int i=0;i<3;i++){
				if(board[i][2-i] == 0)
					possibleMove = indexConverter(i,2-i);
				else if(board[i][2-i] == 1){
					possibleMove =-1;
					break;
				}
			}
		}
		if(possibleMove != -1)
			return possibleMove;
		if(position.row == position.column){
			for(int i=0;i<3;i++){
				if(board[i][i] == 0)
					possibleMove = indexConverter(i,i);
				else if(board[i][i] == 1){
					possibleMove =-1;
					break;
				}
			}
		}
		if(possibleMove != -1)
			return possibleMove;

		for(int i=0;i<3;i++){
			if(board[i][position.column] == 0)
				possibleMove = (indexConverter(i,position.column));
			else if(board[i][position.column] == 1){
				possibleMove = -1;
				break;
			}
		}
		
		if(possibleMove != -1)
			return possibleMove;
		for(int i=0;i<3;i++){
			if(board[position.row][i] == 0)
				possibleMove = (indexConverter(position.row,i));
			else if(board[position.row][i] == 1){
				possibleMove = -1;
				break;
			}
		}
		return possibleMove;
	}
	public static int[] returnCroppedArray(int[] ar,int length){
		int[] a = new int[length];
		for(int i=0; i<length; i++){
			a[i] = ar[i];
 		}
 		return a;
	}
	public static int getNextMove(int move1,int move2){
		int sum = move1+move2;
		for(int i : total){
			int ans = i-sum;
			if(ans >= 0 && ans < 9){
				if(ans != move1 && ans != move2){

					int[] ar = {move1,move2,ans};
					Arrays.sort(ar);
					if(ar[1]-ar[0] == ar[2]-ar[1] && isPossibleCombination(ar)){
						return ans;	
					}
				}
			}
		}
		return -1;
	}
	public static boolean isEmpty(int buttonNo){
		Coordinate pos = indexConverter(buttonNo);
		if(board[pos.row][pos.column] == 0)
			return true;
		return false;

	}
	
	public static boolean isPossibleCombination(int[] ar){
		int countOdd = 0;
		for(int i : ar){
			if(i % 2 != 0)
				countOdd++;
		}
		if(countOdd >= 2 && ar[1] != 4)
			return false;
		return true;
	}	
	
	public  int[] findHisMove(){
		int[] player1Moves = new int[moveCounter+1];
		int count=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(board[i][j] == 1){
					player1Moves[count++] = indexConverter(i,j);
				}
			}
		}
		return player1Moves;
	}
	public boolean checkHorizontally(){
		for(int i=0;i<3;i++){
			int count=0;
			for(int j=0;j<3;j++){
				if(board[i][j] == this.playerNo)
					count++;
				else
					continue;
			}
			if(count==3){
				TicTacToe3.colorStreak(indexConverter(i,0),1);
				return true;
			}
		}
		return false;
	}

	public boolean checkVertically(){
		for(int i=0;i<3;i++){
			int count=0;
			for(int j=0;j<3;j++){
				if(board[j][i] == this.playerNo)
					count++;
				else
					continue;
			}
			if(count==3){
				TicTacToe3.colorStreak(indexConverter(0,i),3);
				return true;
			}
		}
		return false;
	}

	public boolean checkDiagonally(){
		int count=0;
		for(int i=0;i<3;i++){
			if(board[i][i] == this.playerNo)
				count++;
			else
				break;			
		}
		if(count==3){
			TicTacToe3.colorStreak(indexConverter(0,0),4);
			return true;
			}
		count=0;
		for(int j=0; j<3; j++){
			//In second diagonal the second index is length of array -1 -first index -> 3-1-firstIndex -> 2- first index
			if(board[j][2-j] == this.playerNo)
				count++;
			else
				break;
		}
		if(count==3){
			TicTacToe3.colorStreak(indexConverter(0,2),2);
			return true;
		}
		else
			return false;
	}

	public static void printBoard(){
		for(int i=0;i<3;i++){
			for(int j=0; j<3;j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	public static void clearBoard(){
		for(int i =0; i<3;i++){
			for(int j=0;j<3;j++){
				board[i][j] = 0;
			}
		}
	}
	public static Coordinate indexConverter(int buttonPosition){
		Coordinate coordinates = null;
		if(buttonPosition<SECOND_ROW)
				coordinates = new Coordinate(0,buttonPosition);	
		else if(buttonPosition>=SECOND_ROW && buttonPosition<THIRD_ROW)
		//eg buttonPosition = 3 -> 3-SECOND_ROW->3-3->0
			coordinates = new Coordinate(1,buttonPosition-SECOND_ROW);
		else if(buttonPosition>=THIRD_ROW && buttonPosition<=THIRD_ROW+2)
			coordinates = new Coordinate(2,buttonPosition-THIRD_ROW);	
		return coordinates;
	}
	public static int indexConverter(int row,int column){
		return row*3+column;
	}
	public static int indexConverter(Coordinate c){
		return c.row*3+c.column;
	}
}	

class Coordinate{
	int row;
	int column;
	Coordinate(int row, int column){
		this.row = row;
		this.column = column;
	} 	
}
/*
for(int i=0;i<3;i++){
				if(board[0][i] == this.playerNo)
					count++;
				else if(board[0][i] == 0){
					possibleMove = indexConverter(0,i);
					count++;
				}
				else
					break;
			}
			if(count == 3 && possibleMove != -1)
				return possibleMove;
			
			count = 0;
			possibleMove = -1;			
			for(int i=0;i<3;i++){
				if(board[i][0] == this.playerNo)
					count++;
				else if(board[i][0] == 0){
					possibleMove = indexConverter(i,0);
					count++;
				}
				else
					break;
			}
			if(count == 3 && possibleMove != -1)
				return possibleMove;
			count = 0;
			possibleMove = -1;

			for(int i=0;i<3;i++){
				if(board[i][i] == this.playerNo)
					count++;
				else if(board[i][i] == 0){
					possibleMove = indexConverter(i,i);
					count++;
				}
				else
					break;
			}
			if(count == 3 && possibleMove != -1)
				return possibleMove;
		}
		*/
/*	
	public int checkForMoveHorizontally(int row,int playerSign){
		int possibleMove = -1;
		int count =0;
		for(int i=0;i<3;i++){
			if(board[row][i] == playerSign){
				count++;
			}
			else if(board[row][i] == 0){
				possibleMove = indexConverter(row,i);
			}
		}
		if(count == 2 && possibleMove != -1)
			return possibleMove;
		return -1;
	}
	public int checkForMoveVerticaly(int column,int playerSign){
		int possibleMove = -1;
		int count =0;
		for(int i=0;i<3;i++){
			if(board[i][column] == playerSign){
				count++;
			}
			else if(board[i][column] == 0){
				possibleMove = indexConverter(i,column);
			}
		}
		if(count == 2 && possibleMove != -1)
			return possibleMove;
		return -1;
	}
	public int checkForMoveDiagonally(int row,int column,int playerSign){
		int possibleMove =-1;
		int count = 0;
		if(row == column){
			for(int i=0;i<3;i++){
				if(board[i][i] == playerSign){
				count++;
			}
			else if(board[i][i] == 0){
				possibleMove = indexConverter(i,i);
				}		
			}
			if(count == 2 && possibleMove != -1)
			return possibleMove;
		}
		count =0;
		possibleMove =-1;
		if(row+column == 2){
			for(int i=0;i<3;i++){
				if(board[i][2-i] == playerSign){
				count++;
			}
			else if(board[i][2-i] == 0){
				possibleMove = indexConverter(i,2-i);
				}		
			}
			if(count == 2 && possibleMove != -1)
			return possibleMove;
		}
		return -1;
	}

	public int nextEmptyBlock(){
		int possibleMove = -1;
		int count =0;
		for(int j=moveCounter-1;j>=0;j--){
			Coordinate current = indexConverter(movesMade[j]);
			if(current.row == current.column){
				for(int i=0;i<3;i++){
					if(board[i][i] == 1){
					count++;
				}
				else if(board[i][i] == 0){
					possibleMove = indexConverter(i,i);
					count++;
					}		
				else
					possibleMove = -1;
				}
				if(count == 2 && possibleMove != -1)
				return possibleMove;
			}
			if(current.row+current.column == 2){
				for(int i=0;i<3;i++){
					if(board[i][2-i] == 2){
					count++;
				}
				else if(board[i][2-i] == 0){
					possibleMove = indexConverter(i,2-i);
					count++;
					}		
				else
					possibleMove = -1;
				}
				if(count == 2 && possibleMove != -1)
				return possibleMove;
			}
			count=0;
			possibleMove = -1;
			for(int i=0;i<3;i++){
				if(board[current.row][i] == 2){
					count++;
				}
				else if(board[current.row][i] == 0){
					possibleMove = indexConverter(current.row,i);
					count++;
				}
				else
					possibleMove=-1;
			}
			if(count == 2 && possibleMove != -1)
				return possibleMove;	
			count = 0;
			possibleMove = -1;
			for(int i=0;i<3;i++){
				if(board[i][current.column] == 2){
					count++;
				}
				else if(board[i][current.column] == 0){
					possibleMove = indexConverter(i,current.column);
					count++;
				}
				else
					possibleMove=-1;
			}
			if(count == 2 && possibleMove != -1)
				return possibleMove;
		}
		return possibleMove;
	}
}*/