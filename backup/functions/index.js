'use-strict'

const functions = require('firebase-functions');
const admin = require('firebase-admin');

exports.sendNotification = functions.firestore.document("tenants/{tenants_id}/notification/{notification_id}").onWrite(event=> {

  const tenants_id = event.params.tenants_id;
  const notification_id = event.params.notification_id;

  console.log("tenants_id: " + tenants_id + "| notification_id: " + notification_id);

});
