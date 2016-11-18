package com.magesoma.mars.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by u on 2016/7/16.
 */
@Entity
@Table(name = "readerInfo")
public class ReaderInfoBean extends BaseBean {

    public ReaderInfoBean() {
        super();
    }

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private int Id;
    @Column(name = "cardNum")
    private String cardNum;
    @Column(name = "codeNum")
    private String codeNum;


    @Column(name = "bookcode")
    private String bookCode;

    @Column(name = "bookName")
    private String bookName;
    @Column(name = "borrowTime")
    private Date borrowTime;
    @Column(name = "backTime")
    private Date backTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    @Override
    public String toString() {
        return "ReaderInfo{" +
                "Id=" + Id +
                ", cardNum='" + cardNum + '\'' +
                ", codeNum='" + codeNum + '\'' +
                ", bookName='" + bookName + '\'' +
                ", borrowTime=" + borrowTime +
                ", backTime=" + backTime +
                '}';
    }
}
