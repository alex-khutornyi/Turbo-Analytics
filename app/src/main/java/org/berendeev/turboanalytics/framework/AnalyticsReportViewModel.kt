package org.berendeev.turboanalytics.framework

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.GeneralReport
import timber.log.Timber
import java.time.Duration
import java.time.LocalDateTime

private const val MIN_MINUTES_BEFORE_SEND = 15L
private const val MAX_HOURS_BEFORE_SEND = 24L

@Deprecated("Should be removed later, since ViewModel scope is too small. Use AnalyticsStorage.kt instead")
abstract class AnalyticsReportViewModel <T: GeneralReport> (
    private val reporter: AnalyticsReporter,
) : ViewModel() {

    private lateinit var report: T

    abstract val defaultReport: T

    fun reset() {
        Timber.e("reset: ")
        if (::report.isInitialized &&  report.isUpdatedLongEnough()) {
            sendAsync()
        }

        report = defaultReport
    }

    //write a unit test for this method
    private fun GeneralReport.isUpdatedLongEnough(): Boolean {
        val durationSinceLastUpdate = Duration.between(report.createdAt, LocalDateTime.now())

        return durationSinceLastUpdate > Duration.ofMinutes(MIN_MINUTES_BEFORE_SEND)
                && durationSinceLastUpdate < Duration.ofHours(MAX_HOURS_BEFORE_SEND)
    }

    fun getReport() = report

    fun update(report: T) {
        Timber.e( "update: old=${this.report}, date=${this.report.createdAt}")
        Timber.e( "update: new=$report, date=${report.createdAt}")
        this.report = report
    }

    override fun onCleared() {
        Timber.e("onCleared: ")
        sendAsync()
    }

    private fun sendAsync() {
        val analyticsReport = report as? AnalyticsReport
            ?: throw IllegalStateException("Expected report type `${AnalyticsReport::class.qualifiedName}` but found `${report::class.qualifiedName}`")

        GlobalScope.launch(Dispatchers.IO) {
            Timber.e( "sendAsync: $analyticsReport, date=${report.createdAt}")

            reporter.send(analyticsReport)
        }
    }

}