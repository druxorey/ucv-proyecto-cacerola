package Test.Model.Services;

import org.junit.Test;
import Model.Services.EmailService;
import static org.junit.Assert.*;

public class EmailSenderTest {

	@Test
	public void testSendEmailThrowsExceptionWithInvalidAddress() {
		try {
			EmailService.sendEmail("invalid-email", "Test", "Body");
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testSendEmailThrowsExceptionWithEmptyAddress() {
		try {
			EmailService.sendEmail("Test", "Body", "");
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void testSendEmailThrowsExceptionWithNullAddress() {
		try {
			EmailService.sendEmail("Test", "Body", null);
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}
	}


	@Test
	public void testSendEmailThrowsExceptionWithNullBody() {
		try {
			EmailService.sendEmail("Test", null, "test@example.com");
			fail("Expected an Exception to be thrown");
		} catch (Exception e) {
			assertNotNull(e.getMessage());
		}
	}
}