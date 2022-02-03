package org.berendeev.turboanalytics.framework

import org.berendeev.turboanalytics.BuildConfig
import org.berendeev.turboanalytics.framework.service.report.GeneralReport
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsStorage @Inject constructor() {

    private val reports = ConcurrentHashMap<Class<out GeneralReport>, GeneralReport>()

    /**
     * @param report: required a class with an empty constructor
     */
    fun create(report: Class<out GeneralReport>) {
        try {
            if (reports.put(report, report.newInstance()) != null) {
                throw IllegalStateException("Key `${report.canonicalName}` is already in Storage")
            }
        } catch (e: Throwable) {
            error(e)
        }
    }

    fun <T : GeneralReport>get(report: Class<out T>): T {
        val generalReport = (reports[report])
            ?: throw IllegalStateException("TODO: write a message") // TODO: throw Exception in Debug, but report to Crashlytics in Release
        return generalReport as T
    }

    fun update(report: GeneralReport) {
        if (reports.put(report::class.java, report) == null) {
            // TODO: throw Exception in Debug, but report to Crashlytics in Release
            throw IllegalStateException("TODO: write a message")
        }

    }

    fun delete(report: Class<out GeneralReport>) {
        if (reports.remove(report) == null) {
            // TODO: throw Exception in Debug, but report to Crashlytics in Release
            throw IllegalStateException("TODO: write a message")
        }
    }

    fun reset(report: Class<out GeneralReport>) {
        delete(report)
        create(report)
    }

    private fun error(exception: Throwable) {
        if(BuildConfig.DEBUG)
            throw exception
        else {
            Timber.e(exception, "send exception to Crashlytics")
        }
    }

}