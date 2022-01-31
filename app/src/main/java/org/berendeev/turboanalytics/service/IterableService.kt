package org.berendeev.turboanalytics.service

import org.berendeev.turboanalytics.service.event.AnalyticsReport.Iterable
import org.berendeev.turboanalytics.AnalyticsService

class IterableService : AnalyticsService<Iterable> {
    override fun send(event: Iterable) {
        when (event) {
            is Iterable.FirebaseMessage -> {/*IterableFirebaseMessagingService.handleMessageReceived(context, event.remoteMessage)*/}
            is Iterable.InAppClick -> {/*IterableApi.getInstance().trackInAppClick(event.messageId, event.clickedUrl)*/}
            is Iterable.General -> {/*convert with CustomEventConverter and send to IterableApi*/}
        }
    }
}

class RemoteMessage