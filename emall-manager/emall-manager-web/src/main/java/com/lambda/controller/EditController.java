package com.lambda.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lambda.pojo.Content;
import com.lambda.pojo.Person;
import com.lambda.service.ItemService;

/**
 * 页面跳转(卖家)controller
 */

@Controller
public class EditController {
    
    @Autowired
    private ItemService itemService;
    
    /**
     *  6.发布页
     */
    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String publicShow(HttpSession httpSession, Person user, ModelMap map){
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            //非卖家重新登录
            if (user.getUsertype() != 1){
                return "redirect:/login";
            }
            map.put("user", user);
        }else {
            return "redirect:/login";
        }
        
        return "public";
    }
    
    /**
     *  7.发布提交
     */
    @RequestMapping(value = "/publicSubmit", method = RequestMethod.POST)
    public String publicSubmit(HttpSession httpSession, @RequestParam("title") String title, 
            @RequestParam("image") String image, @RequestParam("detail") String detail, 
            @RequestParam("price") BigDecimal price, @RequestParam("summary") String summary, 
            Person user, Content item, ModelMap map){
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            //非卖家重新登录
            if (user.getUsertype() != 1){
                return "redirect:/login";
            }
            map.put("user", user);
        }else {
            return "redirect:/login";
        }

        //设置商品
        item.setTitle(title);
        item.setImage(image);
        item.setDetail(detail);
        item.setPrice(price);
        item.setSummary(summary);
        
        
        //上传至数据库
        Content tbItem = itemService.addItem(item);
        if (tbItem != null){
            map.put("product",tbItem);
        }
            
        return "publicSubmit";
    }
    
    /**
     *  8.编辑页
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpSession httpSession, @RequestParam("id") int showId, Person user, ModelMap map){
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            //非卖家重新登录
            if (user.getUsertype() != 1){
                return "redirect:/login";
            }
            map.put("user", user);
        }else {
            return "redirect:/login";
        }
        
        //商品信息
        Content tbItem = itemService.getItemById(showId);
        if (!tbItem.getHide() && tbItem != null){
            map.put("product",tbItem);
        }
        
        return "edit";
    }
    
    /**
     *  9.编辑提交
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public String editSubmit(HttpSession httpSession, @RequestParam("title") String title, 
            @RequestParam("image") String image, @RequestParam("detail") String detail, 
            @RequestParam("price") BigDecimal price, @RequestParam("summary") String summary, 
            Person user, Content item, ModelMap map){
        
        //用户验证
        if (httpSession.getAttribute("manager") != null){
            user = (Person) httpSession.getAttribute("manager"); 
            //非卖家重新登录
            if (user.getUsertype() != 1){
                return "redirect:/login";
            }
            map.put("user", user);
        }else {
            return "redirect:/login";
        }
        
        //设置商品
        item.setTitle(title);
        item.setImage(image);
        item.setDetail(detail);
        item.setPrice(price);
        item.setSummary(summary);
        
        //上传至数据库
        Content tbItem = itemService.updateItem(item);
        if (tbItem != null){
            map.put("product",tbItem);
        }
        
        return "editSubmit";
    }
    

}
