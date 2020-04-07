// Empty constructor
function TotpPlugin() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
TotpPlugin.prototype.generate = function(message, duration, successCallback, errorCallback) {
  var options = {};
  options.message = message;
  options.duration = duration;
  cordova.exec(successCallback, errorCallback, 'TotpPlugin', 'generate', [options]);
}

// Installation constructor that binds TotpPlugin to window
TotpPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.totpPlugin = new TotpPlugin();
  return window.plugins.totpPlugin;
};
cordova.addConstructor(TotpPlugin.install);