import sqlite3

conn = sqlite3.connect('location.db')
print "Opened database successfully";

conn.execute('''CREATE TABLE PARKING
       (NAME           TEXT    NOT NULL UNIQUE,
       DES             TEXT     NOT NULL,
       LAT             REAL     NOT NULL,
       LON             REAL     NOT NULL,
       OCC             BLOB     NOT NULL);''')
print "Table created successfully";

conn.close()