import sqlite3

conn = sqlite3.connect('test.db')
print "Opened database successfully";

conn.execute('''CREATE TABLE COMPANY
       (USER           TEXT    NOT NULL,
       PASSWORD         TEXT     NOT NULL);''')
print "Table created successfully";

conn.close()
