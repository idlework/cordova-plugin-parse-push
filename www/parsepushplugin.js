'use strict';

function initialize (callback, applicationId, clientKey) {
  _callNativeFunction('initialize', callback, [applicationId, clientKey]);
}

function subscribe (callback, channel) {
  _callNativeFunction('subscribe', [channel]);
}

function unsubscribe (callback, channel) {
  _callNativeFunction('unsubscribe', [channel]);
}

function _callNativeFunction (callback, name, params) {
  cordova.exec(callback, callback, 'ParsePushPlugin', name, params);
}

module.exports = {
  initialize: initialize,
  subscribe: subscribe,
  unsubscribe: unsubscribe
};
