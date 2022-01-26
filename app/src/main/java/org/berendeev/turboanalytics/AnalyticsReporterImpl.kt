package org.berendeev.turboanalytics

import org.berendeev.turboanalytics.sdk.ForterSender
import org.berendeev.turboanalytics.sdk.IterableSender
import org.berendeev.turboanalytics.sdk.KochavaSender
import org.berendeev.turboanalytics.sdk.event.AnalyticsEvent

class AnalyticsReporterImpl(
    private val kochavaSender: KochavaSender,
    private val iterableSender: IterableSender,
    private val forterSender: ForterSender,
) : AnalyticsReporter {

    override fun send(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.Kochava -> kochavaSender.send(event)
            is AnalyticsEvent.Iterable -> iterableSender.send(event)
            is AnalyticsEvent.Forter -> forterSender.send(event)
            else -> throw IllegalArgumentException("Sender `${SdkSender::class.qualifiedName}` implementation NOT found for `${event::class.qualifiedName}`")
        }
    }
}
