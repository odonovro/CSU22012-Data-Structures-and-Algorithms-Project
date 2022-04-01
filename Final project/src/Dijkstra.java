import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dijkstra {
	
	static class linkedList{
		Node node;
		int cost;
		linkedList next;
		
		linkedList(Node NODE, int type, String transfer_time){
			node = NODE;
			next = null;
			
			if (type == 1) {
                cost = 1;
            }
            if (type == 0) {
                cost = 2;
            }
            if (type == 2) {
            	if (transfer_time == "") {throw new IllegalArgumentException("stop is type 2 but transfer_time is blank"); }
                cost = (Integer. parseInt(transfer_time)/100);
            }
		}
	}
	
    static class Node{
        int stopID;
        boolean visited;
        linkedList next;

        Node(int sID){
            stopID = sID;
            next = null;
            visited = false;
        }
    }
    
    static class graph{
    	Node[] arrayOfNodes;
    	
    	graph(){
    		Node[] temp = new Node[20000];
        	for (int i = 0 ; i < temp.length; i++) {
        		temp[i] = new Node(i);
        	}
        	arrayOfNodes = temp;
    	}
    }
    
    
    static void addToGraph(File file, boolean isTransfers, graph myGraph){
    	Node[] arrayOfNodes = myGraph.arrayOfNodes;
		try {
			Scanner txt = new Scanner(file);
	    	
	    	String line = txt.nextLine();
	    	while (txt.hasNextLine()) {
	    		line = txt.nextLine();
    			String[] temp = line.split(",");
    			
    			if (isTransfers) {
    				int from_stop = Integer. parseInt(temp[0]);
    				int to_stop = Integer. parseInt(temp[1]);
    				int type = Integer. parseInt(temp[2]);
    				String transfer_time;
    				if (type == 2) {transfer_time = temp[3];}
    				else {transfer_time = "";}
    				
    				Node curNode = arrayOfNodes[from_stop];
    				if (curNode.next == null) {
    					curNode.next = new linkedList(arrayOfNodes[to_stop], type, transfer_time);
    				}
    				else {
    				linkedList nextToCurNode = curNode.next;
    				while (true) {
    					if (nextToCurNode.next == null) {
    						nextToCurNode.next = new linkedList(arrayOfNodes[to_stop], type, transfer_time);
    						break;
    					}
    					nextToCurNode = nextToCurNode.next;
    				}}
    			}
    			else { //TODO
    				int from_stop = Integer. parseInt(temp[3]);
    				int to_stop = (Integer) null;
    				int type = 1;
    				String transfer_time = "";
    				
    				Node curNode = arrayOfNodes[from_stop];
    				
    				while (true) {
    					if (curNode.next == null) {
    						curNode.next = new linkedList(arrayOfNodes[to_stop], type, transfer_time);
    						break;
    					}
    				}
    			}
	    	}
	    	txt.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    static int[][] runDijkstra(Node start, graph myGraph) {
    	Node[] arrayOfNodes = myGraph.arrayOfNodes;
        int intersections = 20000;
        int[][] ShortPath = new int[intersections][2];
        for (int i = 0 ; i < intersections; i++) {
        	ShortPath[i][0] = Integer.MAX_VALUE;
        }
        
        Node x = start;
        ShortPath[x.stopID][0] = 0 ;
        
        for (int i = 0; i < intersections; i++) {
        	linkedList nextToX = x.next;
            	while (true) { if (nextToX == null) {break;}
            		if ((ShortPath[nextToX.node.stopID][0] > nextToX.cost + ShortPath[x.stopID][0]) && ShortPath[x.stopID][0]!= Integer.MAX_VALUE) {
                		ShortPath[nextToX.node.stopID][0] = nextToX.cost + ShortPath[x.stopID][0]; 
                    	ShortPath[nextToX.node.stopID][1] = x.stopID;
                	}
            		nextToX = nextToX.next;
        		}
            x.visited = true;

            int min_at = Integer.MAX_VALUE;
            double min = Integer.MAX_VALUE;
            for (int j = 0; j < intersections; j++) {
                if (ShortPath[j][0] < min && !arrayOfNodes[j].visited) {
                    min = ShortPath[j][0];
                    min_at = j; 
                }
            }
            if (min_at == Integer.MAX_VALUE) {
                break;
            }
            x = arrayOfNodes[min_at];
        }
        return ShortPath;
    }
     
    public static void main(String args[])
    {
    	System.out.println("start \n");
    	
    	graph myGraph = new graph();
    	
    	File myFile = new File("transfers.txt");
    	
    	addToGraph(myFile, true, myGraph);
    	
    	System.out.println("graph done");
    	
    	Node start = myGraph.arrayOfNodes[10860];
    	
    	int [][] test = runDijkstra(start, myGraph);
    	
    	for (int i = 0 ; i < test.length ; i ++) {
    		if (test[i][0] != Integer.MAX_VALUE) {
    		System.out.println(i + " " + test[i][0] + " " +  test[i][1]);}
    	}
    	
    	System.out.println("fin");
    }

}