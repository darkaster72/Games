import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SudokuLayout{
	JPanel[] panels;
	JPanel mainBoardPanel;
	JPanel buttonStrip;
	JFrame frame;
	static Font font = new Font("Helvetica",Font.BOLD,40);
	static JButton currentSelected;

	SudokuLayout(){
		createLayout();
	}
	public static void main(String ... args){
		new SudokuLayout();
	}
	public void createLayout(){
		frame = new JFrame("Sudoku");
		panels = new JPanel[9];	
		mainBoardPanel = new JPanel();
		mainBoardPanel.setLayout(new GridLayout(3,3,2,2));
		for(int i=0;i<9;i++){
			mainBoardPanel.add(initializePanel(i));
		}
		frame.add(mainBoardPanel);
		frame.add(initializeButtonStrip(),BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private JPanel initializePanel(int index){
		for(int i=0;i<9;i++){
			panels[index] = new JPanel();
			panels[index].add(initializeGrid(i));
		}
		panels[index].setBackground(Color.black);
		return panels[index];
	}
	private JPanel initializeGrid(int index){
		JPanel currentPanel = new JPanel();
		currentPanel.setLayout(new GridLayout(3,3,1,1));
		for(int i=0;i<9;i++){
			currentPanel.add(new MyButton(String.valueOf(i)));			
		}
		currentPanel.setBackground(Color.black);
		return currentPanel;
	}
	private JPanel initializeButtonStrip(){
		buttonStrip = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for(int i=0;i<9;i++){
			buttonStrip.add(new MyButton(i+1));
		}
		buttonStrip.setBackground(Color.black);
		return buttonStrip;
	}
}
class MyButton extends JButton{
	static ActionOnBoardClick actionOnBoard= new ActionOnBoardClick();
	static ActionOnButtonStripClick actionOnButtonStrip= new ActionOnButtonStripClick();
	{
		setFont(SudokuLayout.font);
	}
	public MyButton(String s){
		setActionCommand(s);
		addActionListener(actionOnBoard);
		setPreferredSize(new Dimension(70,70));
		setBackground(Color.white);
		setForeground(Color.black);
	}
	public MyButton(int i){
		super(String.valueOf(i));
		addActionListener(actionOnButtonStrip);
		setPreferredSize(new Dimension(70,70));
		setBackground(Color.gray);
		setForeground(Color.white);
	}
}
class ActionOnBoardClick implements ActionListener{
	public void actionPerformed(ActionEvent e){
		SudokuLayout.currentSelected = (JButton)e.getSource();
		SudokuLayout.currentSelected.setBackground(Color.green);
	}
}
class ActionOnButtonStripClick implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(SudokuLayout.currentSelected != null){
			String s = ((JButton)e.getSource()).getText();
			SudokuLayout.currentSelected.setText(s);
			// System.out.println(Sudoku)
			SudokuLayout.currentSelected.setBackground(Color.white);
			SudokuLayout.currentSelected = null;
		}
	}
}
// class MyPanel extends JPanel{
// 	public MyPanel(){
// 		super();
// 	}
// 	protected void paintComponent(Graphics g)
//     {
//         super.paintComponent(g);
//         setBackground(Color.RED);
//     }
// }