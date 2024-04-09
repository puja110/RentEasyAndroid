package com.example.renteasyandroid.feature.widget

import android.R
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.renteasyandroid.feature.auth.login.LoginActivity


/**
 * Implementation of App Widget functionality.
 */
class RentEasyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, LoginActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    // Get the layout for the App Widget and attach an on-click listener to the button
    val views = RemoteViews(context.packageName, com.example.renteasyandroid.R.layout.rent_easy_widget)
    views.setOnClickPendingIntent(com.example.renteasyandroid.R.id.appwidget_text, pendingIntent)
}