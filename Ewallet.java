/**
 * @(#)Ewallet.java
 *
 *
 * @author 
 * @version 1.00 2021/8/10
 */
import java.util.*;

public class Ewallet extends PaymentMethod {
	private String eWalletMerchant;

    public Ewallet() {
    }
    
    //Getter & Setter
	public String getEWalletMerchant(){
		return eWalletMerchant;
	}

	public String setEWalletMerchant(int choice){
		switch(choice){
			case 1:eWalletMerchant="Touch'n GO";break;
			case 2:eWalletMerchant="Boost";break;
			case 3:eWalletMerchant="GrabPay";break;
		}
		this.eWalletMerchant=eWalletMerchant;
		return eWalletMerchant;
	}
    
    public static void displayEwalletAvailable(){
    		System.out.println(" E-wallet Available");
    		System.out.println("======================");
    		System.out.println("1. Touch'n G0");
    		System.out.println("2. Boost");
    		System.out.println("3. GrabPay");
    		System.out.printf("Enter your choice(1-3): ");
    }
    
}	
    		
    	
    		    	
    	