/**
 * @(#)PaymentMethod.java
 *
 *
 * @author 
 * @version 1.00 2021/8/10
 */


public class PaymentMethod {
	private String paymentMethod;

    public PaymentMethod() {
    }
    
    public PaymentMethod(String paymentMethod){
		this.paymentMethod = paymentMethod;
	}

    //Getter & Setter
    public void setPaymentMethod(String methodType){
    	paymentMethod = methodType;
    }
    
    public String getPaymentMethod(){
    	return paymentMethod;
    }
    

}