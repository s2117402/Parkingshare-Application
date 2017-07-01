import sqlite3


def newparking(name, description, lat, lon):
    conn = sqlite3.connect('location.db')
    print "SELECT NAME FROM PARKING WHERE NAME=" + "\'"+name.strip()+"\'"
    cursor = conn.execute("SELECT NAME FROM PARKING WHERE NAME=" + "\'"+name.strip()+"\'");
    temp=list(cursor)
    if(len(temp)==0):
        print "INSERT INTO PARKING (NAME, DES, LAT, LON ,OCC) \
              VALUES ("+'\''+name.strip()+'\''+", "+'\''+description.strip()+'\''+", "+str(lat)+", "+str(lon)+" ,0)"
        conn.execute("INSERT INTO PARKING (NAME, DES, LAT, LON ,OCC) \
              VALUES ("+'\''+name.strip()+'\''+", "+'\''+description.strip()+'\''+", "+str(lat)+", "+str(lon)+" ,0)");
        conn.commit()
        conn.close()
        return "Share ParkingSpace successfully"
    else:
        return "This name has been taken!"
