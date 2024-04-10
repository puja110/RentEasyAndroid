package com.example.renteasyandroid.feature.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.renteasyandroid.feature.auth.login.LoginActivity


/**
 * Implementation of an AppWidgetProvider for the RentEasy widget.
 * This class handles the behavior of the widget, such as updating its appearance and responding to user interactions.
 */
class RentEasyWidget : AppWidgetProvider() {

    /**
     * Called when the AppWidgetProvider is being updated.
     * This method is responsible for updating all instances of the widget when triggered.
     */
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

    /**
     * Called when the first instance of the widget is created.
     * Enter relevant functionality for when the first widget is created.
     */
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    /**
     * Called when the last instance of the widget is disabled.
     * Enter relevant functionality for when the last widget is disabled.
     */
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

/**
 * Function to update the App Widget's appearance and behavior.
 * This function is called to update a specific instance of the widget.
 */
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Create an intent to launch the LoginActivity when the widget is clicked
    val intent = Intent(context, LoginActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    // Get the layout for the App Widget and attach an on-click listener to the button
    val views =
        RemoteViews(context.packageName, com.example.renteasyandroid.R.layout.rent_easy_widget)
    views.setOnClickPendingIntent(com.example.renteasyandroid.R.id.appwidget_text, pendingIntent)
    // Update the App Widget with the modified views
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
