package pe.com.redcups.juergapp_android

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder

/**
 * Implementation of App Widget functionality.
 */
class EventsWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
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

    companion object {

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.events_widget)

            val args= Bundle()

            args.putString("event_id", "Evento desde el Widget")

            val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.event_detail_dest)
                .setArguments(args)
                .createPendingIntent()

            views.setOnClickPendingIntent(R.id.event_widget_button, pendingIntent)
            views.setTextViewText(R.id.appwidget_text, "Evento Chevere")
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)


        }
    }
}

