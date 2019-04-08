package ni.yihua.demo_jpa.controller;

import ni.yihua.demo_jpa.Respository.CustomerProjection;
import ni.yihua.demo_jpa.Respository.CustomerRepository;
import ni.yihua.demo_jpa.Respository.CustomerSpecificationRepository;
import ni.yihua.demo_jpa.Respository.SpecificationFactory;
import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CustomerSpecificationRepository csr;

    /**
     * 初始化数据
     */
    @RequestMapping("/create")
    public String create(Model model) {
        // save a couple of customers
        model.addAttribute("hello", "hello ni ma!!");
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
        repository.save(new Customer("Bauer", "Dessler"));
        return "index";
    }

    /**
     * 查询所有
     */
    @RequestMapping("/findAll")
    public String findAll() {
        List<Customer> result = listCustomers(repository.findAll(), "-------------------------------------------");
        return "/customer/showCustomer";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete() {
        System.out.println("删除前数据：");
        List<Customer> customers = listCustomers(repository.findAll(), "删除ID=3数据：");
        repository.delete(customers.get(3));

        System.out.println("删除后数据：");
        customers = repository.findAll();
        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
        return "index";
    }

    @RequestMapping("/findByLastName")
    public String findByLastName(Model model, String lastName) {
        List<Customer> result = repository.findByLastName1(lastName);
        for (Customer customer :
                result) {
            System.out.println(customer);
        }
        model.addAttribute("result", result);
        return "index";
    }

    @RequestMapping("/findByFirstName")
    public String findByFirstName(Model model, String firstName) {
        List<Customer> result = repository.findByFirstName1(firstName);
        for (Customer customer :
                result) {
            System.out.println(customer);
        }
        model.addAttribute("result", result);
        return "index";
    }


    @RequestMapping("/findByName4")
    public String findByName4() {
        //按照ID倒序排列
        System.out.println("直接创建sort对象，通过排序方法和属性名");
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Customer> result = listCustomers(repository.findByName4("Bauer", sort), "-------------------------------------------");
        //按照ID倒序排列
        System.out.println("通过Sort.Order对象创建sort对象");
        Sort sortx = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
        List<Customer> resultx = listCustomers(repository.findByName4("Bauer", sort), "-------------------------------------------");

        System.out.println("通过排序方法和属性List创建sort对象");
        List<String> sortProperties = new ArrayList<>();
        sortProperties.add("id");
        sortProperties.add("firstName");
        Sort sort2 = new Sort(Sort.Direction.DESC, sortProperties);
        List<Customer> result2 = listCustomers(repository.findByName4("Bauer", sort2), "-------------------------------------------");

        System.out.println("通过创建Sort.Order对象的集合创建sort对象");
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        orders.add(new Sort.Order(Sort.Direction.ASC, "firstName"));
        List<Customer> result3 = listCustomers(repository.findByName4("Bauer", new Sort(orders)), "-------------------------------------------");
        return "index";
    }

    private List<Customer> listCustomers(List<Customer> bauer, String s) {
        List<Customer> result = bauer;
        for (Customer customer : result) {
            System.out.println(customer.toString());
        }
        System.out.println(s);
        return result;
    }

    @RequestMapping("modify")
    public String setFixedFirstnameFor() {
        Integer result = repository.setFixedFirstnameFor("Bauorx", "Bauer");
        if (result != null) {
            System.out.println("modifying result:" + result);
        }
        System.out.println("-------------------------------------------");
        return "index";
    }


    @RequestMapping("hints")
    public String doHints() {
        Pageable pageable = PageRequest.of(1, 2, Sort.Direction.DESC, "id");
        Page<Customer> customers = repository.findByName("bauer", pageable);
        System.out.println(customers.getTotalElements());
        System.out.println(customers.getTotalPages());
        for (Customer customer :
                customers.getContent()) {
            System.out.println(customer);
        }
        return "index";
    }

    @RequestMapping("/findAllProjections")
    public String findAllProjections() {
        Collection<CustomerProjection> projections = repository.findAllProjectedBy();
        System.out.println(projections);
        System.out.println(projections.size());
        for (CustomerProjection projection : projections) {
            System.out.println("-------------------------------------");
            System.out.println("FullName:" + projection.getFullName());
            System.out.println("FirstName:" + projection.getFirstName());
            System.out.println("LastName:" + projection.getLastName());
        }
        return "index";
    }

    @RequestMapping("/spec")
    public void specificationQuery(HttpServletResponse response) throws IOException {
        Specification<Customer> spec = SpecificationFactory.containsLike("lastName", "bau");
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<Customer> page = csr.findAll(spec, pageable);
        System.out.println(page);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Customer c : page.getContent()) {
            System.out.println(c.toString());
        }
        response.getWriter().println("ok");
    }

    @RequestMapping("/spec2")
    public void specificationQuery2(HttpServletResponse response) throws IOException{
        Specification<Customer> spec2 = Specification
                .where(SpecificationFactory.containsLike("firstName","bau"))
                .or(SpecificationFactory.containsLike("lastName","bau"));
        Pageable pageable = PageRequest.of(0,5, Sort.Direction.DESC,"id");
        Page<Customer> page = csr.findAll(spec2,pageable);
        System.out.println(page);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        for (Customer c:page.getContent()){
            System.out.println(c.toString());
        }
        response.getWriter().println("ok");
    }

}
