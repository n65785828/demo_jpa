package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



    @Query("select c from Customer c where c.firstName=?1")
    List<Customer> findByFirstName1(String bauer);

    @Query("select c from Customer c where c.lastName=?1")
    List<Customer> findByLastName1(String bauer);


}
