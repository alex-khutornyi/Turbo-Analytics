package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.AnalyticsEvent.Iterable

class IterableSender : Sender<Iterable> {
    override fun send(event: Iterable) {
        when (event) {
            is Iterable.FirebaseMessage -> {/*IterableFirebaseMessagingService.handleMessageReceived(context, event.remoteMessage)*/}
            is Iterable.InAppClick -> {/*IterableApi.getInstance().trackInAppClick(event.messageId, event.clickedUrl)*/}
        }
    }
}

class RemoteMessage