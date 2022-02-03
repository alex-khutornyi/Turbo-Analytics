package org.berendeev.turboanalytics.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kochava.base.Tracker
import dagger.hilt.android.AndroidEntryPoint
import org.berendeev.turboanalytics.framework.AnalyticsReporter
import org.berendeev.turboanalytics.databinding.ActivityMainBinding
import org.berendeev.turboanalytics.framework.AnalyticsStorage
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Name
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Property
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var analyticsReporter: AnalyticsReporter

    @Inject
    lateinit var analyticsStorage: AnalyticsStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        analyticsStorage.create(ActivityOpenedReport::class.java)
        Timber.e("onCreate: INIT: ${analyticsStorage.get(ActivityOpenedReport::class.java)}")

        binding.fab.setOnClickListener {

            Timber.e("onCreate: OLD value: ${analyticsStorage.get(ActivityOpenedReport::class.java)}")

            analyticsStorage.get(ActivityOpenedReport::class.java).apply {
                openedTimes++
            }

            Timber.e("onCreate: UPDATED value: ${analyticsStorage.get(ActivityOpenedReport::class.java)}")

        }
    }

}

class DeepLink(url: Uri) : AnalyticsReport.Kochava.Standard(
    Tracker.Event(Tracker.EVENT_TYPE_DEEP_LINK)
        .setUri(url)
)

@Name("Activity.Created")
data class ActivityOpenedReport(
    @Property("TIMES")
    var openedTimes: Long = 0,
) : AnalyticsReport.Kochava.General() {
    fun incrementOpenTimes(): ActivityOpenedReport = copy(openedTimes = openedTimes + 1)
}

@Name("Button.SignUp")
data class RentCarButtonClickedReport(
    @Property("IS_CONFIRMED")
    val isConfirmed: Boolean = false,
    @Property("NAME_STRING")
    val nameString: String = "undefined",
    @Property("TIME")
    val time: Long = System.currentTimeMillis(),
    @Property("DATE")
    val date: LocalDate = LocalDate.now(),
) : AnalyticsReport.Iterable.General()

