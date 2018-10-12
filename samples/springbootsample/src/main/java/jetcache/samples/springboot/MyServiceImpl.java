/**
 * Created on 2018/8/11.
 */
package jetcache.samples.springboot;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Component
public class MyServiceImpl implements MyService {
    @CreateCache(name = "myServiceCache", expire = 60)
    private Cache<String, String> cache;

    @Autowired
    private UserService userService;

    @Override
    public void createCacheDemo() {

    }

    @Override
    public void cachedDemo() {
        userService.getUserById(1L);
        User user = userService.getUserById(1L);
        System.out.println("origin user=" + user);
        user.setUserName("99999999");
        userService.updateUser(user);
        user = userService.getUserById(1L);
        System.out.println("update user=" + user);
        userService.deleteUser(1L);
        user = userService.getUserById(1L);
        System.out.println("final user=" + user);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
