package com.pdp.jingle.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pdp.jingle.models.Artist;

@WebServlet("/ArtistServlet")
public class ArtistServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public ArtistServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Artist> artistList = dbUtil.getArtists();
			String artistsJsonString = new Gson().toJson(artistList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(artistsJsonString);
			out.flush();
		} catch (Exception exec) {
			throw new ServletException(exec);
		}
	}

}
