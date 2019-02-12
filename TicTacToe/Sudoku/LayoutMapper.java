import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class LayoutMapper{
	public static void main(String... agr){
		Scanner sc = new Scanner(System.in);
		int row = sc.nextInt();
		int col = sc.nextInt();
		System.out.println(new Index(row,col,9));
	}

}
class Index{
	private static final int FIRST_BLOCK = 0;
	private static final int SECOND_BLOCK = 3;
	private static final int THIRD_BLOCK = 6;
	int row;
	int column;
	int no;
	int blockNo;
	Index(int row,int column,int no){
		this.row = row;
		this.column = column;
		this.no = no;
		setBlockNo();
	}
	private void setBlockNo(){
		if(row>=SECOND_BLOCK && row<THIRD_BLOCK)
			blockNo+=3;
		else if(row>=THIRD_BLOCK)
			blockNo+=6;
		if(column>=SECOND_BLOCK && column<THIRD_BLOCK)
			blockNo+=1;
		else if(column>=THIRD_BLOCK)
			blockNo+=2;
	}
	public String toString(){
		return String.valueOf(blockNo);
	}
}