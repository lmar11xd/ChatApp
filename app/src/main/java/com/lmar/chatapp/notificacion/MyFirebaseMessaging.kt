package com.lmar.chatapp.notificacion

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage

class MyFirebaseMessaging: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data.isEmpty()) {
            if(needsToBeScheduled()) {
                //scheduledJob()
            } else {
                //handleNow(message)
            }
        }

        message.notification?.let {
            //sendNotification(it.body ?: "")
        }
    }

    private fun needsToBeScheduled() = true

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        //sendRegistrationToServer()
    }
}