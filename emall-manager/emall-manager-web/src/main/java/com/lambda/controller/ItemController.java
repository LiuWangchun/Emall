package com.lambda.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lambda.pojo.Content;
import com.lambda.pojo.Person;
import com.lambda.pojo.Trx;
import com.lambda.service.ItemService;
import com.lambda.service.LogService;
import com.lambda.service.UserService;

/**
 * 异步数据接口Controller
 */

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Resource  
    private UserService userManager;
        
    /**
     * 1.登录
     */
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("userName") String userName, @RequestParam("password") String password, HttpSession httpSession) {
        Person user = userService.getUserByName(userName);
        
        if (user != null && user.getPassword().equals(password)){
            //保存session
            httpSession.setAttribute("manager", user); 
            //登录成功
            return "{\"code\":\"200\",\"message\":\"success\",\"result\":\"true\"}";    
        }else {
            //登录失败
            return "{\"code\":\"500\",\"message\":\"login failed\",\"result\":\"false\"}";
        }
    }
    
    /**
     * 2.删除商品
     */
    @RequestMapping(value = "/api/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(@RequestParam("id") Integer id, Person user, HttpSession httpSession) {
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            if (user.getUsertype() != 1){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        
        if (itemService.deleteItemById(id)){
            //删除成功
            return "{\"code\":\"200\",\"message\":\"success\",\"result\":\"true\"}";    
        }else {
            //删除失败
            return "{\"code\":\"500\",\"message\":\"failed\",\"result\":\"false\"}";
        }
    }
    
    /**
     * 3.购买商品
     */
    @RequestMapping(value = "/api/buy", method = RequestMethod.POST)
    @ResponseBody
    public String buy(@RequestParam("id") Integer id, Trx log, Person user, HttpSession httpSession) {
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            if (user.getUsertype() != 0){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        
        Content tbItem = itemService.getItemById(id);
        
        log.setPersonid(user.getId());
        log.setContentid(id);
        log.setBuyprice(tbItem.getPrice());
        
        if (logService.addLog(log)){
            //购买成功
            return "{\"code\":\"200\",\"message\":\"success\",\"result\":\"true\"}";    
        }else {
            //购买失败
            return "{\"code\":\"500\",\"message\":\"failed\",\"result\":\"false\"}";
        }
    }
    
}
