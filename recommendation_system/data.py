import scipy
import pandas as pd
from db import MySQLDBConnector


def get_user_song_crs_matrix() -> scipy.sparse.csr_matrix:
    con = MySQLDBConnector().get_db_connection()
    user_song_df = pd.read_sql(
        "select user_id, song_id, count(*) as weight from song_history group by user_id, song_id;",
        con,
    )
    user_song_df.set_index(["user_id", "song_id"], inplace=True)
    coo = scipy.sparse.coo_matrix(
        (
            user_song_df.weight.astype(float),
            (
                user_song_df.index.get_level_values(0),
                user_song_df.index.get_level_values(1),
            ),
        )
    )
    return coo.tocsr()
