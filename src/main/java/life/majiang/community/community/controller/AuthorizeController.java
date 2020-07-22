package life.majiang.community.community.controller;

import life.majiang.community.community.dto.AccseeTokenDTO;
import life.majiang.community.community.dto.GithubUser;
import life.majiang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                            HttpServletRequest request) throws IOException {

        AccseeTokenDTO accseeTokenDTO = new AccseeTokenDTO();
        accseeTokenDTO.setClient_id(clientID);
        accseeTokenDTO.setClient_secret(clientSecret);
        accseeTokenDTO.setCode(code);
        accseeTokenDTO.setRedirect_uri(redirectUri);
        accseeTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accseeTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user!=null){
            request.getSession().setAttribute("user", user);
            return "redirect:/";
            //登录成功， 写cookies和session
        }else {
            //登录失败， 重新登录
            return "redirect:/";
        }
//        return "index";
    }
}
