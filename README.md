# Parkingshare-Application
### Breif Introduction
   This is a ParkingShare System based on my another project 'Registration-and-Login-System'.In this project,people can
   share their free parking space with people and they also can find a free parking space.This system has two terminals.One
   is the client terminal(Android APP),the other is a server timinal(Python Flask).Android APP is open for users and server is
   used to manage the whole system.
   
### Client Terminal
   This terminal is an Android APP actually.The app is formed with 5 activities.(Login,Register,UserView,ShareParking Findparking).
#### Login and Register Activity
   The first activity is the Login Activty,this activiey and theother Register Activity are completely based on an other project I finished,so I won't mention these too much here.

---
<div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/login.png" width="400" height="600" alt="Login"/>
</div>

---
<div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/register.png" width="400" height="600" alt="Login"/>
</div>

---
#### User Activity
   Here you can see two buttons.They mean two functions avaliable,you can press 'Share my parking space' to share your free
   parking space with people,or you can press 'Find a parking space' to find a free parking space.

---
<div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/userview.png" width="400" height="600" alt="Login"/>
</div>

---
#### ShareParking Activity
   Here you can share your free parking space(Before you want to share your parking space,please make sure you are standing at
   the parking space).At first,you should name your shared parking space and give some description about it.Then press 'Get location' to get the GPS information(The system will mark your location in the googlemap),then submit it.Here are some pictures of possible cases below.

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/locating.png" width="400" height="600" alt="Locating"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/share.png" width="400" height="600" alt="ShareView"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/sharesuccess.png" width="400" height="600" alt="sucess"/>
</div>

---

#### FindParking Activity
     This activity is desiged for the people to find a parking space.At first,you can press the 'EXPLORE PARKINGSPACE' button
     to find some parking spaces(5 max due to server) are close to your current location.Then the app will mark all these avalible parking spaces at google map,and you can get the names and descriptions at the google map(you even can get the navigation to the parkingspace you selected).When you have choose the parking space already,press the 'Start parking' button
     to occupy the space,the server will send you a push to inform your parking information meanwhile.When you finished the parking ,press 'Stop parking' to release the parking space.
     
---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/parkingview.png" width="400" height="600" alt="ParkingAc"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/parkingspace.png" width="400" height="600" alt="ParkingSpace Marker"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/startparking.png" width="400" height="600" alt="Startparking"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/stopparking.png" width="400" height="600" alt="Stopparking"/>
</div>

---
 <div align=center>
<img src="https://raw.githubusercontent.com/s2117402/Parkingshare-Application/master/Image/push.png" width="600" height="200" alt="ParkinginfoPush"/>
</div>

---


   
