package ni.yihua.demo_jpa.controller;

import ni.yihua.demo_jpa.Respository.CustomerRepository;
import ni.yihua.demo_jpa.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        List<Customer> result = repository.findAll();
        for (Customer customer:result){
            System.out.println(customer.toString());
        }
        System.out.println("-------------------------------------------");
        return "/customer/showCustomer";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(){
        System.out.println("删除前数据：");
        List<Customer> customers = repository.findAll();
        for (Customer customer:customers){
            System.out.println(customer.toString());
        }

        System.out.println("删除ID=3数据：");
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
}