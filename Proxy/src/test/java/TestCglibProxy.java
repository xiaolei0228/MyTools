import com.iunet.cglib_proxy.proxy.CheckPermissionProxy;
import com.iunet.cglib_proxy.service.impl.LoginServiceImpl;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: haoxiaolei
 * Date: 13-7-4
 * Time: 下午6:37
 * To change this template use File | Settings | File Templates.
 */
public class TestCglibProxy {

    @Test
    public void testCglibProxy() {
        CheckPermissionProxy proxy = new CheckPermissionProxy();
        LoginServiceImpl loginService = new LoginServiceImpl();
        //MyCut myCut = new MyCut();
        //try {
        //    Method method = myCut.getClass().getMethod("cutSomething", String.class);       // 要切入的方法
        //    loginService = (LoginServiceImpl) proxy.getInstance(loginService, myCut, method, "单车上的理想");
        //    loginService.addLoginLogs();
        //} catch (NoSuchMethodException e) {
        //    e.printStackTrace();
        //}
    }
}
