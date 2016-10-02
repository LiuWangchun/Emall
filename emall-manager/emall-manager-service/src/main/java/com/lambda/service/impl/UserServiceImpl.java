package com.lambda.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.mapper.PersonMapper;
import com.lambda.pojo.Person;
import com.lambda.pojo.PersonExample;
import com.lambda.pojo.PersonExample.Criteria;
import com.lambda.service.UserService;

/**
 * 用户管理Service
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PersonMapper userMapper;
    
    @Override
    public Person getUserByName(String UserName) {

       PersonExample example = new PersonExample();
       //添加查询条件
       Criteria criteria = example.createCriteria();
       criteria.andUsernameEqualTo(UserName);
       List<Person> list = userMapper.selectByExample(example);
       if(list != null && list.size() > 0){
           Person user = list.get(0);
           return user;
       }
        
        return null;
    }
    

}