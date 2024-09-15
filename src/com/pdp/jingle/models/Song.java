package com.pdp.jingle.models;

import java.util.List;

public class Song {

	String id;
	String title;
	String genre;
	String duration;
	String location;
	String albumCover;
	String albumTitle;
	List<String> artists;

	public Song(String id, String title, String genre, String duration, String location, String albumTitle,
			String albumCover, List<String> artists) {
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.duration = duration;
		this.location = location;
		this.albumTitle = albumTitle;
		this.albumCover = albumCover;
		this.artists = artists;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public String getDuration() {
		return duration;
	}

	public String getLocation() {
		return location;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getAlbumCover() {
		return albumCover;
	}

	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	public List<String> getArtists() {
		return artists;
	}

	public void setArtists(List<String> artists) {
		this.artists = artists;
	}

}
