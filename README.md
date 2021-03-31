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
* Course data taken by api via Retrofit library. We only keep course link Firestore database
* User can filter requests by country via filter button
* User can go donation fragment via donation button

When user click the donate button 
  * Course link send to donate fragment via Navigation Component safe-agrs library
  * Donate Fragment opens

### Donate Fragment

There are two types payment options here. 
 * Donate with Google Play app purchases
 * Donate with wallet

<img src="https://user-images.githubusercontent.com/64445944/113193834-16ff8d00-9269-11eb-9619-c4b7e0ea9820.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113193847-1961e700-9269-11eb-9f97-470700ef89da.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113193853-1a931400-9269-11eb-88d4-d58b8a7ff269.jpg" height=500> <img src="https://user-images.githubusercontent.com/64445944/113193868-1cf56e00-9269-11eb-9ec9-c3e77fbbfa80.jpg" height=500>

When user click the donate with wallet button alert dialog opens and ask are you sure or not.

If user wallet balance is enough 
 * Request is saved to succesfulRequest firestore collection
 * Request is deleted from request firestore collection
 * Congrats dialog open
 * Wallet balance decrease
 * Donate mail is sent to course request owner students (for now manually)

If not
  * Insufficient Balance dialog open and shown to button to direct to myWallet fragment
  
 
  

### MyWallet Fragment
 <img src="https://user-images.githubusercontent.com/64445944/113199842-3f3eba00-9270-11eb-8b17-c61da57ffb4e.jpg" height=500>
 
 When user click the Pay with Cryptocurrencies button user is directed to Coinbase website. User write mail and send money our account.
 
 When user click the Pay with Bank Transfer bank account informations are shown



---





