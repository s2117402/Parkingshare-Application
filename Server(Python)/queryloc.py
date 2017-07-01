import sqlite3

conn = sqlite3.connect('location.db')
print "Opened database successfully";
cursor = conn.execute("SELECT NAME, DES, LAT, LON, OCC from PARKING")
print cursor.arraysize;
for row in cursor:
    print row[0];
    print row[1];
    print row[2];
    print row[3]
    print row[4],"\n";
print "Opened database successfully";
