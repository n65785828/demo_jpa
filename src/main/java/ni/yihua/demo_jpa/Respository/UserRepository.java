package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select c from User c where c.name=?1")
    List<User> listUsersByName(String name);
}
