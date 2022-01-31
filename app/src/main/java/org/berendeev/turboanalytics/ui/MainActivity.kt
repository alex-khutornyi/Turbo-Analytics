package org.berendeev.turboanalytics.ui

import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.kochava.base.Tracker
import dagger.hilt.android.AndroidEntryPoint
import org.berendeev.turboanalytics.framework.AnalyticsReporter
import org.berendeev.turboanalytics.framework.AnalyticsReporterImpl
import org.berendeev.turboanalytics.databinding.ActivityMainBinding
import org.berendeev.turboanalytics.framework.service.ForterService
import org.berendeev.turboanalytics.framework.service.IterableService
import org.berendeev.turboanalytics.framework.service.KochavaService
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Name
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Property
import org.berendeev.turboanalytics.ui.MainAnalyticsReportViewModel.FabButtonClicksReport
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var analyticsReporter: AnalyticsReporter

    private val analyticsReportViewModel: MainAnalyticsReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        analyticsReportViewModel.reset()

        analyticsReporter.report(ActivityOpenedReport(System.currentTimeMillis()))

        binding.fab.setOnClickListener {

            analyticsReporter.report(AnalyticsReport.Forter.Location(Location("Victoria, BC")))

            analyticsReporter.report(DeepLink("some URI".toUri()))

            analyticsReporter.report(
                RentCarButtonClickedReport(
                    isConfirmed = true,
                    nameString = "Audi",
                    time = System.currentTimeMillis(),
                    date = LocalDate.now(),
                )
            )

            val updated = analyticsReportViewModel.getReport()
                .let { report ->
                    report.copy(clickCount = report.clickCount + 1)
                }
            analyticsReportViewModel.update(updated)
        }
    }

}

class DeepLink(url: Uri) : AnalyticsReport.Kochava.Standard(
    Tracker.Event(Tracker.EVENT_TYPE_DEEP_LINK)
        .setUri(url)
)

@Name("Activity.Created")
class ActivityOpenedReport(
    @Property("TIME")
    val time: Long,
) : AnalyticsReport.Kochava.General()

@Name("Button.SignUp")
data class RentCarButtonClickedReport(
    @Property("IS_CONFIRMED")
    val isConfirmed: Boolean,
    @Property("NAME_STRING")
    val nameString: String,
    @Property("TIME")
    val time: Long,
    @Property("DATE")
    val date: LocalDate,
) : AnalyticsReport.Iterable.General()

