from typing import List, Tuple

import implicit
import scipy


class CollaborativeRecommender:
    def __init__(self):
        self.implicit_model = implicit.als.AlternatingLeastSquares(
            factors=50, iterations=10, regularization=0.01
        )

    def fit(self, user_song_matrix: scipy.sparse.csr_matrix) -> None:
        self.implicit_model.fit(user_song_matrix)

    def recommend(
        self,
        user_id: int,
        user_song_matrix: scipy.sparse.csr_matrix,
        n: int = 10,
    ) -> Tuple[List[str], List[float]]:
        song_ids, scores = self.implicit_model.recommend(
            user_id, user_song_matrix[n], N=n
        )

        return [int(song_id) for song_id in song_ids]
