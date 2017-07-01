import sqlite3


def createaccount(username,password):
    conn = sqlite3.connect('test.db')
    print "Opened database successfully";
    print "SELECT PASSWORD from COMPANY where USER=" + '\''+username.strip()+'\''
    cursor = conn.execute("SELECT USER from COMPANY where USER=" + '\''+username.strip()+'\'')
    temp=list(cursor)
    if(len(temp)==0):
        print "INSERT INTO COMPANY (USER,PASSWORD) \
              VALUES ("+'\''+username.strip()+'\''+", "+'\''+password.strip()+'\''+")"
        conn.execute("INSERT INTO COMPANY (USER,PASSWORD) \
              VALUES ("+'\''+username.strip()+'\''+", "+'\''+password.strip()+'\''+")");
        conn.commit()
        conn.close()
        return "Register successfully"
    else:
        print "This Username has been taken!"
        return "This Username has been taken!"
