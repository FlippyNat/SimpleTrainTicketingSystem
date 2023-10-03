import java.time.LocalTime;

public class TrainRoute {

	private String[] schedule;
	private String[] route;
	private String direction;

	public TrainRoute(){
		
	}
	
    public TrainRoute(int routeType,int scheduleType) {
		setRoute(routeType);
		setDirection(routeType);
		setSchedule(scheduleType);

	}
    //getters & setters
	public void setRoute(int routeType){
		if(routeType==1){
		route=new String[]{"Arau","Alor Setar","Butterworth","Ipoh","Sungai Buloh","KL Sentral","Seremban","Batang Melaka"};
		}
		else{
			route=new String[]{"Batang Melaka","Seremban","KL Sentral","Sungai Buloh","Ipoh","Butterworth","Alor Setar","Arau"};
		}
	}

	public void setSchedule(int scheduleType){
		if(scheduleType==1){
			schedule=new String[]{"07:00:00","09:00:00","11:00:00","13:00:00","15:00:00","17:00:00","19:00:00","21:00:00"};
		}
		else{
			schedule=new String[]{"08:00:00","10:00:00","12:00:00","14:00:00","16:00:00","18:00:00","20:00:00","22:00:00"};
		}
	}

	public void setDirection(int routeType){
		if(routeType==1){
			direction="forward";
		}
		else {
			direction="backward";
		}
	}

	public String getDirection(){
		return direction;
	}

	public String[] getRoute(){
		return route;
	}

	public String getRoute(int input){
		return route[input];
	}

	public int getRouteLength(){
		return route.length;
	}

	public String[] getSchedule(){
		return schedule;
	}

	public String getSchedule(int input){
		return schedule[input];
	}

	public int getScheduleLength(){
		return schedule.length;
	}


}