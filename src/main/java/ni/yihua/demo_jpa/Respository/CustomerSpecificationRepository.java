package ni.yihua.demo_jpa.Respository;

import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerSpecificationRepository extends JpaRepository<Customer,Long> , JpaSpecificationExecutor<Customer> {
}
