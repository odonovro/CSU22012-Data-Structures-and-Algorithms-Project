import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dijkstra {
	
	static class directedEdge{
		Node node;
		int cost;
		directedEdge next;
		
		directedEdge(Node NODE, int type, String transfer_time){
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
        directedEdge next;

        Node(int sID){
            stopID = sID;
            next = null;
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
    			
    		if (isTransfers) {
    			while (txt.hasNextLine()) {
    		    		line = txt.nextLine();
    	    			String[] temp = line.split(",");
    	    			int from_stop = Integer. parseInt(temp[0]);
    	    			int to_stop = Integer. parseInt(temp[1]);
    	    			int type = Integer. parseInt(temp[2]);
    	    			String transfer_time;
    	    			if (type == 2) {transfer_time = temp[3];}
    	    			else {transfer_time = "";}
    				
    	    			Node curNode = arrayOfNodes[from_stop];
    	    			if (curNode.next == null) {
    	    				curNode.next = new directedEdge(arrayOfNodes[to_stop], type, transfer_time);
    	    			}
    	    			else {
    	    				directedEdge nextToCurNode = curNode.next;
    	    				while (true) {
    	    					if (nextToCurNode.next == null) {
    	    						nextToCurNode.next = new directedEdge(arrayOfNodes[to_stop], type, transfer_time);
    	    						break;
    	    					}
    	    					nextToCurNode = nextToCurNode.next;
    	    				}
    	    			}
    				}
    		}
    		else {
    			int last_stop = -1;
    			int tripID = -1;
				int sque = -1;
				
				int preTripID = -1;
				int preSque = -1;
				
    			while (txt.hasNextLine()) {
    				line = txt.nextLine();
    				String[] temp = line.split(",");
    				int cur_stop = Integer.parseInt(temp[3]);
    				int type = 1;
    				String transfer_time = "";
    				
    				tripID = Integer.parseInt(temp[0]);
    				sque = Integer.parseInt(temp[4]);
    				
    				if (tripID != -1 && preSque != -1) {
    					Node curNode = arrayOfNodes[last_stop];
    					if ((sque - preSque == 1 && tripID == preTripID)) {
    						if (curNode.next == null) {
    							curNode.next = new directedEdge(arrayOfNodes[cur_stop], type, transfer_time);
    						}
    						else {
    							directedEdge nextToCurNode = curNode.next;
    							while (true) {
    								if (nextToCurNode.next == null) {
    									nextToCurNode.next = new directedEdge(arrayOfNodes[cur_stop], type, transfer_time);
    									break;
    								}
    								nextToCurNode = nextToCurNode.next;
    							}
    						}
    					}
    					else if (sque - preSque > 0){System.out.println("oh no");}
    				}
    				last_stop = cur_stop;
    				preTripID = tripID;
    				preSque = sque;
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
        boolean[] visited = new boolean[intersections];
        for (int i = 0 ; i < intersections; i++) {
        	ShortPath[i][0] = Integer.MAX_VALUE;
        	visited[i] = false;
        }
        
        Node x = start;
        ShortPath[x.stopID][0] = 0 ;
        while (true) {
        	directedEdge nextToX = x.next;
            	while (true) { if (nextToX == null) {break;}
            		if ((ShortPath[nextToX.node.stopID][0] > nextToX.cost + ShortPath[x.stopID][0]) && ShortPath[x.stopID][0]!= Integer.MAX_VALUE) {
                		ShortPath[nextToX.node.stopID][0] = nextToX.cost + ShortPath[x.stopID][0]; 
                    	ShortPath[nextToX.node.stopID][1] = x.stopID;
                	}
            		nextToX = nextToX.next;
        		}
            visited[x.stopID] = true;

            int min_at = Integer.MAX_VALUE;
            double min = Integer.MAX_VALUE;
            for (int j = 0; j < intersections; j++) {
                if (ShortPath[j][0] < min && !visited[j]) {
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
     
//    public static void main(String args[])
//    {
//    	System.out.println("start \n");
//    	
//    	graph myGraph = new graph();
//    	
//    	File transFile = new File("transfers.txt");
//    	File stopTimeFile = new File("stop_times.txt");
//    	
//    	addToGraph(transFile, true, myGraph);
//    	addToGraph(stopTimeFile, false, myGraph);
//    	
//    	System.out.println("graph done");
//    	
//    	Scanner Scannerinput = new Scanner(System.in);
//        String input;
//        
//        
//        System.out.println("What is your Starting Node: ");
//        input = Scannerinput.nextLine();
//    	
//    	Node start = myGraph.arrayOfNodes[Integer.parseInt(input)];
//    	
//    	System.out.println("What is your Ending Node: ");
//    	input = Scannerinput.nextLine();
//    	
//    	int [][] test = runDijkstra(start, myGraph);
//    	
//    	System.out.println(test[Integer.parseInt(input)][0]);
//    	
//    	for (int i = 0 ; i < test.length ; i ++) {
//    		if (test[i][0] != Integer.MAX_VALUE) {
//    		System.out.println(i + " " + test[i][0] + " " +  test[i][1]);}
//    	}
//    	
//    	Scannerinput.close();
//    	System.out.println("fin");
//    }

}
