package com.edu.lms.db.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.faces.context.FacesContext;

import com.edu.lms.BookBean;
import com.edu.lms.BookMngtBean;
import com.edu.lms.ReaderBean;

public class DatabaseOperation {
	public static Statement stmtObj;
	public static Connection connObj;
	public static ResultSet resultSetObj;
	public static PreparedStatement pstmt;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String db_url = "jdbc:mysql://localhost:3306/libraries", 
					db_userName = "root", 
					db_password = "123456789";
			connObj = DriverManager.getConnection(db_url, db_userName, db_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connObj;
	}

	// BOOKS
	public static ArrayList getBooksListFromDB() {
		ArrayList booksList = new ArrayList();
		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM book_record");
			while (resultSetObj.next()) {
				BookBean bookObj = new BookBean();
				bookObj.setBookId(resultSetObj.getInt("book_id"));
				bookObj.setBookName(resultSetObj.getString("book_name"));
				bookObj.setAuthor(resultSetObj.getString("author"));
				bookObj.setMajor(resultSetObj.getString("major"));
				bookObj.setYear(resultSetObj.getInt("year"));
				bookObj.setQuantity(resultSetObj.getInt("quantity"));

				booksList.add(bookObj);
			}
			System.out.println("Total Records Fetched: " + booksList.size());
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return booksList;
	}

	public static String saveBookDetailsInDB(BookBean newBookObj) {
		int saveResult = 0;
		String navigationResult = "";
		try {
			pstmt = getConnection().prepareStatement(
					"INSERT INTO book_record (book_name, author, major, year, quantity) values (?, ?, ?, ?, ?)");
			pstmt.setString(1, newBookObj.getBookName());
			pstmt.setString(2, newBookObj.getAuthor());
			pstmt.setString(3, newBookObj.getMajor());
			pstmt.setInt(4, newBookObj.getYear());
			pstmt.setInt(5, newBookObj.getQuantity());
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		if (saveResult != 0) {
			navigationResult = "booksList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createBook.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}

	public static String editBookRecordInDB(int bookId) {
		BookBean editRecord = null;
		System.out.println("editBookRecordInDB() : Book Id: " + bookId);

		/* Setting The Particular Book Details In Session */
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM book_record WHERE book_id = " + bookId);
			if (resultSetObj != null) {
				resultSetObj.next();
				editRecord = new BookBean();
				editRecord.setBookId(resultSetObj.getInt("book_id"));
				editRecord.setBookName(resultSetObj.getString("book_name"));
				editRecord.setAuthor(resultSetObj.getString("author"));
				editRecord.setMajor(resultSetObj.getString("major"));
				editRecord.setYear(resultSetObj.getInt("year"));
				editRecord.setQuantity(resultSetObj.getInt("quantity"));

			}
			sessionMapObj.put("editRecordObj", editRecord);
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/editBook.xhtml?faces-redirect=true";
	}

	public static String updateBookDetailsInDB(BookBean updateBookObj) {
		try {
			pstmt = getConnection().prepareStatement(
					"UPDATE book_record SET book_name=?, author=?, major=?, year=?, quantity=? WHERE book_id=?");
			pstmt.setString(1, updateBookObj.getBookName());
			pstmt.setString(2, updateBookObj.getAuthor());
			pstmt.setString(3, updateBookObj.getMajor());
			pstmt.setInt(4, updateBookObj.getYear());
			pstmt.setInt(5, updateBookObj.getQuantity());
			pstmt.setInt(6, updateBookObj.getBookId());
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/booksList.xhtml?faces-redirect=true";
	}

	public static String deleteBookRecordInDB(int bookId) {
		System.out.println("deleteBookRecordInDB() : Book Id: " + bookId);
		try {
			pstmt = getConnection().prepareStatement("DELETE FROM book_record WHERE book_id = " + bookId);
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return "/booksList.xhtml?faces-redirect=true";
	}
	// READER:

	public static ArrayList getReadersListFromDB() {
		ArrayList readersList = new ArrayList();
		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM reader_record");
			while (resultSetObj.next()) {
				ReaderBean readerObj = new ReaderBean();
				readerObj.setReaderId(resultSetObj.getInt("reader_id"));
				readerObj.setReaderName(resultSetObj.getString("reader_name"));
				readerObj.setAddress(resultSetObj.getString("address"));
				readerObj.setPhone(resultSetObj.getString("phone"));

				readersList.add(readerObj);
			}
			System.out.println("Total Records Fetched: " + readersList.size());
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readersList;
	}

	public static String saveReaderDetailsInDB(ReaderBean newReaderObj) {
		int saveResult = 0;
		String navigationResult = "";
		try {
			pstmt = getConnection()
					.prepareStatement("INSERT INTO reader_record (reader_name, address, phone) values (?, ?, ?)");
			pstmt.setString(1, newReaderObj.getReaderName());
			pstmt.setString(2, newReaderObj.getAddress());
			pstmt.setString(3, newReaderObj.getPhone());
			saveResult = pstmt.executeUpdate();
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (saveResult != 0) {
			navigationResult = "readersList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createReader.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}

	public static String editReaderRecordInDB(int readerId) {
		ReaderBean editRecord = null;
		System.out.println("editReaderRecordInDB() : Reader Id: " + readerId);

		/* Setting The Particular Reader Details In Session */
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM reader_record WHERE reader_id = " + readerId);
			if (resultSetObj != null) {
				resultSetObj.next();
				editRecord = new ReaderBean();
				editRecord.setReaderId(resultSetObj.getInt("reader_id"));
				editRecord.setReaderName(resultSetObj.getString("reader_name"));
				editRecord.setAddress(resultSetObj.getString("address"));
				editRecord.setPhone(resultSetObj.getString("phone"));

			}
			sessionMapObj.put("editRecordObj", editRecord);
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/editReader.xhtml?faces-redirect=true";
	}

	public static String updateReaderDetailsInDB(ReaderBean updateReaderObj) {
		try {
			pstmt = getConnection()
					.prepareStatement("UPDATE reader_record SET reader_name=?, address=?, phone=? WHERE reader_id=?");
			pstmt.setString(1, updateReaderObj.getReaderName());
			pstmt.setString(2, updateReaderObj.getAddress());
			pstmt.setString(3, updateReaderObj.getPhone());
			pstmt.setInt(4, updateReaderObj.getReaderId());
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/readersList.xhtml?faces-redirect=true";
	}

	public static String deleteReaderRecordInDB(int readerId) {
		System.out.println("deleteReaderRecordInDB() : Reader Id: " + readerId);
		try {
			pstmt = getConnection().prepareStatement("DELETE FROM reader_record WHERE reader_id = " + readerId);
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/readersList.xhtml?faces-redirect=true";
	}
//Book Management
	
	public static ArrayList getBookMngtListFromDB() {
		ArrayList bookMngtList = new ArrayList();
		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM borrowedBook_record");
			while (resultSetObj.next()) {
				BookMngtBean bookMngtObj = new BookMngtBean();
				bookMngtObj.setReaderId(resultSetObj.getInt("reader_id"));
				bookMngtObj.setBookId(resultSetObj.getInt("book_id"));
				bookMngtObj.setBorrowDate(resultSetObj.getString("borrow_date"));
				bookMngtObj.setQuantity(resultSetObj.getInt("quantity"));
				bookMngtObj.setStatus(resultSetObj.getString("status"));

				bookMngtList.add(bookMngtObj);
			}
			System.out.println("Total Records Fetched: " + bookMngtList.size());
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return bookMngtList;
	}
	public static String saveBookMngtDetailsInDB(BookMngtBean newBookMngtObj) {
		int saveResult = 0;
		String navigationResult = "";
		try {
			pstmt = getConnection().prepareStatement(
					"INSERT INTO borrowedBook_record (reader_id, book_id, borrow_date, quantity, status ) values (?, ?, ?, ?, ?)");
			pstmt.setInt(1, newBookMngtObj.getReaderId());
			pstmt.setInt(2, newBookMngtObj.getBookId());
			pstmt.setString(3, newBookMngtObj.getBorrowDate());
			pstmt.setString(5, newBookMngtObj.getStatus());
			pstmt.setInt(4, newBookMngtObj.getQuantity());
			saveResult = pstmt.executeUpdate();
			
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		if (saveResult != 0) {
			navigationResult = "bookMngtList.xhtml?faces-redirect=true";
		} else {
			navigationResult = "createBookMngt.xhtml?faces-redirect=true";
		}
		return navigationResult;
	}
	
	public static String editBookMngtRecordInDB(int readerId, int book_id) {
		BookMngtBean editRecord = null;
		System.out.println("editBookMngtRecordInDB() : Reader Id: " + readerId + ", Book Id: " + book_id);

		/* Setting The Particular Reader Details In Session */
		Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM borrowedBook_record WHERE reader_id = " + readerId + " AND book_id = "+ book_id);
			if (resultSetObj != null) {
				resultSetObj.next();
				editRecord = new BookMngtBean();
				editRecord.setReaderId(resultSetObj.getInt("reader_id"));
				editRecord.setBookId(resultSetObj.getInt("book_id"));
				editRecord.setQuantity(resultSetObj.getInt("quantity"));
				editRecord.setStatus(resultSetObj.getString("status"));
				editRecord.setBorrowDate(resultSetObj.getString("borrow_date"));

			}
			sessionMapObj.put("editRecordObj", editRecord);
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/editBookMngt.xhtml?faces-redirect=true";
	}
	
	public static String updateBookMngtDetailsInDB(BookMngtBean updateBookMngtObj) {
		try {
			pstmt = getConnection()
					.prepareStatement("UPDATE borrowedBook_record SET quantity=?, status=?, borrow_date=? WHERE reader_id= ? AND book_id=? ");
			pstmt.setInt(1, updateBookMngtObj.getQuantity());
			pstmt.setString(2, updateBookMngtObj.getStatus());
			pstmt.setString(3, updateBookMngtObj.getBorrowDate());
			pstmt.setInt(4, updateBookMngtObj.getReaderId());
			pstmt.setInt(5, updateBookMngtObj.getBookId());
			
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/bookMngtList.xhtml?faces-redirect=true";
	}
	
	public static String deleteBookMngtRecordInDB(int readerId, int bookId) {
		System.out.println("deleteBookMngtRecordInDB() : Reader Id: " + readerId + " and Book Id: "+ bookId);
		try {
			pstmt = getConnection().prepareStatement("DELETE FROM borrowedBook_record WHERE reader_id = " + readerId + " AND book_id = " + bookId);
			pstmt.executeUpdate();
			connObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/bookMngtList.xhtml?faces-redirect=true";
	}
	
	//search by readerId
	public static ArrayList getBookMngtListByReaderIdFromDB(int readerId) {
		ArrayList bookMngtListByReaderId = new ArrayList();
		try {
			stmtObj = getConnection().createStatement();
			resultSetObj = stmtObj.executeQuery("SELECT * FROM borrowedBook_record WHERE reader_id = " + readerId);
			while (resultSetObj.next()) {
				BookMngtBean bookMngtObj = new BookMngtBean();
				bookMngtObj.setBookId(resultSetObj.getInt("book_id"));
				bookMngtObj.setQuantity(resultSetObj.getInt("quantity"));
				bookMngtObj.setStatus(resultSetObj.getString("status"));
				bookMngtObj.setBorrowDate(resultSetObj.getString("borrow_date"));
				bookMngtListByReaderId.add(bookMngtObj);
			}
			System.out.println("Total Records Fetched: " + bookMngtListByReaderId.size());
			connObj.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		return bookMngtListByReaderId;
	}
	
	
	
	
	
	
	
	
	
	
}
