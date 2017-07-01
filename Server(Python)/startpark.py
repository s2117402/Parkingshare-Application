import sqlite3

def startmyparking(name):
    conn = sqlite3.connect('location.db')
    print "UPDATE PARKING SET OCC = 1 WHERE NAME = "+ "\'"+name.strip()+"\'"
    cursor = conn.execute("SELECT NAME FROM PARKING WHERE NAME=" + "\'"+name.strip()+"\' AND OCC=0");
    temp=list(cursor)
    if(len(temp)==0):
        return "This Parking Space has been occupied already!"
    else:
        conn.execute("UPDATE PARKING SET OCC = 1 WHERE NAME = "+ "\'"+name.strip()+"\'");
        conn.commit()
        conn.close()
        return "Start parking successfully!"
