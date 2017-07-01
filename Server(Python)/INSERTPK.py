import sqlite3

conn = sqlite3.connect('location.db')
print "Opened database successfully";
conn.execute("INSERT INTO PARKING (NAME, DES, LAT, LON, OCC) \
      VALUES ('MY HOME','NO',31.799879,-85.965426,0)");
print "Table created successfully";
conn.commit()
conn.close()