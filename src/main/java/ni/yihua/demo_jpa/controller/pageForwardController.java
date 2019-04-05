package ni.yihua.demo_jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class pageForwardController {

    public static long VISIT_COUNTS=0;

    @RequestMapping("/")
    public String forwardIndex(){
        VISIT_COUNTS++;
        return "index";
    }

}
