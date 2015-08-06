package nl.idlework.cordova.plugin.parsepush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class ParseBroadcastReceiver extends BroadcastReceiver {
  private static final String APP_SHARE_KEY = "ParsePushPlugin";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (ParsePushPlugin.destroyed()) {
      SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE);
      String applicationId = sharedPref.getString("applicationId", "");
      String clientKey = sharedPref.getString("clientKey", "");
      Parse.initialize(context.getApplicationContext(), applicationId, clientKey);
      ParseInstallation.getCurrentInstallation().saveInBackground();
    }
  }
}
