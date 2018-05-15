package com.mycase.client.ws;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mycase.client.ws.model.CustomerAgreementOrder;

public class CustomerAgreementWSClient {

	private static final String MY_SERVICE_URL = "http://localhost:8080";

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();
		
		/** For testing with get method **/
		String path = "RestServiceProject/createAgreement/Ahilan";
		WebTarget getTarget = client.target(MY_SERVICE_URL).path(path);
		Builder getRequest = getTarget.request();
		Response getResp = getRequest.get();
		
		System.out.println("Response: " + getResp.getStatus());
		
		WebTarget target = client.target(MY_SERVICE_URL).path("RestServiceProject/createAgreement");

		Builder request = target.request();

		// Create an order
		CustomerAgreementOrder order = getCustomerAgreementOrder();

		Response resp = request.post(Entity.entity(order, MediaType.APPLICATION_XML));

		System.out.println("Agreement Id: " + resp.getStatus());
		
		//close resources
		resp.close();
		client.close();

	}

	private static CustomerAgreementOrder getCustomerAgreementOrder() {
		CustomerAgreementOrder order = new CustomerAgreementOrder();

		order.setPersonnr("12345");
		order.setName("Ahilan");
		order.setAddress("Oslo");
		order.setMobilenr("9870");
		order.setAgreementType("Bil");
		order.setIdentifier("EK13");
		return order;
	}

}
