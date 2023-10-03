/**
 * @(#)Bank.java
 *
 *
 * @author 
 * @version 1.00 2021/8/10
 */
import java.util.*;


public class Bank extends PaymentMethod {
	private String bankMerchant;


	public Bank(){

	}
       //Getter & Setter
	public String getBankMerchant(){
		return bankMerchant;
	}

	public String setBankMerchant(int choice){
		switch (choice){
			case 1:bankMerchant="Affin Bank";break;
			case 2:bankMerchant="Alliance Bank";break;
			case 3:bankMerchant="AmBank";break;
			case 4:bankMerchant="CIMB Bank";break;
			case 5:bankMerchant="Citibank";break;
			case 6:bankMerchant="Hong Leong Bank";break;
			case 7:bankMerchant="HSBC Bank";break;
			case 8:bankMerchant="MayBank";break;
			case 9:bankMerchant="Public Bank";break;
			case 10:bankMerchant="RHB Bank";break;
		}
		this.bankMerchant= bankMerchant;
		return bankMerchant;

	}

    public static void displayBanksAvailable(){
   
    		System.out.println(" Bank Merchant");
    		System.out.println("====================");
			System.out.println("1. Affin Bank");
			System.out.println("2. Alliance Bank");
			System.out.println("3. AmBank");
			System.out.println("4. CIMB Bank");
			System.out.println("5. Citibank");
			System.out.println("6. Hong Leong Bank");
			System.out.println("7. HSBC Bank");
			System.out.println("8. MayBank");
			System.out.println("9. Public Bank");
			System.out.println("10. RHB Bank");
    		System.out.printf("\nEnter your choice(1-10): ");
    }

    	
}