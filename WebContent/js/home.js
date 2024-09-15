$(document).ready(function() {
	initializeAudioPlayer();
	getArtists();
	userId = document.getElementById("user-id").innerText;
	getSongs("trending-list", "trendingSongs", userId);
	getSongs("recent-list", "recentlyPlayed", userId, doOtherOpreation = true);
	getSongs("recommended-list", "recommended", userId);
	getSongs("album-list", "albums", userId);

	$("#song-search-bar").keyup(function(event) {
		if (event.target.value.length == 0) {
			$("#search-results").hide();
		}
		if (event.keyCode === 13 && event.target.value.length) {
			$("#search-results").show();
			searchSong(event.target.value);
		}
	});
});

var userId;
var audioSources;
var currentTrackIndex = 0;
var audioPlayer;
var isPlaying;
var isLooped;
const playThresold = 5;
var isPlaythresholdCrossed = false;
var playList = [];


/** AJAX calls */

function getArtists() {
	$.ajax({
		url: "/Jingle/ArtistServlet",
		method: "GET",
		dataType: "json",
		success: function(data) {
			let artistList = document.getElementById("artist-list");
			for (let i = 0; i < data.length; i++) {
				artistList.innerHTML += `<div class="artist-info">
							<img class="artist-picture" src="${data[i].location}" />
							<div class="artist-name">${data[i].firstName} ${data[i].lastName}</div>
						</div>`;
			}
		},
		error: function(error) {
			console.error("Error fetching artists:", error);
		},
	});
}

function getSongs(divId, requestType, userId = null, doOtherOperations = false) {
	$.ajax({
		url: `/Jingle/SongServlet?requestType=${requestType}&userId=${userId}`,
		method: "GET",
		dataType: "json",
		success: function(data) {
			let divElement = document.getElementById(divId);
			if (requestType == "albums") {
				let albums = Object.groupBy(
					data,
					({ albumCover }) => albumCover
				);
				let albumElement = "";
				for (let album in albums) {
					albumElement += `<div class="album">
									<img class="album-cover" src="${album}"/>
									<div class="album-songs">`;
					for (let i = 0; i < albums[album].length; i++) {
						albumElement += `<div class="song" onclick='playSong(${JSON.stringify(
							albums[album]
						)}, ${i})'>
										<p class="song-number">${i + 1}</p>
										<div class="song-details">
											<p class="song-title">${albums[album][i].title}</p>
											<div class="song-additional-info">
											<p class="song-artist">${albums[album][i].artists.toString()}</p>
											<div class="seperator"></div>
											<p class="song-duration">${albums[album][i].duration
								.toString()
								.replace(".", ":")}</p>
											</div>
										</div>
									</div>`;
					}
					albumElement += `</div></div>`;
				}
				divElement.innerHTML = albumElement;
			} else {
				if (requestType == "recentlyPlayed") {
					$(`#${divId}`).empty();
					if (doOtherOperations) {
						audioSources = data;
						getPlayList(userId);
						loadTrack();
					}
				}
				for (let i = 0; i < data.length; i++) {
					let songElement = `<div class="song-info" onclick='playSong(${JSON.stringify(
						data
					)}, ${i})'>
							<img class="song-cover" src="${data[i].albumCover}" />
							<div class="song-name">${data[i].title}</div>
						</div>`;
					divElement.innerHTML += songElement;
				}
			}
		},
		error: function(error) {
			console.error("Error fetching song:", error);
		},
	});
}

function getPlayList(userId) {
	$.ajax({
		url: `/Jingle/PlaylistServlet?userId=${userId}`,
		method: "GET",
		dataType: "json",
		success: function(data) {
			playList = data;
			// update the display after getting the playlist
			updateSongDisplay();
			$("#play-list").empty();
			let divElement = document.getElementById("play-list");
			for (let i = 0; i < data.length; i++) {
				let songElement = `<div class="song-info" onclick='playSong(${JSON.stringify(
					data
				)}, ${i})'>
							<img class="song-cover" src="${data[i].albumCover}" />
							<div class="song-name">${data[i].title}</div>
						</div>`;
				divElement.innerHTML += songElement;
			}
		},
		error: function(error) {
			console.error("Error fetching song:", error);
		},
	});
}

function addSongToPlaylist(userId, songId) {
	$.ajax({
		url: `/Jingle/PlaylistServlet?userId=${userId}&songId=${songId}`,
		method: "POST",
		dataType: "json",
		success: function() {
			updatePlaylistButton(true);
			getPlayList(userId);
		},
		error: function(error) {
			console.error("Error fetching song:", error);
		},
	});
}

function deleteSongFromPlaylist(userId, songId) {
	$.ajax({
		url: `/Jingle/PlaylistServlet?userId=${userId}&songId=${songId}`,
		method: "DELETE",
		dataType: "json",
		success: function() {
			updatePlaylistButton(false);
			getPlayList(userId);
		},
		error: function(error) {
			console.error("Error fetching song:", error);
		},
	});
}

function searchSong(searchQuery) {
	$.ajax({
		url: `/Jingle/SongServlet?searchQuery=${searchQuery}`,
		method: "GET",
		dataType: "json",
		success: function(data) {
			if (!data.url) {
				$("#search-list").empty();
				let songsList = document.getElementById("search-list");
				for (let i = 0; i < data.length; i++) {
					songsList.innerHTML += `<div class="song-info" onclick='playSong(${JSON.stringify(
						data
					)}, ${i})'>
								<img class="song-cover" src="${data[i].albumCover}" />
								<div class="song-name">${data[i].title}</div>
							</div>`;
				}
			} else {
				window.location = contextPath + data.url;
			}
		},
		error: function(error) {
			console.error("Error searching song:", error);
		},
	});
}

function addPlayHistory(userId, songId) {
	$.ajax({
		url: `/Jingle/SongHistoryServlet?userId=${userId}&songId=${songId}`,
		method: "POST",
		dataType: "json",
		success: function() {
			getSongs("recent-list", "recentlyPlayed", userId);
		},
		error: function(error) {
			console.error("Error in adding play history:", error);
		},
	});
}


/** Utility methods */

function initializeAudioPlayer() {
	audioPlayer = document.getElementById("myAudio");
	isPlaying = false;
	isLooped = false;
	if (audioSources != null) {
		loadTrack();
	}
	audioPlayer.addEventListener("timeupdate", updateSongProgress);
	audioPlayer.addEventListener("timeupdate", checkPlayDuration);
	audioPlayer.addEventListener("ended", playNext);
}

function playSong(songList, position) {
	isPlaythresholdCrossed = false;
	audioSources = songList;
	currentTrackIndex = position;
	updateSongDisplay();
	loadTrack();
	audioPlayer.play();
	if (!isPlaying) {
		togglePlayPause();
	}
}

function updateSongDisplay() {
	let songCover = document.getElementById("song-cover");
	let songName = document.getElementById("song-name");
	let artistName = document.getElementById("song-artists");
	let songGenre = document.getElementById("song-genre");
	let songDuration = document.getElementById("song-duration");

	songCover.src = audioSources[currentTrackIndex].albumCover;
	songName.textContent = audioSources[currentTrackIndex].title;
	artistName.textContent = audioSources[currentTrackIndex].artists.toString();
	songGenre.textContent = audioSources[currentTrackIndex].genre;
	songDuration.textContent = audioSources[currentTrackIndex].duration
		.toString()
		.replace(".", ":");
	updatePlaylistButton(playList.filter((song) => song.id == audioSources[currentTrackIndex].id).length > 0);
}

function checkPlayDuration() {

	if (audioPlayer.loop && audioPlayer.currentTime == 0) {
		isPlaythresholdCrossed = false;
	}
	if (!isPlaythresholdCrossed && audioPlayer.currentTime > playThresold) {
		isPlaythresholdCrossed = true;
		addPlayHistory(userId, audioSources[currentTrackIndex].id);
	}
}

function loadTrack() {
	audioPlayer.src = audioSources[currentTrackIndex].location;
	audioPlayer.load();
}

function togglePlayPause() {
	if (isPlaying) {
		audioPlayer.pause();
	} else {
		audioPlayer.play();
	}
	isPlaying = !isPlaying;
	updatePlayPauseButton();
}

function playNext() {
	currentTrackIndex = (currentTrackIndex + 1) % audioSources.length;
	updateSongDisplay();
	loadTrack();
	audioPlayer.play();
	isPlaythresholdCrossed = false;
	isPlaying = true;
	updatePlayPauseButton();
}

function playPrevious() {
	currentTrackIndex =
		(currentTrackIndex - 1 + audioSources.length) % audioSources.length;
	updateSongDisplay();
	loadTrack();
	audioPlayer.play();
	isPlaythresholdCrossed = false;
	isPlaying = true;
	updatePlayPauseButton();
}

function toggleMute() {
	audioPlayer.muted = !audioPlayer.muted;
	updateVolumeButton();
}

function updatePlayPauseButton() {
	const playPauseButton = document.getElementById("playPauseButton");
	playPauseButton.innerHTML = isPlaying
		? '<i class="material-symbols-outlined">pause</i>'
		: '<i class="material-symbols-outlined">play_arrow</i>';
}

function updateVolumeButton() {
	const volumeButton = document.getElementById("volumeButton");
	volumeButton.innerHTML = audioPlayer.muted
		? '<i class="material-symbols-outlined">no_sound</i>'
		: '<i class="material-symbols-outlined">volume_up</i>';
}

function updatePlaylistButton(inPlaylist) {
	const playlistButton = document.getElementById("playlistButton");
	playlistButton.innerHTML = inPlaylist
		? '<i class="material-symbols-outlined">playlist_remove</i>'
		: '<i class="material-symbols-outlined">playlist_add</i>';
}

function setVolume() {
	audioPlayer.volume = volumeControl.value / 100;
	audioPlayer.muted = false;
}

function toggleLoop() {
	audioPlayer.loop = !audioPlayer.loop;
	isLooped = !isLooped;
	updateLoopIcon();
}

function updateLoopIcon() {
	const loopButton = document.getElementById("loopButton");
	loopButton.innerHTML = isLooped
		? '<i class="material-symbols-outlined">repeat_one</i>'
		: '<i class="material-symbols-outlined">repeat</i>';
}

function updateSongProgress() {
	const progressBar = document.getElementById("progressControl");
	const songCurrentTime = document.getElementById("song-current-time");
	songCurrentTime.innerHTML =
		Math.trunc(Math.trunc(audioPlayer.currentTime) / 60) +
		":" +
		(Math.trunc(audioPlayer.currentTime) % 60 < 10
			? "0" + (Math.trunc(audioPlayer.currentTime) % 60)
			: Math.trunc(audioPlayer.currentTime) % 60);

	if (!isNaN(audioPlayer.duration)) {
		progressBar.value =
			(audioPlayer.currentTime / audioPlayer.duration) * 100;
	} else {
		progressBar.value = 0;
	}
}

function setPlaybackPosition() {
	audioPlayer.currentTime =
		(progressControl.value / 100) * audioPlayer.duration;
}


function togglePlaylist() {
	let songId = audioSources[currentTrackIndex].id;
	if (playList.filter((song) => song.id == songId).length > 0) {
		deleteSongFromPlaylist(userId, songId);

	} else {
		addSongToPlaylist(userId, songId);
	}
}


function openPopup() {
	document.getElementById("overlay").style.display = "flex";
}

function closePopup() {
	document.getElementById("overlay").style.display = "none";
}

function scrollToSection(sectionId) {
	var section = document.getElementById(sectionId);

	if (section) {
		section.scrollIntoView({ behavior: "smooth" });
	}
}

function scrollToTop() {
	window.scrollTo(0, 0);
}

function logout() {
	window.location.href = "/Jingle/LogoutServlet";
}
