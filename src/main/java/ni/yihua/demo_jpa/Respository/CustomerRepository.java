package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_COMMENT;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



    @Query("select c from Customer c where c.firstName=?1")
    List<Customer> findByFirstName1(String bauer);

    @Query("select c from Customer c where c.lastName=?1")
    List<Customer> findByLastName1(String bauer);

    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    List<Customer> findByName4(@Param("name") String name2, Sort sort);

    @Modifying//更新查询
    @Transactional//开启事务
    @Query("update Customer c set c.firstName = ?1 where c.lastName = ?2")
    int setFixedFirstnameFor(String firstName, String lastName);


    @QueryHints(value = { @QueryHint(name = HINT_COMMENT, value = "a query for pageable")})
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    Page<Customer> findByName(@Param("name") String name2, Pageable pageable);

    @Query("SELECT c.firstName as firstName,c.lastName as lastName from Customer  c")
    Collection<CustomerProjection> findAllProjectedBy();
}
