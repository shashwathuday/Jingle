package com.pdp.jingle.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.pdp.jingle.models.Song;

@WebServlet("/SongServlet")
public class SongServlet extends BaseServlet {

	private static final long serialVersionUID = -9044881493952948918L;

	public SongServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String requestType = request.getParameter("requestType");
			String searchQuery = request.getParameter("searchQuery");
			String userId = request.getParameter("userId");
			List<Song> songList = new ArrayList<>();

			if (requestType != null) {
				switch (requestType) {
				case "trendingSongs": {
					songList = dbUtil.getTrendingSongs();
					break;
				}
				case "recentlyPlayed": {
					songList = dbUtil.getRecentlyPlayedSongs(userId);
					break;
				}
				case "recommended": {
					songList = getRecommendedSongs(userId);
					break;
				}
				case "albums": {
					songList = dbUtil.getAllSongs();
					break;
				}
				case "playlist": {
					songList = dbUtil.getPlaylist(userId);
					break;
				}
				}
			}

			if (searchQuery != null) {
				songList = dbUtil.searchSong(searchQuery);
			}

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

	private List<Song> getRecommendedSongs(String userId) throws Exception {
		List<Song> songList = new ArrayList<>();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(String.format("http://localhost:9080/songs?userId=%s", userId)))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		songList = dbUtil.getSongsFromList(Arrays.asList(response.body().split(",")));
		return songList;
	}
}
