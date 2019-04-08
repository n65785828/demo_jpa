package ni.yihua.demo_jpa.controller;

import ni.yihua.demo_jpa.Respository.CustomerRepository;
import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository repository;

    /**
     * 初始化数据
     */
    @RequestMapping("/create")
    public String create(Model model) {
        // save a couple of customers
        model.addAttribute("hello","hello ni ma!!");
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
    public String findAll(){
        List<Customer> result = listCustomers(repository.findAll(), "-------------------------------------------");
        return "/customer/showCustomer";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(){
        System.out.println("删除前数据：");
        List<Customer> customers = listCustomers(repository.findAll(), "删除ID=3数据：");
        repository.delete(customers.get(3));

        System.out.println("删除后数据：");
        customers = repository.findAll();
        for (Customer customer:customers){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
        return "index";
    }

    @RequestMapping("/findByLastName")
    public String findByLastName(Model model,String lastName){
        List<Customer> result = repository.findByLastName1(lastName);
        for (Customer customer :
                result) {
            System.out.println(customer);
        }
        model.addAttribute("result",result);
        return "index";
    }

    @RequestMapping("/findByFirstName")
    public String findByFirstName(Model model,String firstName){
        List<Customer> result = repository.findByFirstName1(firstName);
        for (Customer customer :
                result) {
            System.out.println(customer);
        }
        model.addAttribute("result",result);
        return "index";
    }


    @RequestMapping("/findByName4")
    public String findByName4(){
        //按照ID倒序排列
        System.out.println("直接创建sort对象，通过排序方法和属性名");
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Customer> result = listCustomers(repository.findByName4("Bauer", sort), "-------------------------------------------");
        //按照ID倒序排列
        System.out.println("通过Sort.Order对象创建sort对象");
        Sort sortx = new Sort(new Sort.Order(Sort.Direction.DESC,"id"));
        List<Customer> resultx = listCustomers(repository.findByName4("Bauer", sort), "-------------------------------------------");

        System.out.println("通过排序方法和属性List创建sort对象");
        List<String> sortProperties = new ArrayList<>();
        sortProperties.add("id");
        sortProperties.add("firstName");
        Sort sort2 = new Sort(Sort.Direction.DESC,sortProperties);
        List<Customer> result2 = listCustomers(repository.findByName4("Bauer", sort2), "-------------------------------------------");

        System.out.println("通过创建Sort.Order对象的集合创建sort对象");
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC,"id"));
        orders.add(new Sort.Order(Sort.Direction.ASC,"firstName"));
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
}