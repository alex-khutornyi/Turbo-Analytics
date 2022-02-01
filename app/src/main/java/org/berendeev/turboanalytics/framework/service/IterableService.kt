package org.berendeev.turboanalytics.framework.service

import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport.Iterable
import javax.inject.Inject

class IterableService @Inject constructor() : AnalyticsService<Iterable> {
    override fun send(report: Iterable) {
        when (report) {
            is Iterable.FirebaseMessage -> {/*IterableFirebaseMessagingService.handleMessageReceived(context, event.remoteMessage)*/}
            is Iterable.InAppClick -> {/*IterableApi.getInstance().trackInAppClick(event.messageId, event.clickedUrl)*/}
            is Iterable.General -> {/*convert with CustomEventConverter and send to IterableApi*/}
        }
    }
}

class RemoteMessage