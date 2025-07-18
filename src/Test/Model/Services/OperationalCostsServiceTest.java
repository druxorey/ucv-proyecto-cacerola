package Test.Model.Services;

import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.OperationalCostsService;
import org.json.JSONObject;

public class OperationalCostsServiceTest {
    @Test
    public void testLoadOperationalCostsReturnsJSONObject() {
        OperationalCostsService service = new OperationalCostsService();
        JSONObject obj = service.loadOperationalCosts();
        assertNotNull(obj);
    }
}
