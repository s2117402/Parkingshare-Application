import sqlite3

conn = sqlite3.connect('location.db')
print "Opened database successfully";
lat=31.7556628
lon=-85.9772869
range=0.051121
cursor = conn.execute("SELECT NAME, DES, LAT, LON ,OCC from PARKING WHERE OCC=0 AND LAT>"
                      +str(lat-range)+" AND LAT<"+str(lat+range)+" AND LON>"+str(lon-range)+" AND LON<"+str(lon+range))
ls=list(cursor)
size=len(ls)
count=0;
result=str(size)+"[\n"
if(len(ls)!=0):
    for entry in ls:
        result=result+"  {\n    \"NAME\": \""+str(entry[0])+"\",\n    \"DES\": \""+str(entry[1])+"\",\n    \"LAT\": "+str(entry[2])+",\n    \"LON\": "+str(entry[3])+"}"
        if(count<size-1):
            result=result+", "
        count=count+1
    result=result+"]"
print result
conn.close()
