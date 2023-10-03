/**
 * @(#)Refund.java
 *
 *
 * @author 
 * @version 1.00 2021/8/30
 */


import java.util.*;
import java.time.LocalDate;

public class Refund {
	//Variables
	private double refundAmount;
	private LocalDate refundTimestamp;

    //Constructors
	public Refund(){

	}

	public Refund(double refundAmount,LocalDate refundTimestamp){
		this.refundAmount = refundAmount;
		this.refundTimestamp = refundTimestamp;
	}

	//Setters and getters
	public void setRefundAmount(double refundAmount){
		this.refundAmount = refundAmount;
	}

	public void setRefundTimestamp(LocalDate refundTimestamp){
		this.refundTimestamp = refundTimestamp;
	}

	public double getRefundAmount(){
		return refundAmount;
	}

	public LocalDate getRefundTimestamp(){
		return refundTimestamp;
	}

	public String toString(){
		return String.format("%s\t%.2f", refundTimestamp,refundAmount);
	}

}