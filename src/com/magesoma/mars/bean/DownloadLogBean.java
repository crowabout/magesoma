package com.magesoma.mars.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by u on 2016/7/17.
 *
 * 下载日志表
 *
 *
 */
@Entity
@Table(name="dlog")
public class DownloadLogBean extends BaseBean{

    @Id
    @Column(name="ID")
    @GenericGenerator(name="increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private long Id;
    @Column(name="beginTime")
    private Date beginTime;
    @Column(name="endTime")
    private Date endTime;
    @Column(name="pageNum")
    private int pageNum;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
