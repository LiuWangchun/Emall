package com.lambda.mapper;

import com.lambda.pojo.Trx;
import com.lambda.pojo.TrxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrxMapper {
    int countByExample(TrxExample example);

    int deleteByExample(TrxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Trx record);

    int insertSelective(Trx record);

    List<Trx> selectByExample(TrxExample example);

    Trx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Trx record, @Param("example") TrxExample example);

    int updateByExample(@Param("record") Trx record, @Param("example") TrxExample example);

    int updateByPrimaryKeySelective(Trx record);

    int updateByPrimaryKey(Trx record);
}