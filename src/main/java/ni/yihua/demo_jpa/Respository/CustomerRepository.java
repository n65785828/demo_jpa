package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



    @Query("select c from Customer c where c.firstName=?1")
    List<Customer> findByFirstName1(String bauer);

    @Query("select c from Customer c where c.lastName=?1")
    List<Customer> findByLastName1(String bauer);

    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    List<Customer> findByName4(@Param("name") String name2, Sort sort);


}
