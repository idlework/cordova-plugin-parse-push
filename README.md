Cordova Parse Push plugin
=========================
Project is still in development and not ready for use...

# Overview #
Requires parse account http://parse.com

Parse android SDK (v1.9.4)
Parse ios SDK (v1.7.5)

# Install plugin
```c
cordova plugin add https://github.com/idlework/cordova-plugin-parse-push
```

# Setup
```javascript
var applicationId = "${APPLICATION_ID}";
var clientKey = "${CLIENT_KEY}";

document.addEventListener('deviceready', _deviceReady);

function _deviceReady () {
	_initParsePush();
}

function _initParsePush () {
	if (window.parsePush) {
		window.parsePush.on('initializeSucces', _initializeSucces);
		window.parsePush.on('initializeFailed', _initializeFailed);
		window.parsePush.initialize(applicationId, clientKey);
	}
}

function _initializeSucces() {
	alert('initialize succes!');
}

function _initializeFailed() {
	alert('initialize failed!');
}
```

#Interface
```javascript
window.parsePush.on('initializeSucces', function);
window.parsePush.on('initializeFailed', function);
window.parsePush.on('subscribeSucces', function);
window.parsePush.on('subscribeFailed', function);
window.parsePush.on('unsubscribeSucces', function);
window.parsePush.on('unsubscribeFailed', function);

window.parsePush.initialize(${applicationId}, ${clientKey});
window.parsePush.subscribe(${channel});
window.parsePush.unsubscribe(${channel});
```