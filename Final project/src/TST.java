import java.util.Scanner;

public class TST {
	
	public class Node{
		
		char data;
		Info info;
		
		Node left; 
		Node mid; 
		Node right; 
		
		Node(char Data, Info INFO, Node LEFT, Node MID, Node RIGHT){
			data = Data;
			info = INFO;
			
			left = LEFT;
			mid = MID;
			right = RIGHT;
		}
	}
	
	Node head;

	
	TST () {
		head = null;
		}
	
	void insert(){}
}
