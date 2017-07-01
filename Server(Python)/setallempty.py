import sqlite3

conn = sqlite3.connect('location.db')
cursor = conn.execute("UPDATE PARKING SET OCC = 0");
conn.commit()
conn.close()
