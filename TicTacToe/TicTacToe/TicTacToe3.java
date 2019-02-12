import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class TicTacToe3 implements ActionListener{
	static TicTacToe3 currentGame;
	JDialog dialogBox;
	JFrame frame ;
	JButton[] buttons;
	boolean player1Turn;
	Player3 player1;
	Player3 player2;
	Player3 currentPlayer;
	Font font;
	int totalMoves = 0;
	final int playerMode ;
	TicTacToe3(int playerMode){
		this.playerMode = playerMode;
		player1=new Player3(1);
		player2=new Player3(2);
		player1Turn=true;
		frame = new JFrame(((playerMode == 1)?"Single":"Double")+" Player Mode");
		buttons = new JButton[9];
		Player3.clearBoard();
		initializeButtons();
		setFrameProperties();
		frame.setVisible(true);
	}
	public void initializeButtons(){
		for(int i=0; i<9; i++){
			buttons[i] = new JButton();
			buttons[i].setSize(100,100);
			buttons[i].setActionCommand(String.valueOf(i));
			buttons[i].addActionListener(this);
			frame.add(buttons[i]);
		}
	}
	public void setFrameProperties(){
		frame.setLayout(new GridLayout(3,3));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setLocationRelativeTo(null);
	}
	public void actionPerformed(ActionEvent e){
		totalMoves++;
		int buttonNo = Integer.parseInt(e.getActionCommand());
		currentPlayer = (player1Turn)?player1:player2;
		currentPlayer.plays(buttonNo);
		displayMove(buttonNo);

		System.out.println("move : "+totalMoves);
		if(currentPlayer.moveCounter >= 3){
			if(currentPlayer.doesPlayerWin()){
				System.out.println("Player"+currentPlayer.playerNo+" Wins !!!");
				generateDialogBox("Player"+currentPlayer.playerNo+" Wins !!!");
			}
		}
		if(totalMoves == 9){
			System.out.println("Tie");
				generateDialogBox("We Have a tie");
		}
		((JButton)e.getSource()).removeActionListener(this);
		player1Turn = !player1Turn;
		if(playerMode == 1){
			playerMoves(player2.nextMove());
			player1Turn = true;
		}
	}
	public void playerMoves(int buttonNo){
		currentPlayer = (player1Turn)?player1:player2;
		if(totalMoves >= 9){
			System.out.println("Tie");
				generateDialogBox("We Have a tie");
				return;
		}
		if(buttonNo != -1){
    		currentPlayer.plays(buttonNo);
			buttons[buttonNo].removeActionListener(this);
			displayMove(buttonNo);	
		}
		
		System.out.println("Player"+currentPlayer.playerNo+" plays : "+buttonNo);
		totalMoves++;
		System.out.println("move : "+totalMoves);
		if(currentPlayer.moveCounter >= 3){
			if(currentPlayer.doesPlayerWin()){
				System.out.println("Player"+currentPlayer.playerNo+" Wins !!!");
				if(playerMode == 1 && currentPlayer.playerNo == 2){
					generateDialogBox("Computer Wins !!!");
				}
				else
					generateDialogBox("Player"+currentPlayer.playerNo+" Wins !!!");
			}
		}
			
	}
	public void displayMove(int buttonNo){
		buttons[buttonNo].setText(String.valueOf(currentPlayer.playerSign));
		buttons[buttonNo].setFont(new Font("SansSerif",Font.BOLD,32));
	}
	public static void main(String... args){
		startGame();
	}
	public static void startGame(){
		JFrame gameMenuFrame = new JFrame("Tic Tac Toe");
		JButton singlePlayerButton = new JButton("Single player");
		JButton doublePlayerButton = new JButton("Double Player");
		doublePlayerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentGame = new TicTacToe3(2);
				gameMenuFrame.setVisible(false);
			}
		});
		singlePlayerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentGame = new TicTacToe3(1);
				gameMenuFrame.setVisible(false);
			}
		});
		gameMenuFrame.add(singlePlayerButton);
		gameMenuFrame.add(doublePlayerButton);
		gameMenuFrame.setLayout(new FlowLayout());
		gameMenuFrame.setLocationRelativeTo(null);
		gameMenuFrame.pack();
		gameMenuFrame.setVisible(true);
	}
	public void generateDialogBox(String message){
		dialogBox = new JDialog(frame,message,Dialog.ModalityType.DOCUMENT_MODAL);
		JButton playAgain = new JButton("Play Again");
		JButton exit = new JButton("Exit");
		playAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentGame = new TicTacToe3(currentGame.playerMode);
				System.out.println("Game start");
				frame.setVisible(false);
				dialogBox.setVisible(false);
			}
		});

		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		dialogBox.setLayout(new FlowLayout());
		dialogBox.addWindowListener(new WindowAdapter(){
			public void windowClosing(){
				System.exit(0);
			}
		});
		dialogBox.add(playAgain);
		dialogBox.add(exit);
		dialogBox.pack();
		dialogBox.setLocationRelativeTo(null);
		dialogBox.setVisible(true);
	}
	public static void colorStreak(int start,int difference){
		for(int i=start; i<start+difference*3; i+=difference){
			currentGame.buttons[i].setBackground(Color.GREEN);
		}
	}
}