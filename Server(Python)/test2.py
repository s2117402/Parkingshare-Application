import json
import sqlite3
import startpark
conn = sqlite3.connect('location.db')
startpark.startmyparking("wsun1")