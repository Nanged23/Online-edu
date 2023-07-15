package com.atguigu.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.aclservice.service.IndexService;
import com.atguigu.commonutils.R;
import com.atguigu.security.security.DefaultPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("info")
    public R info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    /**
     * 获取权限
     *
     * @return
     */
    @GetMapping("menu")
    public R getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }

    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password,
                   HttpServletRequest request, HttpServletResponse response) {
        try {
            // 对用户进行认证
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null && new  DefaultPasswordEncoder().matches(password, userDetails.getPassword())) {
                // 设置用户会话信息
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
                // 重定向到首页
                return "redirect:/admin/index.html";
            } else {
                // 验证失败，返回错误信息
                request.setAttribute("error", "Incorrect username or password");
                return "/admin/login.html";
            }
        } catch (Exception ex) {
            // 处理异常
            request.setAttribute("error", "Error occurred: " + ex.getMessage());
            return "/admin/login.html";
        }
    }
}
