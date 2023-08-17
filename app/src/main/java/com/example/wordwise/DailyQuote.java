package com.example.wordwise;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class DailyQuote extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.daily_quote);

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://api.quotable.io/quotes/random", null, response -> {
            try {
                JSONObject object = response.getJSONObject(0);
                views.setTextViewText(R.id.quote_of_the_day, object.getString("content").trim());
                views.setTextViewText(R.id.author_name, "~" + object.getString("author").trim() + "~");
                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> Toast.makeText(context, "api call error!!!", Toast.LENGTH_SHORT).show());

        queue.add(jsonArrayRequest);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}