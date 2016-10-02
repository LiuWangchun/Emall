package com.lambda.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.mapper.TrxMapper;
import com.lambda.pojo.Trx;
import com.lambda.pojo.TrxExample;
import com.lambda.pojo.TrxExample.Criteria;
import com.lambda.service.LogService;

/**
 * 记录管理Service
 */

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private TrxMapper logMapper;
    
    @Override
    public Trx getLogByPersonId(Integer personId) {

       TrxExample example = new TrxExample();
       //添加查询条件
       Criteria criteria = example.createCriteria();
       criteria.andPersonidEqualTo(personId);
       List<Trx> list = logMapper.selectByExample(example);
       if(list != null && list.size() > 0){
           Trx log = list.get(0);
           return log;
       }
        
        return null;
    }

    @Override
    public Trx getLogByContentId(Integer contentId) {

        TrxExample example = new TrxExample();
        //添加查询条件
        Criteria criteria = example.createCriteria();
        criteria.andContentidEqualTo(contentId);
        List<Trx> list = logMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            Trx log = list.get(0);
            return log;
     }
             
        return null;
    }

    @Override
    public Boolean addLog(Trx log) {
        
        Long time = System.currentTimeMillis();
        
        try {
            //插入到数据库
            log.setBuytime(time);//时间戳
            logMapper.insert(log);
            return true;
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
