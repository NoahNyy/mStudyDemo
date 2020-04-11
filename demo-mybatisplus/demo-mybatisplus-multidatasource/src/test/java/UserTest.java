import org.junit.Test;
import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.service.dbone.IUserService;
import org.noahnyy.demo.mybatisplus.singledatasource.mybatisplus.multidatasource.service.dbtwo.IUserTwoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author niuyy
 * @since 2020/2/18
 */
public class UserTest extends BaseTest {

    @Autowired
    private IUserService oneUserService;
    @Autowired
    private IUserTwoService twoUserService;



    @Test
    public void testUser(){
        System.out.println(oneUserService.list());
        System.out.println(twoUserService.list());
    }
}
