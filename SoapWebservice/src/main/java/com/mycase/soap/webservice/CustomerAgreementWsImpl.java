package com.mycase.soap.webservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ahilan.mycase.Agreement;
import com.ahilan.mycase.CreateAgreementRequest;
import com.ahilan.mycase.CreateAgreementResponse;
import com.ahilan.mycase.CreateCustomerRequest;
import com.ahilan.mycase.CreateCustomerResponse;
import com.ahilan.mycase.Customer;
import com.ahilan.mycase.CustomerAgreementPortType;
import com.ahilan.mycase.UpdateStatusRequest;
import com.ahilan.mycase.UpdateStatusResponse;

public class CustomerAgreementWsImpl implements CustomerAgreementPortType {

	//Key = customerID
	Map<String, Customer> customers = new HashMap<>();
	
	//key = customerID
	Map<String, List<Agreement>> agreements = new HashMap<>();
	
	int iCustomerID, iAgreementID;
	
	
	/**
	 * 
	 */
	/*public void init() {
		++iCustomerID;
		++iAgreementID;
		Customer c = new Customer();
		c.setPersonnr("12345");
		c.setName("Ahian");
		c.setAddress("Oslo");
		c.setMobile("6789");
		
		customers.put(Integer.toString(iCustomerID), c);
		
		Agreement a = new Agreement();
		a.setCustomernr(Integer.toString(iCustomerID));
		a.setType("Bil");
		a.setIdentifier("AJ12345");
		
		agreements.put(Integer.toString(iAgreementID), a);
		
	}*/
	
	
/*** Bettr to use int for personnr and customerID? ****/
	
	@Override
	public CreateCustomerResponse createCustomer(CreateCustomerRequest parameters) {
		int newCustomerID;
		
		Customer c = parameters.getCustomer();
		
		//Check customer is already in our list
		Customer customerFound = findCustomerID(c.getPersonnr());
		
		if (customerFound == null) {
			newCustomerID = ++iCustomerID;
			String cID = Integer.toString(iCustomerID);
			c.setCustomerID(cID);
			customers.put(cID, c);
		}
		else newCustomerID = Integer.parseInt(customerFound.getCustomerID());
		
		CreateCustomerResponse resp = new CreateCustomerResponse();
		resp.setCustomerID(Integer.toString(newCustomerID));
		return resp;
	}

	private Customer findCustomerID(String personnr) {
		Set<String> keys = customers.keySet();
		
		for(String s: keys) {
			Customer c = customers.get(s);
			String pNr = c.getPersonnr();
			if (pNr.equalsIgnoreCase(personnr)) return c;
		}
		return null;
	}

	@Override
	public CreateAgreementResponse createAgreement(CreateAgreementRequest parameters) {
		
		//TODO: Go through this method and fix agreementID from earlier agreements
		List<Agreement> list;// = new ArrayList<>();		
		
		int aIDResp;
		
		//Get agreement from request
		Agreement a = parameters.getAgreement();
		a.setAgreementID(Integer.toString(iAgreementID));
		
		//If customer has any agreement before, get that list
		list = getCustomerAgreementList(a.getCustomernr());
		
		if (list==null) {
			list = new ArrayList<>();
			
			//Increment agreementID
			aIDResp = ++iAgreementID;
		} else {
			//Has customer same agreement from before, return that agreementID
			int agreementIDFound = getAgreementIDFromList(list, a.getType());
			
			if(agreementIDFound < 0) {
				//Increment agreementID
				aIDResp = ++iAgreementID;
			}else {
				aIDResp = agreementIDFound;
			}
		}
		
		
		list.add(a);
		
		//Put this customer with new list into agreements map
		agreements.put( a.getCustomernr(), list);
		
		//Return agreementID in response
		CreateAgreementResponse resp = new CreateAgreementResponse();
		resp.setAgreementID(Integer.toString(iAgreementID));
		return resp;
	}

	private int getAgreementIDFromList(List<Agreement> list, String type) {
		for(Agreement a:list) {
			if(a.getType().equalsIgnoreCase(type)) return Integer.parseInt(a.getAgreementID());
		}
		return -1;
	}

	private List<Agreement> getCustomerAgreementList(String customernr) {
		
		List<Agreement> list = null;
		
		if ( !agreements.isEmpty() ) {
			for(String s: agreements.keySet()) {
				if(s.equalsIgnoreCase(customernr)) list = agreements.get(s);
			}
		}
		
		return list;
	}

	@Override
	public UpdateStatusResponse updateStatus(UpdateStatusRequest parameters) {
		UpdateStatusResponse myResp = new UpdateStatusResponse();
		myResp.setStatus("Active");
		return myResp;
	}

}
