import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Reservation {
	
	private String ticketID,origin,destination,status;
	private LocalDate departDate;
	private Train train;
	private Payment payment;
	
    public Reservation() {
    	
    }
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

    public Reservation(Train train, String origin, String destination, LocalDate departDate,int reserveCount){
		ticketID=generateTicket(origin, destination,reserveCount);
		this.train=train;
		this.origin=origin;
    	this.destination=destination;
    	this.departDate=departDate;
		status="ACTIVE";
    }
	//getters & setters
	public String getTicketID(){
		return ticketID;
	}

	public void setTicketID(String ticketID){
		this.ticketID=ticketID;
	}

	public String getOrigin(){
		return origin;
	}

	public void setOrigin(String origin){
		this.origin=origin;
	}

	public String getDestination(){
		return destination;
	}

	public void setDestination(String destination){
		this.destination=destination;
	}

	public LocalDate getDepartDate(){
		return departDate;
	}

	public void setDepartDate(LocalDate departDate){
		this.departDate = departDate;
	}

	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}

	public Train getTrain(){
		return train;
	}

	public void setPayment(Payment payment){
		this.payment=payment;
	}

	public Payment getPayment(){
		return payment;
	}

	//functions
	public static String generateTicket(String origin, String destination,int reserveCount){
		String ticketID;
		String sequence;
		
		sequence=String.format("%03d",reserveCount);
		ticketID=String.format("%s%s%s",origin.charAt(0),destination.charAt(0),sequence);
		reserveCount++;

		return ticketID;
	}

	public String toString(){
		String departTime="",arriveTime="";

		for(int i=0;i<train.getTrainRoute().getRouteLength();i++){
			if(origin==train.getTrainRoute().getRoute(i)){
				departTime=train.getTrainRoute().getSchedule(i);

			}
			else if(destination==train.getTrainRoute().getRoute(i)){
				arriveTime=train.getTrainRoute().getSchedule(i);
			}
		}

		return String.format(" %-15s%-16s%-16s%-16s%-16s%-16s%s",ticketID, origin, destination, departDate.toString(),departTime,arriveTime,status);
	}
}