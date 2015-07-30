package nl.idlework.cordova.plugin.parsepush;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.parse.*;

import android.content.SharedPreferences;

public class ParsePushPlugin extends CordovaPlugin {
	private static final String APP_SHARE_KEY = "ParsePushPlugin";
  private static boolean destroyed;

	private CallbackContext _callbackContext;

  @Override
  public void onDestroy() {
    super.onDestroy();
    destroyed = true;
  }
  
  public static boolean destroyed() {
  	return destroyed;
  }
    
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    switch (actions) {
      case "initialize":
          _initialize(args, callbackContext);
          return true;
        break;
      case "subscribe":
          _subscribe(args, callbackContext);
          return true;
        break;
      case "unsubscribe":
          _unsubscribe(args, callbackContext);
          return true;
        break;
      default:
        // Returning false results in a "MethodNotFound" error.
        return false; 
    }
	}
	
	private void _initialize(JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String applicationId = args.getString(0);
		final String clientKey = args.getString(1);

		_callbackContext = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_threadedInitialize(applicationId, clientKey);
			}
		});
	}

	private void _subscribe(JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String channel = args.getString(0);
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_threadedSubscribe(channel);
			}
		});
	}

	private void _unsubscribe(JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String channel = args.getString(0);
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_threadedUnsubscribe(channel);
			}
		});
	}
	
  private void _threadedInitialize(String applicationId, String clientKey) {
    try {
      Parse.initialize(cordova.getActivity(), applicationId, clientKey);
	   	ParseInstallation.getCurrentInstallation().save();

			SharedPreferences sharedPref = cordova.getActivity().getSharedPreferences(APP_SHARE_KEY, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("applicationId", applicationId);
			editor.putString("clientKey", clientKey);
			editor.commit();
		
			_sendResult(PluginResult.Status.OK, "initializeSucces");
    } catch (ParseException e) {
			_sendResult(PluginResult.Status.ERROR, "initializeFailed");
    }
  }

  private void _threadedSubscribe(String channel) {
    ParsePush.subscribeInBackground(channel, new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
      		_sendResult(PluginResult.Status.OK, "subscribeSucces");
        } else {
        	_sendResult(PluginResult.Status.ERROR, "subscribeFailed");
        }
      }
    });
  }

  private void _threadedUnsubscribe(String channel) {
    ParsePush.unsubscribeInBackground(channel, new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
      		_sendResult(PluginResult.Status.OK, "unsubscribeSucces");
        } else {
      		_sendResult(PluginResult.Status.ERROR, "unsubscribeFailed");
        }
      }
    });
  }

  private void _sendResult(String status, String type) {
    PluginResult result = new PluginResult(status, type);
    result.setKeepCallback(true);
    _callbackContext.sendPluginResult(result);
  }
}
