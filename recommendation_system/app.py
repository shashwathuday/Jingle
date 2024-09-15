from flask import Flask, request, Response

from data import get_user_song_crs_matrix
from collaborative_recommender import CollaborativeRecommender

app = Flask(__name__)


@app.route("/songs", methods=["GET", "POST"])
def get_recommended_songs():
    userId = request.args.get("userId")
    user_song_matrix = get_user_song_crs_matrix()

    # instantiate recommender, fit, and recommend
    recommender = CollaborativeRecommender()
    recommender.fit(user_song_matrix)
    songIds = recommender.recommend(int(userId), user_song_matrix, n=5)
    return Response(",".join(str(songId) for songId in songIds))


if __name__ == "__main__":
    app.run(debug=True, port=9080)
