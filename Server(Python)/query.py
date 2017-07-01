import sqlite3

def authentication(username,realpassword):
    conn = sqlite3.connect('test.db')
    cursor = conn.execute("SELECT PASSWORD from COMPANY where USER=" + '\''+username.strip()+'\'')
    temp=list(cursor)
    if(len(temp)==0):
        print "No This User"
        return "No This User!"
    else:
        password = temp[0][0]
        conn.close()
        if (password == realpassword):
            print "Login Successfully"
            return "Login Successfully"
        else:
            print "Password Incorrect!"
            return "Password Incorrect!"


