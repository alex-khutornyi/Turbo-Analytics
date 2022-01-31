package org.berendeev.turboanalytics.ui

import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.kochava.base.Tracker
import org.berendeev.turboanalytics.AnalyticsReporter
import org.berendeev.turboanalytics.AnalyticsReporterImpl
import org.berendeev.turboanalytics.databinding.ActivityMainBinding
import org.berendeev.turboanalytics.service.ForterService
import org.berendeev.turboanalytics.service.IterableService
import org.berendeev.turboanalytics.service.KochavaService
import org.berendeev.turboanalytics.service.event.AnalyticsReport
import org.berendeev.turboanalytics.service.event.ReportName
import org.berendeev.turboanalytics.service.event.ReportProperty
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsReporter: AnalyticsReporter = AnalyticsReporterImpl(
        KochavaService(),
        IterableService(),
        ForterService(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

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
        }
    }

    private fun getRentCarButtonClickedEvent(): AnalyticsReport {
        return RentCarButtonClickedReport(
            isConfirmed = true,
            nameString = "Audi",
            time = System.currentTimeMillis(),
            date = LocalDate.now(),
        )
    }
}

class DeepLink(url: Uri) : AnalyticsReport.Kochava.Standard(
    Tracker.Event(Tracker.EVENT_TYPE_DEEP_LINK)
        .setUri(url)
)

@ReportName("Activity.Created")
class ActivityOpenedReport(
    @ReportProperty("TIME")
    val time: Long,
) : AnalyticsReport.Kochava.General()

@ReportName("Button.SignUp")
class RentCarButtonClickedReport(
    @ReportProperty("IS_CONFIRMED")
    val isConfirmed: Boolean,
    @ReportProperty("NAME_STRING")
    val nameString: String,
    @ReportProperty("TIME")
    val time: Long,
    @ReportProperty("DATE")
    val date: LocalDate,
) : AnalyticsReport.Iterable.General()

