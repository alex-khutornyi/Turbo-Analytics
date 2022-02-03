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
     * @param clazz: required a class with an empty constructor
     */
    fun create(clazz: Class<out GeneralReport>) {
        try {
            if (reports.put(clazz, clazz.newInstance()) != null) {
                throw IllegalStateException("Key `${clazz.canonicalName}` is already in Storage")
            }
        } catch (e: Throwable) {
            error(e)
        }
    }

    fun <T : GeneralReport> update(clazz: Class<out T>, updateAction: (T) -> Unit) {
        updateAction(get(clazz))
    }

    fun update(report: GeneralReport) {
        if (reports.put(report::class.java, report) == null) {
            // TODO: throw Exception in Debug, but report to Crashlytics in Release
            throw IllegalStateException("TODO: write a message")
        }
    }

    fun delete(clazz: Class<out GeneralReport>) {
        if (reports.remove(clazz) == null) {
            // TODO: throw Exception in Debug, but report to Crashlytics in Release
            throw IllegalStateException("TODO: write a message")
        }
    }

    fun reset(clazz: Class<out GeneralReport>) {
        delete(clazz)
        create(clazz)
    }

    fun <T : GeneralReport> get(clazz: Class<out T>): T {
        val generalReport = (reports[clazz])
            ?: throw IllegalStateException("TODO: write a message") // TODO: throw Exception in Debug, but report to Crashlytics in Release
        return generalReport as T
    }

    fun <T : GeneralReport> getAndDelete(clazz: Class<out T>): T {
        val report = get(clazz)
        delete(clazz)
        
        return report
    }

    private fun error(exception: Throwable) {
        if(BuildConfig.DEBUG)
            throw exception
        else {
            Timber.e(exception, "send exception to Crashlytics")
        }
    }

}