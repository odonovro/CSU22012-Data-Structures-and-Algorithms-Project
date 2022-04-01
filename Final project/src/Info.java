public class Info{
			String stopID;
			String code;
			String name;
			String desc;
			String lat;
			String lon;
			String zoneID;
			String url;
			String type; 
			String parentStation;
			
			//Info(String stop_id, String stop_code, String stop_name, String stop_desc, String stop_lat, String stop_lon, String zone_id, String stop_url, String location_type, String parent_station){
			Info(String[] INFO){				
				stopID = INFO[0];
				code = INFO[1];
				name = INFO[2];
				desc = INFO[3];
				lat = INFO[4];
				lon = INFO[5];
				zoneID = INFO[6];
				url = INFO[7];
				type = INFO[8];
				parentStation = INFO[9];
			}
			
		}
