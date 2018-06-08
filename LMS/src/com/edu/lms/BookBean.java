package com.edu.lms;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.edu.lms.db.operations.DatabaseOperation;

@ManagedBean @RequestScoped
public class BookBean {
	private int bookId;
	private String bookName;
	private String author;
	private String major;
	private int year;
	private int quantity;
	
	public ArrayList booksListFromDB;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String string) {
		this.bookName = string;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@PostConstruct
	public void init() {
		booksListFromDB = DatabaseOperation.getBooksListFromDB();
	}

	public ArrayList booksList() {
		return booksListFromDB;
	}
	public String saveBookDetails(BookBean newBookObj) {
		return DatabaseOperation.saveBookDetailsInDB(newBookObj);
	}
	
	public String editBookRecord(int bookId) {
		return DatabaseOperation.editBookRecordInDB(bookId);
	}
	
	public String updateBookDetails(BookBean updateBookObj) {
		return DatabaseOperation.updateBookDetailsInDB(updateBookObj);
	}
	
	public String deleteBookRecord(int bookId) {
		return DatabaseOperation.deleteBookRecordInDB(bookId);
	}
}
