import sqlite3

conn = sqlite3.connect('test.db')
print "Opened database successfully";

cursor = conn.execute("SELECT USER, PASSWORD from COMPANY")
for row in cursor:
   print "USER = ", row[0]
   print "PASSWORD = ", row[1], "\n"

print "Operation done successfully";
conn.close()