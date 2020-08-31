package life.majiang.community.community.controller;

import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie :cookies){    //通过循环查找cookie中有没有用户对应的cookie
            if(cookie.getName().equals("token")){//根据cookie获得里面的token
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                System.out.println(cookie.getName());
                if(user!=null){//在用户不为空时在前端设置用户
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        System.out.println("name");
        return "index";
    }
}
