package com.pdp.jingle.servlets;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import com.pdp.jingle.utils.DbUtil;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 6876760343276491852L;
	DbUtil dbUtil;

	// Tomcat will inject the connection pool object to the dataSource Variable
	@Resource(name = "jdbc/jingle")
	private DataSource dataSource;

	/**
	 * @overriding the init() method to create an instance of the db
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();

		try {
			dbUtil = new DbUtil(dataSource);
		}

		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

}
