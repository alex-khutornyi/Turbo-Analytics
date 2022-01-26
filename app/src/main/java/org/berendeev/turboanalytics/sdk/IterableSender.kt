package org.berendeev.turboanalytics.sdk

import org.berendeev.turboanalytics.sdk.event.AnalyticsEvent.Iterable
import org.berendeev.turboanalytics.SdkSender

class IterableSender : SdkSender<Iterable> {
    override fun send(event: Iterable) {
        when (event) {
            is Iterable.FirebaseMessage -> {/*IterableFirebaseMessagingService.handleMessageReceived(context, event.remoteMessage)*/}
            is Iterable.InAppClick -> {/*IterableApi.getInstance().trackInAppClick(event.messageId, event.clickedUrl)*/}
            is Iterable.CustomEvent -> {/*convert with CustomEventConverter and send to IterableApi*/}
        }
    }
}

class RemoteMessage