package com.mycase.rest.webservice.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mycase.rest.webservice.MailService;

public class MailServiceMock{


	public void sendMail_Success() {
		MailService mailService = mock(MailService.class);
		
		when(mailService.sendMail()).thenReturn("Active");
		
		//return null;
	}

}
