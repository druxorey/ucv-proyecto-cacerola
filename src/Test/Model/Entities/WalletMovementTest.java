package Test.Model.Entities;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Entities.WalletMovement;
import java.util.Date;

public class WalletMovementTest {
    @Test
    public void testWalletMovementConstructorAndGetters() {
        Date now = new Date();
        WalletMovement wm = new WalletMovement(now, "desc", 100.5);
        assertEquals(now, wm.getDate());
        assertEquals("desc", wm.getDescription());
        assertEquals(100.5, wm.getAmount(), 0.001);
    }
}
