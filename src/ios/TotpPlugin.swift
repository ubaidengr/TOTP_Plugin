@objc(TotpPlugin) class TotpPlugin : CDVPlugin {
  func generate(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let uri = command.arguments[0] as? String ?? ""

    if msg.characters.count > 0 {
      
		var code = ""
	
		if let urlc = URLComponents(string: obj.stringValue!) {
			if let otp = OTP(urlc: urlc) {
                if let token = Token(otp: otp, urlc: urlc, load: load) {
                    code = token!.codes[0].value
                }
            }
		}
		
		pluginResult = CDVPluginResult(
			status: CDVCommandStatus_OK,
			messageAsString: code
		)
    }

    self.commandDelegate!.sendPluginResult(
      pluginResult, 
      callbackId: command.callbackId
    )
  }
}