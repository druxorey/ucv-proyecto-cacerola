package test.Model.Services;

import org.junit.Test;
import Model.Services.EmailSender;
import static org.junit.Assert.*;

public class EmailSenderTest {
    @Test
    public void testSendEmailThrowsExceptionWithInvalidAddress() {
        try {
            EmailSender.sendEmail("invalid-email", "Test", "Body");
            fail("Expected an Exception to be thrown");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
        }
    }
}
