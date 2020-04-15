@objc(TotpPlugin) class TotpPlugin : CDVPlugin {
  func generate(_ command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    var uri = ""
    if let url = command.arguments[0] as? [String: String] {
        uri = url["uri"]!
    }
    
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
