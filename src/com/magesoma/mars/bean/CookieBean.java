package com.magesoma.mars.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by u on 2016/7/16.
 *
 * cookies 表
 */
@Entity
@Table(name="cookie")
public class CookieBean extends BaseBean{

    @Id
    @Column(name="ID")
    @GenericGenerator(name="increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private long ID;
    @Column(name="cookieName")
    private String cookieName;
    @Column(name="content")
    private String content;
    @Column(name="domain")
    private String domain;
    @Column(name="size")
    private int size;
    @Column(name="path")
    private String path;
    @Column(name="expiredate")
    private Date expire;
    @Column(name="isOnlyHttp")
    private boolean isOnlyHttp;
    @Column(name="security")
    private boolean security;

    public long getId() {
        return ID;
    }

    public void setId(long id) {
        this.ID = id;
    }

    public String getName() {
        return cookieName;
    }

    public void setName(String name) {
        this.cookieName = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public boolean isOnlyHttp() {
        return isOnlyHttp;
    }

    public void setOnlyHttp(boolean isOnlyHttp) {
        this.isOnlyHttp = isOnlyHttp;
    }

    public boolean getSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "ID=" + ID +
                ", cookieName='" + cookieName + '\'' +
                ", content='" + content + '\'' +
                ", domain='" + domain + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", expire=" + expire +
                ", isOnlyHttp=" + isOnlyHttp +
                ", security='" + security + '\'' +
                '}';
    }
}
