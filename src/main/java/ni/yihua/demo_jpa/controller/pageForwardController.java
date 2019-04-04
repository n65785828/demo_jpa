package ni.yihua.demo_jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class pageForwardController {


    @RequestMapping("/")
    public String forwardIndex(){
        return "index";
    }

}
