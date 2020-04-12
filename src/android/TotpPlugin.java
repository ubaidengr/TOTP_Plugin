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
      String uri;
      try {
        JSONObject options = args.getJSONObject(0);
        uri = options.getString("uri");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }
      
      // Send a positive result to the callbackContext
	  Token token = null;
        try {
            token = new Token(uri);
        } catch (Token.TokenUriInvalidException e) {
            e.printStackTrace();
        }
	  String code = "";
	  if (token != null) {
		  code = token.generateCodes().getCurrentCode();
		  callbackContext.success(code);
	  }
	  else { 
		  code = "Invalid code";
		  Toast toast = Toast.makeText(cordova.getActivity(), code, Toast.LENGTH_LONG);
		  // Display toast
		  toast.show();
	  }
	  
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      callbackContext.sendPluginResult(pluginResult);
      return true;
  }

}