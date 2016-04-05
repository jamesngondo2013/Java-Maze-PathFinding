package ie.gmit.sw.ai;

import java.util.Random;

import ie.gmit.sw.ai.Node.NodePassage;

public class Maze {
	private Node[][] maze;
	private Node goal;
	public Maze(int rows, int cols){
		
		maze = new Node[rows][cols];
		//System.out.println("maze row: "   + "cols: " + cols);
		init();
		buildMaze();
		setGoalNode();
		
		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature('W', 'X', featureNumber);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
		addFeature('E', 'X', featureNumber);
		generatePath();
	}
	
	private void init(){	 // initially set the maze will walls
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row,col); 
				maze[row][col].setData('X');
			}
		}
	}
	private void generatePath() {

		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				if (col < maze[row].length - 1) {
					if (maze[row][col + 1].data == ' ') {
						maze[row][col].addPath(Node.NodePassage.West);
					}
				}

				if (col > 0) {
					if (maze[row][col - 1].data == ' ') {
						maze[row][col].addPath(Node.NodePassage.East);
					}
				}
				if (row < maze.length - 1) {
					if (maze[row + 1][col].data == ' ') {
						maze[row][col].addPath(Node.NodePassage.North);
					}
				}
				if (row > 0) {
					if (maze[row - 1][col].data == ' ') {
						maze[row][col].addPath(Node.NodePassage.South);
					}
				}
			}
		}

	}
	
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getData() == replace){
				maze[row][col].setData(feature);
				counter++;
			}
		}
	}
	
	
	private void buildMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num >= 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setData(' ');
					continue;
				}
				if (row + 1 < maze.length){ //Check south
					maze[row + 1][col].setData(' ');
				}
				//added this
				if (col > 0 && (row == 0 || num >= 5)){
					maze[row][col].setPassage(Node.NodePassage.West);
					maze[row][col].setPassage(Node.NodePassage.East);
				}else{
					maze[row][col].setPassage(Node.NodePassage.North);
					maze[row][col].setPassage(Node.NodePassage.South);
					
				}
			}
		}	
	}
	//Pick a goal node aaded this
	public void setGoalNode(){
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		maze[randRow][randCol].setGoalNode(true);
		goal = maze[randRow][randCol];
		maze[randRow][randCol].setData('G'); //added this
	}
	
	public Node getGoalNode(){
		return goal;
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}