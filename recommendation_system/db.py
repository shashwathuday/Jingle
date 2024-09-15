import mysql.connector


class MySQLDBConnector:
    def __init__(self) -> None:
        self.connection = mysql.connector.connect(
            host="localhost",
            user="jingleadmin",
            password="jingleadmin",
            database="jingle",
        )

    def get_db_connection(self):
        return self.connection
