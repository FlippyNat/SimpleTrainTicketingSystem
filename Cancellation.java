
import java.time.LocalDate;

public class Cancellation {
	//Variables
    private LocalDate cancellationTimestamp;
    private Reservation reservation;
    private Refund refund;

    //Constructors
    public Cancellation(){

    }

    public Cancellation(LocalDate cTimestamp,Reservation reservation){
    	this.cancellationTimestamp = cTimestamp;
    	this.reservation = reservation;
    	
    }

    //Setter and getters
    public void setCancellationtTimestamp(LocalDate cTimestamp){
    	this.cancellationTimestamp = cTimestamp;
    }

	public void setRefund(Refund refund){
		this.refund = refund;
	}

	public LocalDate getCancellationTimestamp(){
		return cancellationTimestamp;
	}

	public Refund getRefund(){
		return refund;
	} 

	public Reservation getReservation(){
		return reservation;
	}

    //functions
    public static void cancelReservationProcessCust(Reservation reservation,Cancellation cancel){
    	boolean isRefundAvailable = false;

    	LocalDate now = LocalDate.now();

    	isRefundAvailable = Cancellation.verifyRefundAvailability(cancel.getReservation().getPayment());

    	if(isRefundAvailable == true){
			Refund refund = new Refund(cancel.getReservation().getPayment().getPaymentAmount(),now);//Create refund object
			System.out.println("\n*==========================================================*");
			System.out.println("|~~~Congratulations! You are qualify to claim the refund~~~|");
			System.out.println("*==========================================================*");
			displayCancellation(cancel.getReservation());
			reservation.setStatus("CANCELLED");

			cancel.setRefund(refund);
		}
		else{
			System.out.println("*===================================================*");
			System.out.println("|~~~Sorry you are not qualify to claim the refund~~~|");
			System.out.println("*===================================================*");
			System.out.println("Refund Details:\n");
			displayCancellation(cancel.getReservation());
			System.out.printf("Reason              : Refund Expired (Refund only available within 1 day after making reservation)\n");
			reservation.setStatus("CANCELLED");
		}
		
    }

	public static void displayCancellation(Reservation reservation){
		System.out.println("+---------------+");
		System.out.println("|Refund Details:|");
		System.out.println("+---------------+");
		System.out.println("-------------------------------------------------");
		System.out.printf("|Ticket Id           : %s\t\t\t\t\t|\n",reservation.getTicketID());
		System.out.printf("|Reservation status  : Cancelled\t\t\t\t|\n");
		System.out.printf("|Payment Id          : %s\t\t\t\t\t|\n",reservation.getPayment().getPaymentId());
		System.out.printf("|Refund amount       : RM%.2f\t\t\t\t\t|\n",reservation.getPayment().getPaymentAmount());
		System.out.println("-------------------------------------------------\n");
	}

    public static boolean verifyRefundAvailability(Payment paymentRecord){
	
		if(paymentRecord.getTransactionTime().compareTo(LocalDate.now())==0){
			return true;	
		}
		else if(paymentRecord.getTransactionTime().compareTo(LocalDate.now())<1){
			return false;
		}
		else{
			return false;
		}
    }

	public String toString(){
		return String.format("%-20s%-16.2f",cancellationTimestamp,refund.getRefundAmount());
	}
}