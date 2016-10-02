package com.lambda.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.mapper.ContentMapper;
import com.lambda.pojo.Content;
import com.lambda.pojo.ContentExample;
import com.lambda.pojo.ContentExample.Criteria;
import com.lambda.service.ItemService;

/**
 * 商品管理Service
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ContentMapper itemMapper;
    
    @Override
    public Content getItemById(Integer itemId) {

       ContentExample example = new ContentExample();
       //添加查询条件
       Criteria criteria = example.createCriteria();
       criteria.andIdEqualTo(itemId);
       List<Content> list = itemMapper.selectByExample(example);
       if(list != null && list.size() > 0){
           Content item = list.get(0);
           return item;
       }
        
        return null;
    }

    @Override
    public Content addItem(Content item) {
        
        Long time = System.currentTimeMillis();
        
        try {
            //插入到数据库
            item.setTime(time);
            item.setHide(false);
            itemMapper.insert(item);
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
        //返回对象
        ContentExample example = new ContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andTimeEqualTo(time);
        List<Content> list = itemMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            Content itemTrue = list.get(0);
            return itemTrue;
        }
        return null;
    }

    @Override
    public Content updateItem(Content item) {
        
        Long time = System.currentTimeMillis();
        
        try {
            //插入到数据库
            item.setTime(time);
            item.setHide(false);
            itemMapper.updateByPrimaryKey(item);
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
        //返回对象
        ContentExample example = new ContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andTimeEqualTo(time);
        List<Content> list = itemMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            Content itemTrue = list.get(0);
            return itemTrue;
        }
        return null;
    }
    
    @Override
    public Boolean deleteItemById(Integer id) {
        
        try {
            ContentExample example = new ContentExample();
            Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            List<Content> list = itemMapper.selectByExample(example);
            if(list != null && list.size() > 0){
                Content item = list.get(0);
                //隐藏值设为true
                item.setHide(true);
                itemMapper.updateByPrimaryKey(item);
                return true;
            }else {
                return false;
            }
            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    

}
