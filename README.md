# Share.io 🌎

### Share.io is an app that helps students who are financal means not enough reach  paid educational contents for free

## Our Tech Stack 💻
  * Kotlin-Android
  * Firebase Authentication
  * Firebase Firestore
  * Google Play App Purchases
  * Coinbase Payment for Cryptocurrencies
  * Adobe XD
 --- 
# About App Content


### App Architecture
 
<img src="https://user-images.githubusercontent.com/64445944/113156868-105e1f00-9243-11eb-9013-4002b4372724.png" height=500> 

---
### Firestore Collections-Data Classes

<img src="https://user-images.githubusercontent.com/64445944/113155030-46020880-9241-11eb-98e3-6912ed7376ab.png" height=500>
  
---

## Work Flow

### Splash Screen Activity
<img src="https://user-images.githubusercontent.com/64445944/113162909-5a95cf00-9248-11eb-9a11-a1be719add3d.jpg" height=600>

When user open the application splash screen starts as a first activity

If user logged in Main Activity opens if not Auth Activity open

---
### Auth Activity

There are 3 fragment in Auth Activity. We use Navigation Component to manage these fragments easily

<img src="https://user-images.githubusercontent.com/64445944/113160711-66809180-9246-11eb-978c-f6eac3e46c30.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113160725-684a5500-9246-11eb-93cd-2f3873e8ef3b.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113160730-6a141880-9246-11eb-8779-1c5e7373f55f.jpg" height=500> 

---
### Main Activity
There are ten different fragments for Main Activity. We used bottom navigation menu to open three main fragments.
 * Home Fragment
 * Request Fragment
 * Profile Fragment
 
<img src="https://user-images.githubusercontent.com/64445944/113181882-ef092d00-925a-11eb-9322-fec9c992a2ee.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113181691-bb2e0780-925a-11eb-8e3d-fc3818436062.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113181704-bd906180-925a-11eb-86da-64f3270eaf8b.jpg" height=500>

### In Profile Fragment 

#### User can 

* Verify account
* Look name and email
* Go myWallet fragment
* Go myRequests Fragment (for only Student)
* Go myDonations Fragment
* Log out.

### In Course Requests Fragment

#### There three possibilities here.

* If user type is student and verified user can go onlineCourseRequests Fragment and bookRequestsFragment(not available for this version).
* If user type is student but not verified an alert dialog about verification is shown by application
* If user type not student an alert dialog about user can only donation is shown by app by application

### In Home Fragment

* User can see all requests in recycler view.
* User can filter requests by country via filter button
* User can go donation fragment via donation button


  
  



---





