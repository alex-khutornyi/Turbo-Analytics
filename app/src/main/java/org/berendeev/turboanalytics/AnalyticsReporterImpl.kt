package org.berendeev.turboanalytics

class AnalyticsReporterImpl(
private val kochavaSender: KochavaSender,
private val iterableSender: IterableSender,
private val firebaseSender: FirebaseSender,
) : AnalyticsReporter {

    override fun send(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.Kochava -> kochavaSender.send(event)
            is AnalyticsEvent.Iterable -> iterableSender.send(event)
            else -> throw IllegalArgumentException("Sender `${Sender::class.qualifiedName}` implementation NOT found for `${event::class.qualifiedName}`")
        }
    }
}
