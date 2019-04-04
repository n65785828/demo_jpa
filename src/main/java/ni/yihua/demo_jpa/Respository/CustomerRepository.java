package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
