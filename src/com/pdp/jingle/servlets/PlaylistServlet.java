package com.pdp.jingle.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pdp.jingle.models.Song;

/**
 * Servlet implementation class PlaylistServlet
 */
@WebServlet("/PlaylistServlet")
public class PlaylistServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlaylistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String userId = request.getParameter("userId");
			List<Song> songList = new ArrayList<>();
			songList = dbUtil.getPlaylist(userId);
			String songsJsonString = new Gson().toJson(songList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(songsJsonString);
			out.flush();
		} catch (Exception exec) {
			throw new ServletException(exec);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String userId = request.getParameter("userId");
			String songId = request.getParameter("songId");
			dbUtil.addSongToPlaylist(userId, songId);
			String songsJsonString = new Gson().toJson(new JsonObject());
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(songsJsonString);
			out.flush();
		} catch (Exception exec) {
			throw new ServletException(exec);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String userId = request.getParameter("userId");
			String songId = request.getParameter("songId");
			dbUtil.deleteSongFromPlaylist(userId, songId);
			String songsJsonString = new Gson().toJson(new JsonObject());
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			out.print(songsJsonString);
			out.flush();
		} catch (Exception exec) {
			throw new ServletException(exec);
		}
	}

}
