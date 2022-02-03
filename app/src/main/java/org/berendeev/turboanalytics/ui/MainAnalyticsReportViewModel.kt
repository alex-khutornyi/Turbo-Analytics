package org.berendeev.turboanalytics.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import org.berendeev.turboanalytics.framework.AnalyticsReportViewModel
import org.berendeev.turboanalytics.framework.AnalyticsReporter
import org.berendeev.turboanalytics.framework.service.report.AnalyticsReport
import org.berendeev.turboanalytics.framework.service.report.GeneralReport
import org.berendeev.turboanalytics.ui.MainAnalyticsReportViewModel.FabButtonClicksReport
import javax.inject.Inject

@Deprecated("Should be removed later, since ViewModel scope is too small. Use AnalyticsStorage.kt instead")
@HiltViewModel
class MainAnalyticsReportViewModel @Inject constructor(
    analyticsReporter: AnalyticsReporter,
) : AnalyticsReportViewModel<FabButtonClicksReport>(
    analyticsReporter,
) {

    override val defaultReport: FabButtonClicksReport = FabButtonClicksReport(clickCount = 0)

    @GeneralReport.Name("Main.Fab.Button.Click")
    data class FabButtonClicksReport(

        @GeneralReport.Property("ClicksCount")
        val clickCount: Int = 0,

    ) : AnalyticsReport.Forter.General()
}