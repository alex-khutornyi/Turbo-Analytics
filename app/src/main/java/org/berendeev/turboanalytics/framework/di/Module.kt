package org.berendeev.turboanalytics.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.berendeev.turboanalytics.framework.AnalyticsReporter
import org.berendeev.turboanalytics.framework.AnalyticsReporterImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {

    @Binds
    @Singleton
    internal abstract fun provideAnalyticsReporter(bind: AnalyticsReporterImpl): AnalyticsReporter

    companion object {

    }

}