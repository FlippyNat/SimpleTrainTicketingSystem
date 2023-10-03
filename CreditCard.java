public class CreditCard extends PaymentMethod  {
	private String creditCardNo;
	private String creditCardMerchant;

    public CreditCard() {
    }


    
    //Getter & Setter
    public void setCreditCardNo(String creditCardNo){
    	this.creditCardNo = creditCardNo;
    }
    
    public String getCreditCardNo(){
    	return creditCardNo;
    }


	public String setCreditCardMerchant(int choice){
		switch (choice){
			case 1:creditCardMerchant="Visa";break;
			case 2:creditCardMerchant="Mastercard";break;
		}
		this.creditCardMerchant=creditCardMerchant;
		return creditCardMerchant;
	}

	public String getCreditCardMerchant(){
		return creditCardMerchant;
	}
    
    public static void displayCreditCardAvailable(){

    		System.out.println(" Credit card Available");
    		System.out.println("======================");
    		System.out.println("1. Visa");
    		System.out.println("2. Mastercard");
    		System.out.printf("\nEnter your choice(1-2): ");
   }

}
