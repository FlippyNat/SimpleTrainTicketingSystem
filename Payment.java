 /**
 * @(#)Payment.java
 *
 *
 * @author 
 * @version 1.00 2021/8/27
 */
import java.util.*;
import java.time.LocalDate;

public class Payment {
	private LocalDate transactionTime;
	private String paymentId;
	private double paymentAmount;
	private PaymentMethod paymentMethod;
	private String paymentMerchant;
	private final double rate=20.00;
	
	public Payment(){
	
	}

	public Payment(Reservation reservation){
		paymentAmount=calculatePrice(reservation);
	}

//Getter & Setter
    public Payment(LocalDate transactionTime, PaymentMethod paymentMethod,String paymentMerchant, Reservation reservation,int pRecordsCount) {
    	this.transactionTime = transactionTime;
		this.paymentMerchant=paymentMerchant;
    	paymentAmount=calculatePrice(reservation);
    	this.paymentMethod = paymentMethod;

		paymentId=generatePaymentId(paymentMethod.getPaymentMethod(), paymentMerchant, pRecordsCount);
    }
    
    public void setPaymentId(String paymentId){
    	this.paymentId = paymentId;
    }
    
    public void setPaymentAmount(double paymentAmount){
    	this.paymentAmount = paymentAmount;
    }
    
    public void setTransactionTime(LocalDate transactionTime){
		this.transactionTime = transactionTime;
	}

    public void setPaymentMethod(String payMethod){
    	paymentMethod.setPaymentMethod(payMethod);
    }
    
    public String getPaymentId(){
    	return paymentId;
    }
    
    public double getPaymentAmount(){
    	return paymentAmount;
    }
    
    public LocalDate getTransactionTime(){
		return transactionTime;
	}
    
    public PaymentMethod getPaymentMethod(){
    	return paymentMethod;
    }

	public String getPaymentMerchant(){
		return paymentMerchant;
	}

//functions
    public static String generatePaymentId(String paymentMethod, String pMerchant, int pRecordsCount){
    	String paymentId;
    	
    	paymentId = paymentMethod.substring(0,3) + pMerchant.substring(0,2) + Integer.toString(100 + pRecordsCount);
    	
    	return paymentId.toUpperCase();
    }
  
    public static void generateInvoice(Reservation reservation,Payment invoice,Customer customer){
   		String departDate = reservation.getDepartDate().format(Main.dateFormat);
		System.out.println("\n====================================================================");
		System.out.println("    /$$$$$$                               /$$                    		");
		System.out.println("   |_  $$_/                              |__/                    		");
		System.out.println("     | $$   /$$$$$$$  /$$    /$$ /$$$$$$  /$$  /$$$$$$$  /$$$$$$ 		");
		System.out.println("     | $$  | $$__  $$|  $$  /$$//$$__  $$| $$ /$$_____/ /$$__  $$		");
		System.out.println("     | $$  | $$  \\ $$ \\  $$/$$/| $$  \\ $$| $$| $$      | $$$$$$$$		");
		System.out.println("     | $$  | $$  | $$  \\  $$$/ | $$  | $$| $$| $$      | $$_____/		");
		System.out.println("    /$$$$$$| $$  | $$   \\  $/  |  $$$$$$/| $$|  $$$$$$$|  $$$$$$$		");
		System.out.println("   |______/|__/  |__/    \\_/    \\______/ |__/ \\_______/ \\_______/		");
		System.out.println("====================================================================");
		System.out.printf("Passenger Details:					Reservation Details:\n");
		System.out.printf("---------------------------------------------------------------------\n");
		System.out.printf("Passenger Name:%15s\t\tTicket ID:%18s\n",customer.getName(),reservation.getTicketID());
		System.out.printf("IC No:%24s\t\tOrigin:%21s\n",customer.getIcNo(),reservation.getOrigin());
		System.out.printf("\t\t\t\t\t\t\t\t\tDestination:%16s\n",reservation.getDestination());
		System.out.printf("\t\t\t\t\t\t\t\t\tDeparture Date:%13s\n",departDate);
		System.out.printf("---------------------------------------------------------------------\n");
		System.out.printf("\t\t\t\t\t\t\t\t\tAmount(RM):%17.2f\n",invoice.getPaymentAmount());

}   

   public static void generateReceipt(Reservation reservation, Payment payment,Customer customer){
   	 String departureDate = reservation.getDepartDate().format(Main.dateFormat);
   	 
   	System.out.println("\n====================================================================");
   	System.out.println(" /$$$$$$$                                /$$             /$$    		");
	System.out.println("| $$__  $$                              |__/            | $$    	");
	System.out.println("| $$  \\ $$  /$$$$$$   /$$$$$$$  /$$$$$$  /$$  /$$$$$$  /$$$$$$  	");
	System.out.println("| $$$$$$$/ /$$__  $$ /$$_____/ /$$__  $$| $$ /$$__  $$|_  $$_/  	");
	System.out.println("| $$__  $$| $$$$$$$$| $$      | $$$$$$$$| $$| $$  \\ $$  | $$    	");
	System.out.println("| $$  \\ $$| $$_____/| $$      | $$_____/| $$| $$  | $$  | $$ /$$");
	System.out.println("| $$  | $$|  $$$$$$$|  $$$$$$$|  $$$$$$$| $$| $$$$$$$/  |  $$$$/	");
	System.out.println("|__/  |__/ \\_______/ \\_______/ \\_______/|__/| $$____/    \\___/  	");
	System.out.println("                                            | $$                	");
	System.out.println("                                            | $$                	");
	System.out.println("                                            |__/                ");
	System.out.println("====================================================================");
	System.out.printf("Passenger Details:					Reservation Details:\n");
	System.out.printf("---------------------------------------------------------------------\n");
	System.out.printf("Passenger Name:%15s\t\tTicket ID:%18s\n",customer.getName(),reservation.getTicketID());
	System.out.printf("IC No:%24s\t\tOrigin:%21s\n",customer.getIcNo(),reservation.getOrigin());
	System.out.printf("Payment Type:%17s\t\tDestination:%16s\n",payment.getPaymentMethod().getPaymentMethod(),reservation.getDestination());
	System.out.printf("Payment Merchant:%13s\t\tDeparture Date:%13s\n",payment.getPaymentMerchant(),departureDate);
	System.out.printf("---------------------------------------------------------------------\n");
	System.out.printf("\t\t\t\t\t\t\t\t\tAmount(RM):%17.2f\n",payment.getPaymentAmount());
}

	public double calculatePrice(Reservation reservation){
	
	double origin=0,destination=0;
	double cost=0.0;

	for(int i=0;i<reservation.getTrain().getTrainRoute().getRouteLength();i++){
		if(reservation.getOrigin()==reservation.getTrain().getTrainRoute().getRoute(i)){
			origin=i;
		}
		else if(reservation.getDestination()==reservation.getTrain().getTrainRoute().getRoute(i)){
			destination=i;
		}

		cost=(double)(destination-origin)*rate;
	}
	return cost;
}

	public String toString(Reservation reservation){
		String pDate = transactionTime.format(Main.dateFormat);
		
		return String.format(" %-15s%-16.2f%-16s%-20s%-20s%-9s", paymentId, paymentAmount, pDate, paymentMethod.getPaymentMethod(), paymentMerchant, reservation.getTicketID());

	}
}