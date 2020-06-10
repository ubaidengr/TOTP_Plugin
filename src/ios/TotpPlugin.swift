@objc(TotpPlugin) class TotpPlugin : CDVPlugin {
  @objc func generate(_ command: CDVInvokedUrlCommand) {
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
            if let token = TokenStore().add(urlc) {
                code = token.codes[0].value
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
