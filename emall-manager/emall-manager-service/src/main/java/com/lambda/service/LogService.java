package com.lambda.service;

import com.lambda.pojo.Trx;

public interface LogService {

    Trx getLogByPersonId(Integer personId);
    
    Trx getLogByContentId(Integer contentId);
    
    Boolean addLog(Trx log);
    
}
