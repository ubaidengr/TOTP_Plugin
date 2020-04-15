@objc(TotpPlugin) class TotpPlugin : CDVPlugin {
  func generate(_ command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let uri = command.arguments[0] as? String ?? ""
    
    if uri.count > 0 {
        
        var code = ""
        
        if let urlc = URLComponents(string: uri) {
            if let otp = OTP(urlc: urlc) {
                if let token = Token(otp: otp, urlc: urlc, load: false) {
                    code = token.codes[0].value
                }
            }
        }
        
        pluginResult = CDVPluginResult(
            status: CDVCommandStatus_OK,
            messageAs: code
        )
    }
    
    self.commandDelegate!.send(
        pluginResult,
        callbackId: command.callbackId
    )
  }
}
