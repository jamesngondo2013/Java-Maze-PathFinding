package ie.gmit.sw.ai;

import java.awt.Color;

//James Ngondo

public class Node {
	public char data;
	
	public boolean isGoal=false;
	public boolean visited =  false;
	
	private static final int MAX_EXITS = 4;
	public enum NodeType{Wall, Passage};
	public enum NodePassage{North, South, East, West, None};
	private Node parent;
	private Color color = Color.BLACK;
	private NodeType type = NodeType.Wall;
	private NodePassage passage = NodePassage.None;

	private int row;
	private int col;
	private int distance;
	public Node(){
		
	}
	public Node(int row ,int col){
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	private NodePassage[] paths = null;
	public boolean hasDirection(NodePassage direction){	
		if(paths!=null){
			if(paths.length>0){
				for (int i = 0; i < paths.length; i++) {
					if (paths[i] == direction) return true;
				}
			}
		}
		return false;
	}

	public void addPath(NodePassage direction) {
		int index = 0;
		if (paths == null){
			paths = new NodePassage[index + 1];		
		}else{
			index = paths.length;
			NodePassage[] temp = new NodePassage[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
		}
		
		paths[index] = direction;
	}



public Node[] children(Node[][] maze){
		
	java.util.List<Node> children = new java.util.ArrayList<Node>();
	
	if (row > 0 && maze[row - 1][col].hasDirection(NodePassage.South ) ) children.add(maze[row - 1][col]); //Add North
	if (row < maze.length - 1 && maze[row + 1][col].hasDirection(NodePassage.North)) children.add(maze[row + 1][col]); //Add South
	if (col > 0 && maze[row][col - 1].hasDirection(NodePassage.East)) children.add(maze[row][col - 1]); //Add West
	if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(NodePassage.West)) children.add(maze[row][col + 1]); //Add East*/
	
	return children.toArray(new Node[children.size()]);
		
	}
	
	
	public NodeType getType() {
		return type;
	}
	
	public void setType(NodeType type) {
		this.type = type;
	}

	public NodePassage getPassage() {
		return passage;
	}

	public void setPassage(NodePassage passage) {
		this.passage = passage;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.color = Color.BLUE;
		this.visited = visited;
	}
	
	public boolean isGoalNode() {
		return isGoal;
	}

	public void setGoalNode(boolean goal) {
		this.isGoal = goal;
	}
	
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public String toString() {
		if (passage == NodePassage.North){
			return "N ";
		}else{
			return "W ";
		}
	}
}
