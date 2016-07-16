package com.magesoma.mars.bean;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by u on 2016/7/16.
 */
@Entity
@Table(name="tb_books")
public class Books extends BaseBean{
   public Books(){
      super();
   }
   @Id
   private  String ISBN;
   @Column(name="bookName")
   private  String bookName;
   @Column(name="publishing")
   private  String publishing;
   @Column(name="writer")
   private  String writer;
   @Column(name="price")
   private  float price;
   @Column(name="date")
   private Date date;

   public String getISBN() {
      return ISBN;
   }

   public void setISBN(String ISBN) {
      this.ISBN = ISBN;
   }

   public String getBookName() {
      return bookName;
   }

   public void setBookName(String bookName) {
      this.bookName = bookName;
   }

   public String getPublishing() {
      return publishing;
   }

   public void setPublishing(String publishing) {
      this.publishing = publishing;
   }

   public String getWriter() {
      return writer;
   }

   public void setWriter(String writer) {
      this.writer = writer;
   }

   public float getPrice() {
      return price;
   }

   public void setPrice(float price) {
      this.price = price;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   @Override
   public String toString() {
      return "Books{" +
              "ISBN='" + ISBN + '\'' +
              ", bookName='" + bookName + '\'' +
              ", publishing='" + publishing + '\'' +
              ", writer='" + writer + '\'' +
              ", price=" + price +
              ", date=" + date +
              '}';
   }
}

