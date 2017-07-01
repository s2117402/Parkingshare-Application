import sqlite3

def findparkingspace(lat,lon):
    conn = sqlite3.connect('location.db')
    print "Opened database successfully";
    range = 0.051121
    cursor = conn.execute("SELECT NAME, DES, LAT, LON ,OCC from PARKING WHERE OCC=0 AND LAT>"
                          + str(lat - range) + " AND LAT<" + str(lat + range) + " AND LON>" + str(
        lon - range) + " AND LON<" + str(lon + range))
    ls = list(cursor)
    size = len(ls)
    count = 0;
    result = str(size) + "["
    if (len(ls) != 0):
        for entry in ls:
            result = result + "{\"NAME\": \"" + str(entry[0]) + "\", \"DES\": \"" + str(
                entry[1]) + "\", \"LAT\": " + str(entry[2]) + ", \"LON\": " + str(entry[3]) + "}"
            if (count < size - 1):
                result = result + ", "
            count = count + 1
        result = result + "]"
    print result
    conn.close()
    return result