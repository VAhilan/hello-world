package com.mycase.rest.webservice.model;

public class Agreement {

	private String customerID;
	private String agreementID;
	private String agreementType;
	private String identifier;
	
	
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getAgreementID() {
		return agreementID;
	}
	public void setAgreementID(String agreementID) {
		this.agreementID = agreementID;
	}
	public String getAgreementType() {
		return agreementType;
	}
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
}
