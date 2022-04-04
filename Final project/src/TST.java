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
	
	void insert(char[] word, Node root, int current, Info INFO) {
		if (current == word.length) {System.out.println("\n Done \n"); return;} //all char in word done
		
		Info info;
		if (word.length == current + 1) {
			info = INFO;
		}
		else {
			info = null;
		}
		
		Node node = new Node(word[current], info, null, null, null);
		System.out.println(node.data + " " + current + " " + (node.info != null));
		
		if (this.head == null) {this.head = node; insert(word, this.head, current+1, INFO); System.out.println("New head"); return;} //if there is no TTS then make node the head of TTS
		if (root == null) {System.out.println("root is null"); return;} //error check
		
		if (root.data == node.data) {
			if ((node.info != null)) {root.info = node.info;}
			
			if (root.mid != null) {
				insert(word, root.mid, current+1, INFO); 
				return;
				}
			else {
				//if (current+1 == word.length) {System.out.println("\n blaaa \n"); return;} //all char in word done
				//root.mid = new Node(word[current+1], word.length == current+1, null, null, null);
				//insert(word, root.mid, current+1); 
				if (!(root.info != null)) {
					root.mid = node;
					insert(word, root.mid, current + 1, INFO);
					return;
				}
				else {
					insert(word, root, current+1, INFO);
					return;
				}
				}
		}
		
		if (root.mid == null) {
			root.mid = node;
			insert(word, root.mid, current+1, INFO);
			return;
		}
		
		if (Character.compare(node.data, root.data) > 0) {
			if (root.right == null) {
				root.right = node;
				insert(word, root.right, current+1, INFO);
				return;
			}
			insert(word, root.right, current, INFO);
			return;
		}
		
		if (Character.compare(node.data, root.data) < 0) {
			if (root.left == null) {
				root.left = node;
				insert(word, root.left, current+1, INFO);
				return;
			}
			insert(word, root.left, current, INFO);
			return;
		}
		System.out.println("OHHHHHHHHHH NOOOOOOOOOOOOOOOOO");
	}
	
	
	void put(Info INFO){
		char[] word = INFO.name.toCharArray();
		insert(word, this.head, 0, INFO);
	}
	
	Node print(Node node) {
		String left = "";
		String mid = "";
		String right = "";
		if (node.left == null) {node.left = new Node(' ', null, null, null, null);}
		if (node.right == null) {node.right = new Node(' ', null, null, null, null);}
		if (node.mid == null) {node.mid = new Node(' ', null, null, null, null);}
		
		if (node == this.head) {System.out.println("\n  " + node.data + "   is the head");}
		
		if (node.left.info != null) {left = "1";}
		if (node.mid.info != null) {mid = "1";}
		if (node.right.info != null) {right = "1";}
		
		System.out.println(node.left.data + left + " " + node.mid.data + mid + " " + node.right.data + right);
		return node;
	}
	
	
	
	public static void main(String args[])
    {
		TST tst = new TST();
		
		//System.out.println(Character.compare('e', 'h')); // -3
		
		String[] str = new String[10];
		for (int i = 0 ; i < 10 ; i++) {
			str[i] = null;
		}
		
		Info test = new Info(str);
		test.name = "she";
		tst.put(test);
		test.name = "sells";
		tst.put(test);
		test.name = "sea";
		tst.put(test);
		test.name = "shells";
		tst.put(test);
		test.name = "by";
		tst.put(test);
		test.name = "the";
		tst.put(test);
		test.name = "sea";
		tst.put(test);
		test.name = "shore";
		tst.put(test);
        
        Scanner Scannerinput = new Scanner(System.in);
        String input;
        Node current = tst.head;
        
        for (int i = 0; i < 2; i--) {
        	input = Scannerinput.nextLine();
        	if (input.compareTo("end") == 0) {break;}
        	
        	if (input.compareTo("l") == 0) {
        		current = current.left;
        	}
        	if (input.compareTo("r") == 0) {
        		current = current.right;
        	}
        	if (input.compareTo("m") == 0) {
        		current = current.mid;
        	}
        	if (input.compareTo(";") == 0) {
        		current = tst.head;
        	}
        	
        	tst.print(current);
        }
        
        Scannerinput.close();
        
        
        System.out.println("fin");
        
    }

}
