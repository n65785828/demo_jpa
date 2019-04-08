package ni.yihua.demo_jpa.controller;

import ni.yihua.demo_jpa.Respository.UserRepository;
import ni.yihua.demo_jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping("saveUser")
    public String createUsers() {
        User user = new User();
        user.setId(1l);
        user.setName("jack");
        user.setPassword("password$1");
        repository.save(user);
        return "/user/user";
    }

    @RequestMapping("get")
    public String getUsersByUserName() {
        List<User> users = repository.listUsersByName("jack");
        for (User user :
                users) {
            System.out.println(user);
        }
        return "/user/user";
    }

    @RequestMapping("list")
    public String listUsersByUserName() {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        List<User> users = repository.listUsersByNameAndSort("jack",sort);
        for (User user :
                users) {
            System.out.println(user);
        }


        sort = new Sort(Sort.Direction.DESC,"id");
        users = repository.listUsersByNameAndSort("jack",sort);
        for (User user :
                users) {
            System.out.println(user);
        }

        return "/user/user";
    }



}
