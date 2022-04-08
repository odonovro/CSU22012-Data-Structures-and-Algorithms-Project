import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class searchByArrivalTime {
	
	//trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
	static class trip_details{
		
		int trip_id;
		String arrival_time;
		String departue_time;
		int stop_id;
		int stop_sequence;
		String stop_headsign;
		String pickup_type;
		String drop_off_type;
		String shape_dist_traveled;
		
		int arr_hour;
		int arr_min;
		int arr_sec;
		
		trip_details(String[] details){
			trip_id = Integer.parseInt(details[0]);
			arrival_time = details[1];
				String[] temp = arrival_time.split(":");
				arr_hour = Integer.parseInt(temp[0].split(" ")[temp[0].split(" ").length-1]);
				arr_min = Integer.parseInt(temp[1]);
				arr_sec = Integer.parseInt(temp[2]);
			
			departue_time = details[2];
			stop_id = Integer.parseInt(details[3]);
			stop_sequence = Integer.parseInt(details[4]);
			stop_headsign = details[5];
			pickup_type = details[6];
			drop_off_type = details[7];
			
			if (details.length == 9) {
			shape_dist_traveled = details[8];
			}
			else {
				shape_dist_traveled = "";
//				for(int i = 0 ; i < details.length ; i++) {
//					System.out.println(details[i]);
//					}
				}
			}
		}
	
	trip_details[] allData;
	searchByArrivalTime(int size){
		allData = new trip_details[size];
	}
	
	void FileToData(File file) {
		int i = 0;
		try {
			Scanner txt = new Scanner(file);
	    	String line = txt.nextLine();
    		while (txt.hasNextLine()) {
    			line = txt.nextLine();
    	    	String[] temp_string = line.split(",");
    	    	trip_details temp_details= new trip_details(temp_string);
    	    	
    	    	if(temp_details.arr_hour >= 0 && temp_details.arr_hour < 24 && temp_details.arr_min >= 0 && temp_details.arr_min < 60 && temp_details.arr_sec >= 0 && temp_details.arr_sec < 60) {
    	    		this.allData[i] = temp_details;
    	    		i++;
    	    	}
    		}
	    	txt.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	static trip_details[] insertionSort (trip_details[] a){
    	
		trip_details temp;
    	for (int i = 1; i < a.length ; i++) {
    		for (int j = i; j>0  ; j--) {
    			if (a[j] == null) {break;}
    			if (a[j].trip_id < a[j-1].trip_id) {
    				temp = a[j];
    				a[j] = a[j-1];
    				a[j-1] = temp;
    			}
    		}
    	}
    	return a;

        //todo: implement the sort
    }//end insertionsort
	
	trip_details[] search(int hour, int min, int sec){
		int j = 0;
		trip_details[] arr = new trip_details[128];
		for (int i = 0 ; i < this.allData.length ; i++) {
			if(this.allData[i] == null) {break;}
			
			if (hour == this.allData[i].arr_hour && min == this.allData[i].arr_min && sec == this.allData[i].arr_sec) {
				arr[j] = this.allData[i];
				j++;
				if (j == arr.length - 2) {
					trip_details[] temp = new trip_details[arr.length*2];
					for (int k = 0 ; k < arr.length; k++) {
						temp[k] = arr[k];
					}
					arr = temp;
				}
			}
		}
		return arr;
	}	
}
