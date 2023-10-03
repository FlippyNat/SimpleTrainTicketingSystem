public class Train {

	private String trainID;
	private int seatCount;
	private TrainRoute trainRoute;
	
    public Train() {
    	
    }
    
	public Train(String trainID,int seatCount,TrainRoute trainRoute){
		this.trainID=trainID;
		this.seatCount=seatCount;
		this.trainRoute=trainRoute;
	}
	//getters & setters
	public String getTrainID(){
		return trainID;
	}

	public int getSeatCount(){
		return seatCount;
	}

	public void setSeatCount(int seatCount){
		this.seatCount=seatCount;
	}

	public TrainRoute getTrainRoute(){
		return trainRoute;
	}
    
}