/**
 * Created on 2018/8/11.
 */
package jetcache.samples.springboot;

import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:areyouok@gmail.com">huangli</a>
 */
@Repository
public class UserServiceImpl implements UserService {

    private static User user = new User(1L, "李晓");

    @Override
    public User getUserById(long userId) {
        System.out.println("load user from database: " + userId);

        return user;
    }

    @Override
    public void updateUser(User user) {
        // System.out.println("cache Update" + user);
    }

    @Override
    public void deleteUser(long userId) {
        user = null;
        // System.out.println("cache delete" + userId);
    }
}
