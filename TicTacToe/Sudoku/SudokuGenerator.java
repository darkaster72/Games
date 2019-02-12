public class SudokuGenerator{
	int[][] board = new int[9][9];
	public SudokuGenerator(){

	}
	public static void main(String... args){
		new SudokuGenerator();
	}
	public static boolean checkIfValid(Index i){
		return checkRow(i)|| checkColumn(i) || checkInBlock(i); 
	}
	public static boolean checkRow(Index index){
		for(int i= 0;i<9;i++){
			if(board[index.row][i] == index.no)
				return false;
		}
		return true;
	}
	public static boolean checkColumn(Index index){
		for(int i= 0;i<9;i++){
			if(board[i][index.column] == index.no)
				return false;
		}
		return true;
	}
	public static boolean checkInBlock(Index index){
		for()
	}
}
