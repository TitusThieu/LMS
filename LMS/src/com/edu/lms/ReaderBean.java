package com.edu.lms;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.edu.lms.db.operations.DatabaseOperation;

@ManagedBean @RequestScoped
public class ReaderBean {
	private int readerId;
	private String readerName;
	private String address;
	private String phone;
	
	public ArrayList readersListFromDB;
	
	public int getReaderId() {
		return readerId;
	}
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@PostConstruct
	public void init() {
		readersListFromDB = DatabaseOperation.getReadersListFromDB();
	}

	public ArrayList readersList() {
		return readersListFromDB;
	}
	public String saveReaderDetails(ReaderBean newReaderObj) {
		return DatabaseOperation.saveReaderDetailsInDB(newReaderObj);
	}
	
	public String editReaderRecord(int readerId) {
		return DatabaseOperation.editReaderRecordInDB(readerId);
	}
	
	public String updateReaderDetails(ReaderBean updateReaderObj) {
		return DatabaseOperation.updateReaderDetailsInDB(updateReaderObj);
	}
	
	public String deleteReaderRecord(int readerId) {
		return DatabaseOperation.deleteReaderRecordInDB(readerId);
	}
}
