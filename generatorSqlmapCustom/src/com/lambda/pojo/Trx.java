package com.lambda.pojo;

import java.math.BigDecimal;

public class Trx {
    private Integer id;

    private Integer contentid;

    private Integer personid;

    private BigDecimal buyprice;

    private Long buytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContentid() {
        return contentid;
    }

    public void setContentid(Integer contentid) {
        this.contentid = contentid;
    }

    public Integer getPersonid() {
        return personid;
    }

    public void setPersonid(Integer personid) {
        this.personid = personid;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public Long getBuytime() {
        return buytime;
    }

    public void setBuytime(Long buytime) {
        this.buytime = buytime;
    }
}