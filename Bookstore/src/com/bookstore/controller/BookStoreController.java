package com.bookstore.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookstore.dao.BookDaoImpl;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import com.bookstore.service.BookServiceImpl;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 * 
 * @author www.codejava.net
 */
public class BookStoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDaoImpl bookDAO;
	private BookService bookservice;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		bookDAO = new BookDaoImpl();
		bookservice = new BookServiceImpl(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertBook(request, response);
				break;
			case "/delete":
				deleteBook(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBook(request, response);
				break;
			default:
				listBook(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listBook(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		//List<Book> listBook = bookDAO.listAllBooks();
		List<Book> listBook = bookservice.listBook();


		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		//Book existingBook = bookDAO.getBook(id);
		Book existingBook = bookservice.getBook(id);


		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		request.setAttribute("book", existingBook);
		dispatcher.forward(request, response);

	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));

		Book book = new Book(title, author, price);
		bookservice.insertBook(book);
		// bookDAO.insertBook(book);
		response.sendRedirect("list");
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		float price = Float.parseFloat(request.getParameter("price"));

		Book book = new Book(id, title, author, price);
		bookservice.updateBook(book);
		//bookDAO.updateBook(book);
		response.sendRedirect("list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		Book book = new Book(id);
		//book.setId(id);
		//book.setAuthor(request.getParameter("author"));
		//book.setTitle(request.getParameter("title"));
		//book.setPrice(Float.parseFloat(request.getParameter("price")));
		//bookDAO.deleteBook(book);
		
		bookservice.deleteBook(book);
		response.sendRedirect("list");

	}
	

}
