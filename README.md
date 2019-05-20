## LeLeLe
LeLeLe is naming from three words, Lessor, Lessee and Lease, using to help to improve the communication between the landlord and the tenants. Landlords are able to create groups, invite tenants, and edit electricity fee of each room. Tenants are able to check own electricity fee, be invited to a room.
<br /><br />[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="200">](https://play.google.com/store/apps/details?id=com.hugh.lelele)

- Real-Time Chatting
  - Landlord and Tenant could send messages to each other any time.
- Post Articles
  - Home page is designed as a bulletin board.
  - Landlords and Tenants are allowed to post articles.
  - Landlord can delete all articles, no matter who the auther is.
  - Tenant can only delete the articles which are written by him.
- Electricity Fee
  - Landlords are able to edit the electricity fee of each and send a notifications to tenants after editting is done.
  - Tenants are able to check his electricity fee onlin.
  - Graphing electricity fee for tenants.
- Groups
  - Landlord could create new groups.
  - Landlord could edit his group details including add/ delete the rooms.
- Rooms
  - Landlord could check the room list of the group.
  - Lnadlord could invite tenant to a room.
  - Lnadlord could delete tenant from a room.
  - Lnadlord could cancle the invitation.
- Inviting
  - Landlord could send a invitation to a tenant by searching tenant's email.
  - Tenant could reject the invitation comming from landlord.
- Profile
  - User will be asked to fill all his user detail information.
  - There's notifying articles on the home page, when the profil is not completed.
- Notification
  - User could check his notifications in notification page.

## Screenshot

<img src="https://i.imgur.com/6laMMlG.png" width="210">
<img src="https://i.imgur.com/VU1Mz3Z.png" width="210">
<img src="https://i.imgur.com/Ze9gh3F.png" width="210">
<img src="https://i.imgur.com/S08SczK.png" width="210">

## Major Features
-	Following MVP design pattern for making it easier to maintain and extend new features.
-	Handling two types of users, and giving different functions and layout to different user types.
-	Pushing notifications to tenants with defined events through Cloud Function for Firebase, Cloud Messaging, and Firebase Admin SDK for FCM.
-	Implement the real-time chatting function by setting up the Firestore collection listener.
-	Graphing the electricity fee by MPAndroidChart.
-	Doing unit tests with JUnit, Mockito and Espresso to ensure there’s no new issue appearing after refactoring.
-	Tracking through Fabric Crashlytics and Google Analytics.
-	Using Firestore to save user data, especially the rooms and the groups data.
-	Using Facebook API to implement the login function.
-	Using Room to keep user data as a local database.
-	Posting articles and showing the articles immediately through the Firestore real-time updates.

## Requirement
- Android Studio 3.0+
- Android SDK 24+

## Version
* 1.0.3 - 2019/05/13 Lastest Version

## Contact
n1553330708@gmail.com <br />
n1553330708@yahoo.com.tw
