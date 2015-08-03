'use strict';

function initialize (applicationId, clientKey) {
  _callNativeFunction('initialize', [applicationId, clientKey]);
}

function subscribe (channel) {
  _callNativeFunction('subscribe', [channel]);
}

function unsubscribe (channel) {
  _callNativeFunction('unsubscribe', [channel]);
}

function _callNativeFunction (name, params) {
  cordova.exec(_triggerEvent, _triggerEvent, 'ParsePushPlugin', name, params);
}

function _triggerEvent (key) {
  if (typeof result !== 'string') {
    return;
  }

  this.trigger(type);
}

module.exports = {
  initialize: initialize,
  subscribe: subscribe,
  unsubscribe: unsubscribe
};
