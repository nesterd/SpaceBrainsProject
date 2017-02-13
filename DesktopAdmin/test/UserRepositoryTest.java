import com.spacebrains.core.dao.UserRepository;
import com.spacebrains.model.Role;
import com.spacebrains.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserRepositoryTest {
    UserRepository repository;
    User user;

    @Before
    public void prepare() {
        repository = new UserRepository();
        user = new User(2,"admin", "testAdmin", "admin@admin.ru", "", Role.ADMIN);
        user.setPswd("654321");
    }

    @Test
    public void testLogin() {
        User testAdmin = repository.login("testAdmin", "654321");
        user.setAccessCode(testAdmin.getAccessCode());
        Assert.assertEquals(user, repository.login("testAdmin", "654321"));
    }
}
