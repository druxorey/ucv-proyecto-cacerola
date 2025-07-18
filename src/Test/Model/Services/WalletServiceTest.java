package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.WalletService;

public class WalletServiceTest {
    @Test
    public void testGetBalanceReturnsZeroForNonexistentUser() {
        WalletService service = new WalletService();
        double balance = service.getBalance("nonexistent");
        assertEquals(0.0, balance, 0.001);
    }
}
