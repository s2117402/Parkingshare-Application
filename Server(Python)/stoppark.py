import sqlite3

def stopmyparking(name):
    conn = sqlite3.connect('location.db')
    cursor = conn.execute("SELECT NAME FROM PARKING WHERE NAME=" + "\'"+name.strip()+"\' AND OCC=1");
    temp=list(cursor)
    if(len(temp)==0):
        return "Stop parking successfully!"
    else:
        conn.execute("UPDATE PARKING SET OCC = 0 WHERE NAME = "+ "\'"+name.strip()+"\'");
        conn.commit()
        conn.close()
        return "Stop parking successfully!"
