package org.berendeev.turboanalytics.ui

import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.kochava.base.Tracker
import org.berendeev.turboanalytics.framework.AnalyticsReporter
import org.berendeev.turboanalytics.framework.AnalyticsReporterImpl
import org.berendeev.turboanalytics.databinding.ActivityMainBinding
import org.berendeev.turboanalytics.framework.service.ForterService
import org.berendeev.turboanalytics.framework.service.IterableService
import org.berendeev.turboanalytics.framework.service.KochavaService
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Name
import org.berendeev.turboanalytics.framework.service.report.GeneralReport.Property
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

@Name("Activity.Created")
class ActivityOpenedReport(
    @Property("TIME")
    val time: Long,
) : AnalyticsReport.Kochava.General()

@Name("Button.SignUp")
class RentCarButtonClickedReport(
    @Property("IS_CONFIRMED")
    val isConfirmed: Boolean,
    @Property("NAME_STRING")
    val nameString: String,
    @Property("TIME")
    val time: Long,
    @Property("DATE")
    val date: LocalDate,
) : AnalyticsReport.Iterable.General()

