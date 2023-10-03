public class Customer extends User{
	//Variables
	private String phoneNo;
	private Reservation[] reservation=new Reservation[100];
	private Cancellation[] cancellation=new Cancellation[100];
	private int reserveCount=0;
	private int cancelCount=0;

	//Constructors
	public Customer(){

	}

	public Customer(String name,String phoneNo,String icNo,String password){
		super(name,password,icNo);

		this.phoneNo = phoneNo;
		
	}

	//Setters and getters
	public void setPhoneNo(String phoneNo){
		this.phoneNo = phoneNo;
	}

	public void setReservation(Reservation[] reservation){
		this.reservation = reservation;
	}

	public void setReservation(Reservation reservation){
		this.reservation[reserveCount]=reservation;
		reserveCount++;
	}

	public String getPhoneNo(){
		return phoneNo;
	}

	public Reservation[] getReservation(){
		return reservation;
	}
	
	public int getReserveCount(){
		return reserveCount;
	}

	public Reservation getReservation(int input){
		return reservation[input];
	}

	public Cancellation getCancellation(int input){
		return cancellation[input];
	}

	public void setCancellation(Cancellation cancellation){
		this.cancellation[cancelCount]=cancellation;
		cancelCount++;
	}

	public int getCancelCount(){
		return cancelCount;
	}



}