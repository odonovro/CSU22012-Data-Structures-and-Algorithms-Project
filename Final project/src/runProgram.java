import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

public class runProgram {
	static void shortestPathBetween2Points(int startID, int endID, Dijkstra.graph myGraph){
		Dijkstra.Node start = myGraph.arrayOfNodes[startID];
    	
    	int [][] test = Dijkstra.runDijkstra(start, myGraph);
    	
    	int cost = test[endID][0];
    	
    	if(cost == Integer.MAX_VALUE) {
    		System.out.println("The distance from stop " + startID + " to stop " + endID + " is " + "inf");
    	}
    	else {
    		System.out.println("The distance from stop " + startID + " to stop " + endID + " is " + cost);
    	}
	}
	
	void SearchingForABusStopByFullName(){
		
	}
	
	static void SearchingForAllTripsWithAGivenArrivalTime(int H, int M, int S, searchByArrivalTime ADS){
		searchByArrivalTime.trip_details[] searchResults = ADS.search(H, M, S);
		searchByArrivalTime.insertionSort(searchResults);
		for (int i = 0 ; i < 1000000; i++) {
			if (searchResults[i] == null) {System.out.println("We found " + i + " results"); break;}
			System.out.println(searchResults[i].arrival_time + "	" +searchResults[i].trip_id);
		}
	}
	
	public static void main(String args[]){
		System.out.println("Program Started");
		//Files
		File transfersFile = new File("transfers.txt");
		File stopTimeFile = new File("stop_times.txt");
		File stopsFile = new File("stops.txt");
		
		//Load Dijkstra Graph from File
			System.out.println("Loading Dijkstra graph please wait...");
			Dijkstra.graph myGraph = new Dijkstra.graph();
			Dijkstra.addToGraph(transfersFile, true, myGraph);
			Dijkstra.addToGraph(stopTimeFile, false, myGraph);
			System.out.println("Dijkstra graph loaded");   
		
		//Load TST from File
			System.out.println("Loading TST please wait..."); 
			TST tst = new TST();
			tst.fileToTST(stopsFile);
			System.out.println("TST loaded"); 
			
		//Load ArrivalTime from File
			System.out.println("Loading ArrivalTime data please wait..."); 
			searchByArrivalTime ADS = new searchByArrivalTime(2000000);
			ADS.FileToData(stopTimeFile);
			System.out.println("ArrivalTime data loaded"); 
		
		System.out.println("Loading Done \n \n \n");
		
		int temp;
		Scanner Scannerinput = new Scanner(System.in);
		while (true) {
			System.out.println("Program Select: ");
			System.out.println("Type (1) for shortest path between 2 Points");
			System.out.println("Type (2) for searching for a bus stop by full name");
			System.out.println("Type (3) for searching for all trips with a given arrival time");
			System.out.println("Type (exit) to exit the program");
			String input = Scannerinput.nextLine();
			
			
			
			if (input.compareTo("1") == 0 || input.compareTo("(1)") == 0) {
				while (true) {
					String Start, End;
					System.out.println("\n \n \n You are running shortest path between 2 Points");
					System.out.println("If you want to return to Program Select type (return) otherwise press enter");
					input = Scannerinput.nextLine();
					if (input.compareTo("return") == 0 || input.compareTo("(return)") == 0) {
						break;
					}
					else {
						while (true) {
							System.out.println("Please enter the stop ID of your starting stop");
							Start = Scannerinput.nextLine();
							try {
								temp = Integer.parseInt(Start);
								if (myGraph.arrayOfNodes.length > temp) {
									break;
								}
								else {
									System.out.println("Your input is out of range for Stop ID");
								}
							}
							catch(Exception e){
								System.out.println("Please enter a integer for the stop ID");
							}
						}
						while (true) {
							System.out.println("Please enter the stop ID of your ending stop");
							End = Scannerinput.nextLine();
							try {
								temp = Integer.parseInt(End);
								if (myGraph.arrayOfNodes.length > temp) {
									break;
								}
								else {
									System.out.println("Your input is out of range for Stop ID");
								}
							}
							catch(Exception e){
								System.out.println("Please enter a integer for the stop ID");
							}
						}
					}
					shortestPathBetween2Points(Integer.parseInt(Start), Integer.parseInt(End), myGraph);
				}
			}
			
			
			
			if (input.compareTo("2") == 0 || input.compareTo("(2)") == 0) {
				while (true) {
					System.out.println("\n \n \n You are running search for a bus stop by full name");
					System.out.println("If you want to return to Program Select type (return) otherwise press enter");
					input = Scannerinput.nextLine();
					if (input.compareTo("return") == 0 || input.compareTo("(return)") == 0) {
						break;
					}
					else {
						Iterable<Info> queue = new Queue<Info>();
						System.out.println("Please enter first few characters of bus stops name: ");
						input = Scannerinput.nextLine();
						queue = tst.infoWithPrefix(" " + input);
						Iterator<Info> current = queue.iterator();
						System.out.println("\n List of bus stops is : The result will appear in the following format");
						System.out.println("stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station");
						while (true) {
							if (!current.hasNext()) break;
							Info info = current.next();
							System.out.println(info.stopID + "," + info.code + "," + info.name + "," + info.desc + "," + info.lat + "," + info.lon + "," + info.zoneID + "," + info.url + "," + info.type + "," + info.parentStation);
						}
					}
				}
			}
			
			
			
			if (input.compareTo("3") == 0 || input.compareTo("(3)") == 0) {
				while (true) {
					String inputH, inputM, inputS;
					int H, M, S;
					System.out.println("\n \n \n You are running search for all trips with a given arrival time");
					System.out.println("If you want to return to Program Select type (return) otherwise press enter");
					input = Scannerinput.nextLine();
					if (input.compareTo("return") == 0 || input.compareTo("(return)") == 0) {
						break;
					}
					else {
						System.out.println("Hours");
				        inputH = Scannerinput.nextLine();
						while (true) {
							System.out.println("Hours");
					        inputH = Scannerinput.nextLine();
				        	try {
				        		H = Integer.parseInt(inputH);
				        		if (H < 24 && H >= 0) {
				        			break;
				        		}
				        		else {
				        			System.out.println("invalid input");
				        			System.out.println("Hours");
				        			inputH = Scannerinput.nextLine();
				        		}
				        	}
				        	catch(Exception e){
				        		System.out.println("Hours");
				            	inputH = Scannerinput.nextLine();
				        	}
				        }
				        System.out.println("Minites");
				        inputM = Scannerinput.nextLine();
				        while (true) {
				        	try {
				        		M = Integer.parseInt(inputM);
				        		if (M < 60 && M >= 0) {
				        			break;
				        		}
				        		else {
				        			System.out.println("invalid input");
				        			System.out.println("Minites");
				        			inputM = Scannerinput.nextLine();
				        		}
				        	}
				        	catch(Exception e){
				        		System.out.println("Minites");
				            	inputM = Scannerinput.nextLine();
				        	}
				        }
				        System.out.println("Seconds");
				        inputS = Scannerinput.nextLine();
				        while (true) {
				        	try {
				        		S = Integer.parseInt(inputS);
				        		if (S < 60 && S >= 0) {
				        			break;
				        		}
				        		else {
				        			System.out.println("invalid input");
				        			System.out.println("Seconds");
				        			inputS = Scannerinput.nextLine();
				        		}
				        	}
				        	catch(Exception e){
				        		System.out.println("Seconds");
				            	inputS = Scannerinput.nextLine();
				        	}
				        }
					}
				SearchingForAllTripsWithAGivenArrivalTime(H, M, S, ADS);
				}
			}
			if (input.compareTo("exit") == 0 || input.compareTo("(exit)") == 0) {
				break;
			}
		}
		Scannerinput.close();
		System.out.println("Program Ended");
	}
}
