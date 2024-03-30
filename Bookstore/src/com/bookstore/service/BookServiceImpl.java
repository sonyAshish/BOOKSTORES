package com.bookstore.service;

import java.sql.SQLException;
import java.util.List;

import com.bookstore.dao.BookDao;
import com.bookstore.dao.BookDaoImpl;
import com.bookstore.model.Book;

public class BookServiceImpl implements BookService {

    String jdbcURL;
    String jdbcUsername;
    String jdbcPassword;

    public BookServiceImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {

        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void insertBook(Book book) throws SQLException {
        System.out.println("BookServiceImpl:" + book.getTitle() + " " + book.getAuthor() + " " + book.getTitle());
        bookDao.insertBook(book);
    }

    @Override
    public void deleteBook(Book book) throws SQLException {
        System.out.println("ServiceImpl deleteBook method: " + book.getId() + "" + book.getAuthor() + "" + book.getTitle() + "" + book.getPrice());
        bookDao.deleteBook(book);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        System.out.println("ServiceImpl updateBook method: " + book.getId() + "" + book.getAuthor() + "" + book.getTitle() + "" + book.getPrice());
        bookDao.updateBook(book);
    }

    @Override
    public List<Book> listBook() throws SQLException {
        System.out.println("ServiceImpl listBook method");
        return bookDao.listBook();
    }

	@Override
	public Book getBook(int id)  throws SQLException{
		 System.out.println("ServiceImpl listBook method");
	        return bookDao.getBook( id);
	}
}
