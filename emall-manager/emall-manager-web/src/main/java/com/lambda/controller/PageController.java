package com.lambda.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lambda.pojo.Content;
import com.lambda.pojo.Person;
import com.lambda.pojo.Trx;
import com.lambda.service.ItemService;
import com.lambda.service.LogService;

/**
 * 页面跳转controller
 */

@Controller
public class PageController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private LogService logService;

    /**
     * 1.登录页
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpSession httpSession, Person user, ModelMap map){
        //用户信息
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            map.put("user", user);
        }
        return "login";
    }
    
    /**
     * 2.退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }
    
    /**
     * 3.展示页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpSession httpSession, Person user, Trx log, ModelMap map){
        
        //用户信息
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            map.put("user", user);
        }
        
        //输出商品列表
        Integer contentId = 1;
        Content tbItem = itemService.getItemById(contentId);
        List<Object> list = new ArrayList<Object>();
        while (tbItem != null) {
            
            if (!tbItem.getHide()){//判断是否被删除
                
                log = logService.getLogByContentId(contentId);
                if (log != null && user != null && log.getPersonid().equals(user.getId())) {
                    tbItem.setIsBuy(true);
                }
                if (log != null){
                    tbItem.setIsSell(true);
                }
                
                list.add(tbItem);
            }
            
            contentId ++;
            tbItem = itemService.getItemById(contentId);
        }
        map.put("productList",list);
        
        return "index";
    }
    
    /**
     * 4.查看页
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(HttpSession httpSession, @RequestParam("id") int showId, Person user, ModelMap map){
        
        //用户信息
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            map.put("user", user);
        }
        
        //商品信息
        Content tbItem = itemService.getItemById(showId);
        
        if (!tbItem.getHide() && tbItem != null){
        
            //历史信息查询
            Trx log = logService.getLogByContentId(tbItem.getId());
            
            if (log != null && user != null && log.getPersonid().equals(user.getId())){
                tbItem.setBuyPrice(log.getBuyprice());
                tbItem.setIsBuy(true);
            }
            if (log != null){
                tbItem.setIsSell(true);
            }
            
            map.put("product",tbItem);
        }
        
        return "show";
    }
    
    /**
     * 5.账务页
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(HttpSession httpSession, Person user, Trx log, ModelMap map){
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            //非买家重新登录
            if (user.getUsertype() != 0){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }
        
        //用户信息
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            map.put("user", user);
        }
        
        //输出商品列表
        Integer contentId = 1;
        Content tbItem = itemService.getItemById(contentId);
        List<Object> list = new ArrayList<Object>();
        while (tbItem != null) {
            
            log = logService.getLogByContentId(contentId);
            if (log != null && user != null && log.getPersonid().equals(user.getId())) {
                tbItem.setBuyPrice(log.getBuyprice());
                tbItem.setBuyTime(log.getBuytime());
                
                list.add(tbItem);
            }
            
            contentId ++;
            tbItem = itemService.getItemById(contentId);
        }
        map.put("buyList",list);
        return "account";
    }
    
}
