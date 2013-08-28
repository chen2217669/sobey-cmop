package com.sobey.test.mail;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.icegreen.greenmail.util.GreenMail;
import com.sobey.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class MailServerSimulatorTest extends SpringContextTestCase {

	@Autowired
	private GreenMail greenMail;

	@Test
	public void greenMail() {
		assertEquals(3025, greenMail.getSmtp().getPort());
	}
}
