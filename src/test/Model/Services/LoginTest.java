package test;
import org.junit.Test;
import static org.junit.Assert.*;
import Model.Services.UserService;
//import Model.Entities.User;
public class LoginTest{

    @Test 
    public void testAdminLoginSuccess(){
      UserService userService = new UserService();
      int result = userService.authenticate("12345678","adminpassword");
      assertEquals(2,result);
    }
    @Test
    public void testUserLoginSuccess() {
      UserService userService = new UserService();
      int result = userService.authenticate("87654321","userpassword");
      assertEquals(1,result);
    }

    @Test
    public void testLoginFailurePassword() {
      UserService userService = new UserService();
      int result = userService.authenticate("12345678","adMinpaswrR");
      assertEquals(0,result);
   }
   @Test
   public void testLoginFailureUserId() {
      UserService userService = new UserService();
      int result = userService.authenticate("12m4sq678","userpassword");
      assertEquals(0,result);
}
}