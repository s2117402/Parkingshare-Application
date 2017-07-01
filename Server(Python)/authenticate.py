from flask import Flask, request
import query
import registeration
import registerParking
import findparkingspace
import startpark
import stoppark
import jiguang
app = Flask(__name__)
@app.route('/')
def hello_world():
    return 'hello world'

@app.route('/authenticate', methods=['POST'])
def authenticate():
    print request.headers
    # print request.form
    print request.form.get('name')
    print request.form['password']
    # print request.form.getlist('name')
    # print request.form.get('nickname', default='little apple')
    num1=request.form.get('name')
    num2=request.form.get('password')
    username = str(num1.encode("ascii"))
    realpassword = str(num2.encode("ascii"))
    result=str(query.authentication(username,realpassword))
    print result
    return result

@app.route('/register', methods=['POST'])
def register():
    print request.headers
    # print request.form
    print request.form.get('name')
    print request.form['password']
    # print request.form.getlist('name')
    # print request.form.get('nickname', default='little apple')
    num1=request.form.get('name')
    num2=request.form.get('password')
    username = str(num1.encode("ascii"))
    realpassword = str(num2.encode("ascii"))
    result=str(registeration.createaccount(username, realpassword))
    print result
    return result

@app.route('/newparking', methods=['POST'])
def newparking():
    print request.headers
    # print request.form
    print request.form.get('name')
    print request.form['description']
    print request.form['lat']
    print request.form['lon']
    name=request.form.get('name')
    description=request.form.get('description')
    lat=request.form.get('lat')
    lon=request.form.get('lon')
    result=str(registerParking.newparking(name,description,lat,lon))
    print result
    return result

@app.route('/findparking', methods=['POST'])
def findparking():
    print request.headers
    # print request.form
    print request.form['lat']
    print request.form['lon']
    lat=request.form.get('lat')
    lon=request.form.get('lon')
    return findparkingspace.findparkingspace(float(lat), float(lon))

@app.route('/startparking', methods=['POST'])
def startparking():
    print request.headers
    # print request.form
    print request.form['name']
    name=request.form.get('name')
    username=request.form.get('username')
    from threading import Thread
    t = Thread(target=jiguang.sendinfo, args=(username,name))
    t.start()
    return str(startpark.startmyparking(name))

@app.route('/stopparking', methods=['POST'])
def stopparking():
    print request.headers
    # print request.form
    print request.form['name']
    name=request.form.get('name')
    return str(stoppark.stopmyparking(name))

if __name__ == '__main__':
    # app.run(debug=True)
    app.run(host='0.0.0.0', debug=True)
