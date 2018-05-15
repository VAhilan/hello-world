package com.mycase.rest.webservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CustomerAgreementOrder")
public class AgreementOrder {

	private String personnr;
	private String name;
	private String address;
	private String mobilenr;
	private String agreementType;
	private String identifier;

	public String getPersonnr() {
		return personnr;
	}

	public void setPersonnr(String personnr) {
		this.personnr = personnr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobilenr() {
		return mobilenr;
	}

	public void setMobilenr(String mobilenr) {
		this.mobilenr = mobilenr;
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
