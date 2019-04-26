'use-strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.firestore.document("tenants/{userId}/notification/{notificationId}").onWrite((snap, context)=> {

  //const user_id = event.params.user_id;
  //const notification_id = event.params.notification_id;
  const userId = context.params.userId;
  const notificationId = context.params.notificationId;

  console.log("tenants_id: " + userId + "| notification_id: " + notificationId);

  return admin.firestore().collection("tenants").doc(userId).collection("notification").doc(notificationId).get().then((queryResult) => {
  //admin.firestore().collection("tenants").doc(userId).collection("notification").doc(notificationId).get()
    console.log(queryResult);
    const from_user_id = queryResult.data().senderEmail;
    const content = queryResult.data().content;
    const title = queryResult.data().title;

    const from_data = admin.firestore().collection("landlords").doc(from_user_id).get();
    const to_data = admin.firestore().collection("tenants").doc(userId).get();


    // const result = firebasePromise("landlords", from_user_id).then((from_data) => {
    //     const to_data = firebasePromise("tenants", userId).then((to_data) => {
    //       return to_data.data().name;
    //     });
    //     return [from_data.data().name, to_data];
    // });



    return Promise.all([from_data, to_data]).then(result => {

      const from_name = result[0].data().name;
      const to_name = result[1].data().name;
      const phoneToken = result[1].data().phoneToken;

      console.log("from_name: " + from_name + "| to_name: " + to_name);

      const payload = {
        notification: {
          title : title,
          body : content,
        }
      };

      return admin.messaging().sendToDevice(phoneToken, payload).then(result => {

        console.log("Notification Sent.");
        return result;
      });
    });
  });
});
// function firebasePromise (x, y) {
//   return new Promise((resol, rej) => {
//     resol(admin.firestore().collection(x).doc(y).get());
//   });
// };

// new Promise((resol, rej) => {
//   resol(admin.firestore().collection("tenants").doc(userId).collection("notification").doc(notificationId).get());
// })
