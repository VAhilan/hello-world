package com.mycase.rest.webservice;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ahilan.mycase.CreateAgreementRequest;
import com.ahilan.mycase.CreateAgreementResponse;
import com.ahilan.mycase.CreateCustomerRequest;
import com.ahilan.mycase.CreateCustomerResponse;
import com.ahilan.mycase.Customer;
import com.ahilan.mycase.Agreement;
import com.ahilan.mycase.CustomerAgreementPortType;
import com.ahilan.mycase.UpdateStatus;
import com.ahilan.mycase.UpdateStatusRequest;
import com.ahilan.mycase.UpdateStatusResponse;
import com.mycase.rest.webservice.mock.MailServiceMock;
import com.mycase.soap.webservice.CustomerAgreementWsImplService;

@RestController
@RequestMapping("/createAgreement")
public class MyController {

	private static final String WSDL_URL = "http://localhost:8080/MySoapService/customerAgreementService?wsdl";
	private static final String STATUS = "Active";
	
	@RequestMapping(value="/{order}", method=RequestMethod.POST)
	public AgreementOrderResponse createAgreement(@PathVariable AgreementOrder order) throws MalformedURLException {
		AgreementOrderResponse resp=new AgreementOrderResponse();
		//TODO : get response from back-end and insert into response
		
		
		//Get the end-point
		CustomerAgreementWsImplService service=new CustomerAgreementWsImplService(new URL(WSDL_URL));
		CustomerAgreementPortType port=service.getCustomerAgreementWsImplPort();
		
		//Create CustomerRequest and call the service
		CreateCustomerResponse customerResp = callCreateCustomerService(order, port);
		
		//Create agreementRequest
		CreateAgreementResponse agreementResp = callCreateAgreementService(order, port, customerResp.getCustomerID());
		
		//Mock mail service?
		MailService mail = new MailServiceImpl();
		mail.sendMail();
		
		//Send status
		UpdateStatusResponse updateResp = callSendStatus(port, customerResp.getCustomerID(), agreementResp.getAgreementID());
		
		resp.setAgreementID(agreementResp.getAgreementID());
		return resp;
		
	}
	

	//Create CustomerRequest and call the service
	private CreateCustomerResponse callCreateCustomerService(AgreementOrder order, CustomerAgreementPortType port) {
		Customer customer = createCustomer(order);
		CreateCustomerRequest req=new CreateCustomerRequest();
		req.setCustomer(customer);
		CreateCustomerResponse customerResp=port.createCustomer(req);
		
	
		return customerResp;
	}

	private Customer createCustomer(AgreementOrder order) {
		Customer c = new Customer();
		
		c.setPersonnr(order.getPersonnr());
		c.setName(order.getName());
		c.setAddress(order.getAddress());
		c.setMobile(order.getMobilenr());
		return c;
	}
	
	private CreateAgreementResponse callCreateAgreementService(AgreementOrder order, CustomerAgreementPortType port, String customerID) {
		CreateAgreementRequest req = new CreateAgreementRequest();
		Agreement agreement = new Agreement();
		agreement.setCustomernr(customerID);
		agreement.setType(order.getAgreementType());
		agreement.setIdentifier(order.getIdentifier());
		
		req.setAgreement(agreement);
		
		CreateAgreementResponse agreementResp=port.createAgreement(req);
		return agreementResp;
	}
	
	private UpdateStatusResponse callSendStatus(CustomerAgreementPortType port, String customerID, String agreementID) {
		UpdateStatusRequest updateReq = new  UpdateStatusRequest();
		UpdateStatus updateStatus = new UpdateStatus();
		updateStatus.setCustomernr(customerID);
		updateStatus.setAgreementID(agreementID);
		updateStatus.setStatus(STATUS);
		
		updateReq.setUpdateStatus(updateStatus);
		
		return port.updateStatus(updateReq);
	}
	

	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String greeting(@PathVariable String name) {
		
		return "Hello " + name;	
	}
	
}
