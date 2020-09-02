package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccseeTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.mapper.UserMapper;
import life.majiang.community.community.model.User;
import life.majiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private  String clientID;
    @Value("${github.client.secret}")
    private  String clientSecret;
    @Value("${github.redirect.uri}")
    private  String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {

        AccseeTokenDTO accseeTokenDTO = new AccseeTokenDTO();
        accseeTokenDTO.setClient_id(clientID);
        accseeTokenDTO.setClient_secret(clientSecret);
        accseeTokenDTO.setCode(code);
        accseeTokenDTO.setRedirect_uri(redirectUri);
        accseeTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accseeTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //;
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            System.out.println(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user); //插入数据库完成。相当于写入session
            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user", githubUser);
            final String s = "redirect:/";
            return "redirect:/";
            //登录成功， 写cookies和session
        }else {
            System.out.println("run over! ");
            String s = "redirect:/";
            return s;
            //登录失败， 重新登录
        }
//        System.out.println("run over! ");
//        String s = "redirect:/";
//        return s;
//        return "index";
    }
}
