declare module Meteor {

    /**
     * A [Mongo.Collection](#collections) containing user documents.
     *
     * @locus Anywhere
     */
    var users:any;


    /**
     * Boolean variable.  True if running in client environment.
     *
     * @locus Anywhere
     */
    var isClient:boolean;


    /**
     * Boolean variable.  True if running in server environment.
     *
     * @locus Anywhere
     */
    var isServer:boolean;


    /**
     * `Meteor.settings` contains deployment-specific configuration options. You can initialize settings by passing the `--settings` option (which takes the name of a file containing JSON data) to `meteor run` or `meteor deploy`. When running your server directly (e.g. from a bundle), you instead specify settings by putting the JSON directly into the `METEOR_SETTINGS` environment variable. If you don't provide any settings, `Meteor.settings` will be an empty object.  If the settings object contains a key named `public`, then `Meteor.settings.public` will be available on the client as well as the server.  All other properties of `Meteor.settings` are only defined on the server.
     *
     * @locus Anywhere
     */
    var settings:any;


    /**
     * Boolean variable.  True if running in a Cordova mobile environment.
     *
     * @locus Anywhere
     */
    var isCordova:boolean;


    /**
     * `Meteor.release` is a string containing the name of the [release](#meteorupdate) with which the project was built (for example, `"1.2.3"`). It is `undefined` if the project was built using a git checkout of Meteor.
     *
     * @locus Anywhere
     */
    var release:any;


    /**
     * Get the current user id, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    function userId():any;


    /**
     * True if a login method (such as `Meteor.loginWithPassword`, `Meteor.loginWithFacebook`, or `Accounts.createUser`) is currently in progress. A reactive data source.
     *
     * @locus Client
     */
    function loggingIn():any;


    /**
     * Get the current user record, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    function user():any;


    /**
     * Log the user out.
     *
     * @locus Client
     *
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function logout(callback?:Function):any;


    /**
     * Log out other clients logged in as the current user, but does not log out the client that calls this function.
     *
     * @locus Client
     *
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function logoutOtherClients(callback?:Function):any;


    /**
     * Log the user in with a password.
     *
     * @locus Client
     *
     * @param {Object or String} user - <p>Either a string interpreted as a username or an email; or an object with a single key: <code>email</code>, <code>username</code> or <code>id</code>.</p>
     * @param {String} password - <p>The user's password.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function loginWithPassword(user:any, password:string, callback?:Function):any;


    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - <p>Name of the subscription.  Matches the name of the server's <code>publish()</code> call.</p>
     * @param {Any} [arg1, arg2...] - <p>Optional arguments passed to publisher function on server.</p>
     * @param {function or Object} [callbacks] - <p>Optional. May include <code>onError</code> and <code>onReady</code> callbacks. If a function is passed instead of an object, it is interpreted as an <code>onReady</code> callback.</p>
     */
    function subscribe(name:string, ...args:any[]):any;


    /**
     * Invokes a method passing any number of arguments.
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>Name of method to invoke</p>
     * @param {EJSONable} [arg1, arg2...] - <p>Optional method arguments</p>
     * @param {function} [asyncCallback] - <p>Optional callback, which is called asynchronously with the error or result after the method is complete. If not provided, the method runs synchronously if possible (see below).</p>
     */
    function call(name:string, ...args:any[]):any;


    /**
     * Invoke a method passing an array of arguments.
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>Name of method to invoke</p>
     * @param {Array.<EJSONable>} args - <p>Method arguments</p>
     * @param {Options} [options]
     * @param {function} [asyncCallback] - <p>Optional callback; same semantics as in <a href="#meteor_call"><code>Meteor.call</code></a>.</p>
     */
    function apply(name:string,
          args:any,
          options?:{
              wait:boolean;
              onResultReceived:Function
          },
          asyncCallback?:Function):any;


    /**
     * Get the current connection status. A reactive data source.
     *
     * @locus Client
     */
    function status():any;


    /**
     * Force an immediate reconnection attempt if the client is not connected to the server.
     * 
     *   This method does nothing if the client is already connected.
     *
     * @locus Client
     */
    function reconnect():any;


    /**
     * Disconnect the client from the server.
     *
     * @locus Client
     */
    function disconnect():any;


    /**
     * Register a callback to be called when a new DDP connection is made to the server.
     *
     * @locus Server
     *
     * @param {function} callback - <p>The function to call when a new DDP connection is established.</p>
     */
    function onConnection(callback:Function):any;


    /**
     * Publish a record set.
     *
     * @locus Server
     *
     * @param {String} name - <p>Name of the record set.  If <code>null</code>, the set has no name, and the record set is automatically sent to all connected clients.</p>
     * @param {function} func - <p>Function called on the server each time a client subscribes.  Inside the function, <code>this</code> is the publish handler object, described below.  If the client passed arguments to <code>subscribe</code>, the function is called with the same arguments.</p>
     */
    function publish(name:string, func:Function):any;


    /**
     * Defines functions that can be invoked over the network by clients.
     *
     * @locus Anywhere
     *
     * @param {Object} methods - <p>Dictionary whose keys are method names and values are functions.</p>
     */
    function methods(methods:any):any;


    /**
     * Wrap a function that takes a callback function as its final parameter so that the wrapper function can be used either synchronously (without passing a callback) or asynchronously (when a callback is passed). If a callback is provided, the environment captured when the original function was called will be restored in the callback.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>A function that takes a callback as its final parameter</p>
     * @param {Object} [context] - <p>Optional <code>this</code> object against which the original function will be invoked</p>
     */
    function wrapAsync(func:Function, context?:any):any;


    /**
     * Run code when a client or a server starts.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>A function to run on startup.</p>
     */
    function startup(func:Function):any;


    /**
     * Call a function in the future after waiting for a specified delay.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>The function to run</p>
     * @param {Number} delay - <p>Number of milliseconds to wait before calling function</p>
     */
    function setTimeout(func:Function, delay:Number):any;


    /**
     * Call a function repeatedly, with a time delay between calls.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>The function to run</p>
     * @param {Number} delay - <p>Number of milliseconds to wait between each function call.</p>
     */
    function setInterval(func:Function, delay:Number):any;


    /**
     * Cancel a repeating function call scheduled by `Meteor.setInterval`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - <p>The handle returned by <code>Meteor.setInterval</code></p>
     */
    function clearInterval(id:Number):any;


    /**
     * Cancel a function call scheduled by `Meteor.setTimeout`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - <p>The handle returned by <code>Meteor.setTimeout</code></p>
     */
    function clearTimeout(id:Number):any;


    /**
     * Generate an absolute URL pointing to the application. The server reads from the `ROOT_URL` environment variable to determine where it is running. This is taken care of automatically for apps deployed with `meteor deploy`, but must be provided when using `meteor bundle`.
     *
     * @locus Anywhere
     *
     * @param {String} [path] - <p>A path to append to the root URL. Do not include a leading &quot;<code>/</code>&quot;.</p>
     * @param {Options} [options]
     */
    function absoluteUrl(path?:string,
                options?:{
                    secure:boolean;
                    replaceLocalhost:boolean;
                    rootUrl:string
                }):any;


    /**
     * This class represents a symbolic error thrown by a method.
     *
     * @locus Anywhere
     *
     * @param {Number} error - <p>A numeric error code, likely similar to an HTTP code (eg, 404, 500).</p>
     * @param {String} [reason] - <p>Optional.  A short human-readable summary of the error, like 'Not Found'.</p>
     * @param {String} [details] - <p>Optional.  Additional information about the error, like a textual stack trace.</p>
     */
    function Error(error:Number, reason?:string, details?:string):Error;

    interface Error {

    }


}