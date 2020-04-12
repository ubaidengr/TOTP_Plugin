package com.ubaid.cordova.plugin;
// The native Toast API
import android.widget.Toast;
// Cordova-required packages
import com.ubaid.cordova.plugin.Token;
import android.Manifest;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.content.IntentFilter;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class TotpPlugin extends CordovaPlugin {
  private static final String DURATION_LONG = "long";
  private final int PERMISSIONS_REQUEST_CAMERA = 1;
  
  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      // Verify that the user sent a 'show' action
      if (!action.equals("generate")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }
      String message;
      String duration;
      try {
        JSONObject options = args.getJSONObject(0);
        message = options.getString("message");
        duration = options.getString("duration");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }
      // Create the toast
      Toast toast = Toast.makeText(cordova.getActivity(), message,
        DURATION_LONG.equals(duration) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      // Display toast
      toast.show();
      // Send a positive result to the callbackContext
	  Token token = null;
        try {
            String uri = "otpauth://totp/ubaid.engr%40yahoo.com?secret=2y53rhvk4zqiyauigjnz5vrcsv5cqznd2th5a4umqk6ncnecis2afopr&algorithm=SHA1&digits=6&period=30";
            token = new Token(uri);
        } catch (Token.TokenUriInvalidException e) {
            e.printStackTrace();
        }
		String code = "";
	  if (token != null) {
		  code = token.generateCodes().getCurrentCode();
	  }
	  else { 
		  code = "Invalid code";
	  }
	  
	  message = "Code generated: " + code;
	  
	  toast = Toast.makeText(cordova.getActivity(), message,
        DURATION_LONG.equals(duration) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
      // Display toast
      toast.show();
	  
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      callbackContext.sendPluginResult(pluginResult);
      return true;
  }

}