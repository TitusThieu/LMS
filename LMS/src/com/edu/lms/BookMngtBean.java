package com.edu.lms;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.edu.lms.db.operations.DatabaseOperation;


@ManagedBean @RequestScoped
public class BookMngtBean {
	private int bookId;
	private int readerId;
	private int quantity;
	private String status; 
	private String borrowDate;
	
	public ArrayList bookMngtListFromDB;
	public ArrayList booksListFromDB;
	public ArrayList readersListFromDB;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	public ArrayList getBookMngtListFromDB() {
		return bookMngtListFromDB;
	}
	public void setBookMngtListFromDB(ArrayList bookMngtListFromDB) {
		this.bookMngtListFromDB = bookMngtListFromDB;
	}
	
	public ArrayList getBooksListFromDB() {
		return booksListFromDB;
	}

	public void setBooksListFromDB(ArrayList booksListFromDB) {
		this.booksListFromDB = booksListFromDB;
	}

	public ArrayList getReadersListFromDB() {
		return readersListFromDB;
	}

	public void setReadersListFromDB(ArrayList readersListFromDB) {
		this.readersListFromDB = readersListFromDB;
	}

	

	@PostConstruct
	public void init() {
		bookMngtListFromDB = DatabaseOperation.getBookMngtListFromDB();
		booksListFromDB = DatabaseOperation.getBooksListFromDB();
		readersListFromDB = DatabaseOperation.getReadersListFromDB();
	}

	public ArrayList bookMngtList() {
		return bookMngtListFromDB;
	}
	public String saveBookMngtDetails(BookMngtBean newBookMngtObj) {
		return DatabaseOperation.saveBookMngtDetailsInDB(newBookMngtObj);
	}
	public String editBookMngtRecord(int readerId, int bookId) {
		return DatabaseOperation.editBookMngtRecordInDB(readerId, bookId);
	}
	
	public String updateBookMngtDetails(BookMngtBean updateBookMngtObj) {
		return DatabaseOperation.updateBookMngtDetailsInDB(updateBookMngtObj);
	}
	
	public String deleteBookMngtRecord(int readerId, int bookId) {
		return DatabaseOperation.deleteBookMngtRecordInDB(readerId, bookId);
	}
	public ArrayList bookMngtListByReaderId() {
		return DatabaseOperation.getBookMngtListByReaderIdFromDB(readerId);
	}
	
//custom validation method
	public void validateQuantity(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if ((Integer)value > 3) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Quantity must be less than 4");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void validateQuantitySum(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (DatabaseOperation.getBookMngtListByReaderIdFromDB(readerId).size() > 5) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Quantity of all books borrowed must be less than 6");
			context.addMessage(component.getClientId(context), message);
		}
	}
	

	
	
	
	
	
	
	
	
}
