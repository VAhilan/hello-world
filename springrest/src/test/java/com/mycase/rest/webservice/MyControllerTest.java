package com.mycase.rest.webservice;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

class MyControllerTest {

	@Test
	void testCreateustomeragreement() throws MalformedURLException {
		
		MyController testClass = new MyController();
		AgreementOrder order = new AgreementOrder();
		order.setPersonnr("1234");
		order.setName("Ahilan");
		order.setAddress("Bergen");
		order.setMobilenr("987");
		order.setAgreementType("Hytte");
		order.setIdentifier("Torggate");
		
		AgreementOrderResponse resp=testClass.createAgreement(order);
		
		System.out.println("AgreementID : " + resp.getAgreementID());
		
		assertTrue(resp.getAgreementID() != null);
		//fail("Not yet implemented");
	}

}
