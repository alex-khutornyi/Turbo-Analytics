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
import org.berendeev.turboanalytics.sdk.ForterSender
import org.berendeev.turboanalytics.sdk.IterableSender
import org.berendeev.turboanalytics.sdk.KochavaSender
import org.berendeev.turboanalytics.sdk.event.AnalyticsEvent
import org.berendeev.turboanalytics.sdk.event.EventName
import org.berendeev.turboanalytics.sdk.event.EventProperty
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val analyticsReporter: AnalyticsReporter = AnalyticsReporterImpl(
        KochavaSender(),
        IterableSender(),
        ForterSender(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        analyticsReporter.send(ActivityOpenedEvent(System.currentTimeMillis()))

        binding.fab.setOnClickListener {

            analyticsReporter.send(AnalyticsEvent.Forter.LocationEvent(Location("Victoria, BC")))

            analyticsReporter.send(DeepLinkEvent("some URI".toUri()))

            analyticsReporter.send(
                RentCarButtonClickedEvent(
                    isConfirmed = true,
                    nameString = "Audi",
                    time = System.currentTimeMillis(),
                    date = LocalDate.now(),
                )
            )
        }
    }

    private fun getRentCarButtonClickedEvent(): AnalyticsEvent {
        return RentCarButtonClickedEvent(
            isConfirmed = true,
            nameString = "Audi",
            time = System.currentTimeMillis(),
            date = LocalDate.now(),
        )
    }
}

class DeepLinkEvent(url: Uri) : AnalyticsEvent.Kochava.StandardEvent(
    Tracker.Event(Tracker.EVENT_TYPE_DEEP_LINK)
        .setUri(url)
)

@EventName("Activity.Created")
class ActivityOpenedEvent(
    @EventProperty("TIME")
    val time: Long,
) : AnalyticsEvent.Kochava.CustomEvent()

@EventName("Button.SignUp")
class RentCarButtonClickedEvent(
    @EventProperty("IS_CONFIRMED")
    val isConfirmed: Boolean,
    @EventProperty("NAME_STRING")
    val nameString: String,
    @EventProperty("TIME")
    val time: Long,
    @EventProperty("DATE")
    val date: LocalDate,
) : AnalyticsEvent.Iterable.CustomEvent()

