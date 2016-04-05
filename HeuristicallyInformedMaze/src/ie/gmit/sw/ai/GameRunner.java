package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 50;
	private Node[][] model;
	private Node goal;
	private GameView view;
	private int currentRow;
	private int currentCol;
	private static int player_goal=0;
	private static int bombNumber =0;
	private static int swords = 0;
	
	
	public GameRunner() throws Exception{
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
		goal = m.getGoalNode();
    	view = new GameView(model, goal);
    	
    	
    	placePlayer();
    	
    	Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	view.setPreferredSize(d);
    	view.setMinimumSize(d);
    	view.setMaximumSize(d);
    	
    	JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addKeyListener(this);
        f.getContentPane().setLayout(new FlowLayout());
        f.add(view);
        f.setSize(1000,1000);
        f.setLocation(100,100);
        f.pack();
        f.setVisible(true);
        goal.setGoalNode(true);
        
        System.out.println("Goal Node Position: " + "Row: "+ goal.getRow() + " " + "Col: " + goal.getCol());
        
        Traversator t = new BestFirstTraversator(goal);    
        t.traverse(model, model[currentRow][currentCol], view);
	}
	
	private void placePlayer(){   	// player - start node
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol].setData('P');
    	updateView(); 		
	}
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
	}
	
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }else if (e.getKeyCode() == KeyEvent.VK_Z){
        	view.toggleZoom();
        }else{
        	return;
        }
        
        updateView();       
    }
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

    
	private boolean isValidMove(int r, int c){
		
		if(model[r][c].getData()==' '){
			return roadcheck(r,c);
		}else if(model[r][c].getData()=='B'){
			return bombcheck(r, c);
		}else if(model[r][c].getData()=='W'){
			return sword_weaponcheck(r, c);
		}else if(model[r][c].getData()=='G'){
			return checkGoalNode(r, c);
		}else if(model[r][c].getData()=='Q'){
			return roadcheck(r, c);
		}
		else 
			{return enemycheck(r,c);
		}
		
		
	}
	// check if goal node reached
	public boolean checkGoalNode(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getData() == 'G'){
			model[currentRow][currentCol].setData(' '); // clear
			model[r][c].setData('X'); // set player and he can move
			displayGUI();
			System.out.println("Congratulations...Reached Goal Node !!!");
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	public boolean roadcheck(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getData() == ' '
				||model[r][c].getData() == 'Q' ){
			model[currentRow][currentCol].setData(' '); // clear
			model[r][c].setData('P'); // set player and he can move
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	public boolean bombcheck(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getData() == 'B'){
		//	check the node --- changing bomb B to wall X
			model[r][c].setData('X'); // set wall
			bombNumber++;
			System.out.println("Number of Bombs: " + bombNumber );
			return false;
		}else{
			return false; //Can't move
		}
	}
	public boolean sword_weaponcheck(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getData() == 'W'){
		//	check the node --- changing weapon to wall X
			model[r][c].setData('X');
			swords++;
			System.out.println("Number of Swords: " + swords );
			return false;
		}else{
			return false; //Can't move
		}
	}
	
	public boolean enemycheck(int r,int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c].getData() == 'E'){
			model[currentRow][currentCol].setData(' ');
			model[r][c].setData('P');
			player_goal++;
			System.out.println("Number of battles won: " + player_goal);
			return true;
		}else{
			return false; //Can't move
		}
	}
	public static int getPlayer_goal() {
		return player_goal;
	}

	public static void setPlayer_goal(int player_goal) {
		GameRunner.player_goal = player_goal;
	}

	public static int getBombNumber() {
		return bombNumber;
	}

	public void setBombNumber(int bombNumber) {
		this.bombNumber = bombNumber;
	}

	public static int getSwords() {
		return swords;
	}

	public void setSwords(int swords) {
		this.swords = swords;
	}
	 public void displayGUI()
	 {
	    JOptionPane.showMessageDialog(null, getPanel(), "END : ", JOptionPane.INFORMATION_MESSAGE);
	 }
	 public JPanel getPanel()
	 {
	    JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
	    JLabel goal = getLabel("Congratulations, Reached Goal Node");
	    JLabel battles = getLabel("Battles Won: " + getPlayer_goal());
	    JLabel bombs = getLabel("Bombs Acquired : " + getBombNumber());
	    JLabel swords = getLabel("Swords Acquired: " + getSwords());
	    JLabel end = getLabel("Game Finished !!!");
	    panel.add(goal);
	    panel.add(battles);
	    panel.add(bombs);
	    panel.add(swords);
	    panel.add(end);

	    return panel;
	 }

	 public JLabel getLabel(String title) {
	   return new JLabel(title);
	 }
	
	public static void main(String[] args) throws Exception{
    	   new GameRunner();
		     
		
	}
}