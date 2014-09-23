interface IAccounts {

    /**
     * Options to customize emails sent from the Accounts system.
     *
     * @locus Anywhere
     */
    emailTemplates:any;


    /**
     * Set global accounts options.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     */
    config(options:{
               sendVerificationEmail:boolean;
               forbidClientAccountCreation:boolean;
               restrictCreationByEmailDomain:any;
               loginExpirationInDays:Number;
               oauthSecretKey:string
           }):any;


    /**
     * Validate login attempts.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a login is attempted (either successful or unsuccessful).  A login can be aborted by returning a falsy value or throwing an exception.</p>
     */
    validateLoginAttempt(func:Function):any;


    /**
     * Register a callback to be called after a login attempt succeeds.
     *
     * @locus Server
     *
     * @param {function} func - <p>The callback to be called when login is successful.</p>
     */
    onLogin(func:Function):any;


    /**
     * Register a callback to be called after a login attempt fails.
     *
     * @locus Server
     *
     * @param {function} func - <p>The callback to be called after the login has failed.</p>
     */
    onLoginFailure(func:Function):any;


    /**
     * Customize new user creation.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a new user is created. Return the new user object, or throw an <code>Error</code> to abort the creation.</p>
     */
    onCreateUser(func:Function):any;


    /**
     * Set restrictions on new user creation.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a new user is created. Takes the new user object, and returns true to allow the creation or false to abort.</p>
     */
    validateNewUser(func:Function):any;


    /**
     * Create a new user.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     * @param {function} [callback] - <p>Client only, optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    createUser(options:{
                   username:string;
                   email:string;
                   password:string;
                   profile:any
               },
               callback?:Function):any;


    /**
     * Change the current user's password. Must be logged in.
     *
     * @locus Client
     *
     * @param {String} oldPassword - <p>The user's current password. This is <strong>not</strong> sent in plain text over the wire.</p>
     * @param {String} newPassword - <p>A new password for the user. This is <strong>not</strong> sent in plain text over the wire.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    changePassword(oldPassword:string, newPassword:string, callback?:Function):any;


    /**
     * Request a forgot password email.
     *
     * @locus Client
     *
     * @param {Options} options
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    forgotPassword(options:{
                       email:string
                   },
                   callback?:Function):any;


    /**
     * Reset the password for a user using a token received in email. Logs the user in afterwards.
     *
     * @locus Client
     *
     * @param {String} token - <p>The token retrieved from the reset password URL.</p>
     * @param {String} newPassword - <p>A new password for the user. This is <strong>not</strong> sent in plain text over the wire.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    resetPassword(token:string, newPassword:string, callback?:Function):any;


    /**
     * Marks the user's email address as verified. Logs the user in afterwards.
     *
     * @locus Client
     *
     * @param {String} token - <p>The token retrieved from the verification URL.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    verifyEmail(token:string, callback?:Function):any;


    /**
     * Forcibly change the password for a user.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to update.</p>
     * @param {String} newPassword - <p>A new password for the user.</p>
     */
    setPassword(userId:string, newPassword:string):any;


    /**
     * Send an email with a link the user can use to reset their password.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first email in the list.</p>
     */
    sendResetPasswordEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use to set their initial password.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first email in the list.</p>
     */
    sendEnrollmentEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use verify their email address.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first unverified email in the list.</p>
     */
    sendVerificationEmail(userId:string, email?:string):any;

}

declare var Accounts:IAccounts;


interface IEJSON {

    /**
     * Allocate a new buffer of binary data that EJSON can serialize.
     *
     * @locus Anywhere
     */
    newBinary:any;


    /**
     * Add a custom datatype to EJSON.
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>A tag for your custom type; must be unique among custom data types defined in your project, and must match the result of your type's <code>typeName</code> method.</p>
     * @param {function} factory - <p>A function that deserializes a JSON-compatible value into an instance of your type.  This should match the serialization performed by your type's <code>toJSONValue</code> method.</p>
     */
    addType(name:string, factory:Function):any;


    /**
     * Serialize an EJSON-compatible value into its plain JSON representation.
     *
     * @locus Anywhere
     *
     * @param {EJSON} val - <p>A value to serialize to plain JSON.</p>
     */
    toJSONValue(val:any):any;


    /**
     * Deserialize an EJSON value from its plain JSON representation.
     *
     * @locus Anywhere
     *
     * @param {JSONCompatible} val - <p>A value to deserialize into EJSON.</p>
     */
    fromJSONValue(val:any):any;


    /**
     * Serialize a value to a string.
     * 
     * For EJSON values, the serialization fully represents the value. For non-EJSON values, serializes the same way as `JSON.stringify`.
     *
     * @locus Anywhere
     *
     * @param {EJSON} val - <p>A value to stringify.</p>
     * @param {Options} [options]
     */
    stringify(val:any,
              options?:{
                  indent:any;
                  canonical:boolean
              }):any;


    /**
     * Parse a string into an EJSON value. Throws an error if the string is not valid EJSON.
     *
     * @locus Anywhere
     *
     * @param {String} str - <p>A string to parse into an EJSON value.</p>
     */
    parse(str:string):any;


    /**
     * Returns true if `x` is a buffer of binary data, as returned from [`EJSON.newBinary`](#ejson_new_binary).
     *
     * @locus Anywhere
     *
     * @param {Object} x - <p>The variable to check.</p>
     */
    isBinary(x:any):any;


    /**
     * Return true if `a` and `b` are equal to each other.  Return false otherwise.  Uses the `equals` method on `a` if present, otherwise performs a deep comparison.
     *
     * @locus Anywhere
     *
     * @param {EJSON} a
     * @param {EJSON} b
     * @param {Options} [options]
     */
    equals(a:any,
           b:any,
           options?:{
               keyOrderSensitive:boolean
           }):any;


    /**
     * Return a deep copy of `val`.
     *
     * @locus Anywhere
     *
     * @param {EJSON} val - <p>A value to copy.</p>
     */
    clone(val:any):any;

}

declare var EJSON:IEJSON;


interface IMeteor {

    /**
     * A [Mongo.Collection](#collections) containing user documents.
     *
     * @locus Anywhere
     */
    users:any;


    /**
     * Boolean variable.  True if running in client environment.
     *
     * @locus Anywhere
     */
    isClient:boolean;


    /**
     * Boolean variable.  True if running in server environment.
     *
     * @locus Anywhere
     */
    isServer:boolean;


    /**
     * `Meteor.settings` contains deployment-specific configuration options. You can initialize settings by passing the `--settings` option (which takes the name of a file containing JSON data) to `meteor run` or `meteor deploy`. When running your server directly (e.g. from a bundle), you instead specify settings by putting the JSON directly into the `METEOR_SETTINGS` environment variable. If you don't provide any settings, `Meteor.settings` will be an empty object.  If the settings object contains a key named `public`, then `Meteor.settings.public` will be available on the client as well as the server.  All other properties of `Meteor.settings` are only defined on the server.
     *
     * @locus Anywhere
     */
    settings:any;


    /**
     * Boolean variable.  True if running in a Cordova mobile environment.
     *
     * @locus Anywhere
     */
    isCordova:boolean;


    /**
     * `Meteor.release` is a string containing the name of the [release](#meteorupdate) with which the project was built (for example, `"1.2.3"`). It is `undefined` if the project was built using a git checkout of Meteor.
     *
     * @locus Anywhere
     */
    release:any;


    /**
     * Get the current user id, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    userId():any;


    /**
     * True if a login method (such as `Meteor.loginWithPassword`, `Meteor.loginWithFacebook`, or `Accounts.createUser`) is currently in progress. A reactive data source.
     *
     * @locus Client
     */
    loggingIn():any;


    /**
     * Get the current user record, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    user():any;


    /**
     * Log the user out.
     *
     * @locus Client
     *
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    logout(callback?:Function):any;


    /**
     * Log out other clients logged in as the current user, but does not log out the client that calls this function.
     *
     * @locus Client
     *
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    logoutOtherClients(callback?:Function):any;


    /**
     * Log the user in with a password.
     *
     * @locus Client
     *
     * @param {Object or String} user - <p>Either a string interpreted as a username or an email; or an object with a single key: <code>email</code>, <code>username</code> or <code>id</code>.</p>
     * @param {String} password - <p>The user's password.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    loginWithPassword(user:any, password:string, callback?:Function):any;


    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - <p>Name of the subscription.  Matches the name of the server's <code>publish()</code> call.</p>
     * @param {Any} [arg1, arg2...] - <p>Optional arguments passed to publisher function on server.</p>
     * @param {function or Object} [callbacks] - <p>Optional. May include <code>onError</code> and <code>onReady</code> callbacks. If a function is passed instead of an object, it is interpreted as an <code>onReady</code> callback.</p>
     */
    subscribe(name:string, ...args:any[]):any;


    /**
     * Invokes a method passing any number of arguments.
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>Name of method to invoke</p>
     * @param {EJSONable} [arg1, arg2...] - <p>Optional method arguments</p>
     * @param {function} [asyncCallback] - <p>Optional callback, which is called asynchronously with the error or result after the method is complete. If not provided, the method runs synchronously if possible (see below).</p>
     */
    call(name:string, ...args:any[]):any;


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
    apply(name:string,
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
    status():any;


    /**
     * Force an immediate reconnection attempt if the client is not connected to the server.
     * 
     *   This method does nothing if the client is already connected.
     *
     * @locus Client
     */
    reconnect():any;


    /**
     * Disconnect the client from the server.
     *
     * @locus Client
     */
    disconnect():any;


    /**
     * Register a callback to be called when a new DDP connection is made to the server.
     *
     * @locus Server
     *
     * @param {function} callback - <p>The function to call when a new DDP connection is established.</p>
     */
    onConnection(callback:Function):any;


    /**
     * Publish a record set.
     *
     * @locus Server
     *
     * @param {String} name - <p>Name of the record set.  If <code>null</code>, the set has no name, and the record set is automatically sent to all connected clients.</p>
     * @param {function} func - <p>Function called on the server each time a client subscribes.  Inside the function, <code>this</code> is the publish handler object, described below.  If the client passed arguments to <code>subscribe</code>, the function is called with the same arguments.</p>
     */
    publish(name:string, func:Function):any;


    /**
     * Defines functions that can be invoked over the network by clients.
     *
     * @locus Anywhere
     *
     * @param {Object} methods - <p>Dictionary whose keys are method names and values are functions.</p>
     */
    methods(methods:any):any;


    /**
     * Wrap a function that takes a callback function as its final parameter so that the wrapper function can be used either synchronously (without passing a callback) or asynchronously (when a callback is passed). If a callback is provided, the environment captured when the original function was called will be restored in the callback.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>A function that takes a callback as its final parameter</p>
     * @param {Object} [context] - <p>Optional <code>this</code> object against which the original function will be invoked</p>
     */
    wrapAsync(func:Function, context?:any):any;


    /**
     * Run code when a client or a server starts.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>A function to run on startup.</p>
     */
    startup(func:Function):any;


    /**
     * Call a function in the future after waiting for a specified delay.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>The function to run</p>
     * @param {Number} delay - <p>Number of milliseconds to wait before calling function</p>
     */
    setTimeout(func:Function, delay:Number):any;


    /**
     * Call a function repeatedly, with a time delay between calls.
     *
     * @locus Anywhere
     *
     * @param {function} func - <p>The function to run</p>
     * @param {Number} delay - <p>Number of milliseconds to wait between each function call.</p>
     */
    setInterval(func:Function, delay:Number):any;


    /**
     * Cancel a repeating function call scheduled by `Meteor.setInterval`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - <p>The handle returned by <code>Meteor.setInterval</code></p>
     */
    clearInterval(id:Number):any;


    /**
     * Cancel a function call scheduled by `Meteor.setTimeout`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - <p>The handle returned by <code>Meteor.setTimeout</code></p>
     */
    clearTimeout(id:Number):any;


    /**
     * Generate an absolute URL pointing to the application. The server reads from the `ROOT_URL` environment variable to determine where it is running. This is taken care of automatically for apps deployed with `meteor deploy`, but must be provided when using `meteor bundle`.
     *
     * @locus Anywhere
     *
     * @param {String} [path] - <p>A path to append to the root URL. Do not include a leading &quot;<code>/</code>&quot;.</p>
     * @param {Options} [options]
     */
    absoluteUrl(path?:string,
                options?:{
                    secure:boolean;
                    replaceLocalhost:boolean;
                    rootUrl:string
                }):any;

}

declare var Meteor:IMeteor;


interface IAssets {

    /**
     * Retrieve the contents of the static server asset as a UTF8-encoded string.
     *
     * @locus Server
     *
     * @param {String} assetPath - <p>The path of the asset, relative to the application's <code>private</code> subdirectory.</p>
     * @param {function} [asyncCallback] - <p>Optional callback, which is called asynchronously with the error or result after the function is complete. If not provided, the function runs synchronously.</p>
     */
    getText(assetPath:string, asyncCallback?:Function):any;


    /**
     * Retrieve the contents of the static server asset as an [EJSON Binary](#ejson_new_binary).
     *
     * @locus Server
     *
     * @param {String} assetPath - <p>The path of the asset, relative to the application's <code>private</code> subdirectory.</p>
     * @param {function} [asyncCallback] - <p>Optional callback, which is called asynchronously with the error or result after the function is complete. If not provided, the function runs synchronously.</p>
     */
    getBinary(assetPath:string, asyncCallback?:Function):any;

}

declare var Assets:IAssets;


interface IPackage {

    /**
     * Provide basic package information.
     *
     * @locus package.js
     *
     * @param {Options} options
     */
    describe(options:{
                 summary:string;
                 version:string;
                 name:string;
                 git:string
             }):any;


    /**
     * Define package dependencies and expose package methods.
     *
     * @locus package.js
     *
     * @param {function} func - <p>A function that takes in the package control 'api' object, which keeps track of dependencies and exports.</p>
     */
    onUse(func:Function):any;


    /**
     * Define dependencies and expose package methods for unit tests.
     *
     * @locus package.js
     *
     * @param {function} func - <p>A function that takes in the package control 'api' object, which keeps track of dependencies and exports.</p>
     */
    onTest(func:Function):any;

}

declare var Package:IPackage;


interface INpm {

    /**
     * Specify which [NPM](https://www.npmjs.org/) packages
     * your Meteor package depends on.
     *
     * @locus package.js
     *
     * @param {Object} dependencies - <p>An object where the keys are package
     * names and the values are version numbers in string form.
     * You can only depend on exact versions of NPM packages. Example:</p>
     * <pre class="prettyprint source lang-js"><code>Npm.depends({moment: &quot;2.8.3&quot;});</code></pre>
     */
    depends(dependencies:any):any;


    /**
     * Require a package that was specified using
     * `Npm.depends()`.
     *
     * @locus Server
     *
     * @param {String} name - <p>The name of the package to require.</p>
     */
    require(name:string):any;

}

declare var Npm:INpm;


interface ICordova {

    /**
     * Specify which [Cordova / PhoneGap](http://cordova.apache.org/)
     * plugins your Meteor package depends on.
     * 
     * Plugins are installed from
     * [plugins.cordova.io](http://plugins.cordova.io/), so the plugins and
     * versions specified must exist there. Alternatively, the version
     * can be replaced with a GitHub tarball URL as described in the
     * [Cordova / PhoneGap](https://github.com/meteor/meteor/wiki/Meteor-Cordova-Phonegap-integration#meteor-packages-with-cordovaphonegap-dependencies)
     * page of the Meteor wiki on GitHub.
     *
     * @locus package.js
     *
     * @param {Object} dependencies - <p>An object where the keys are plugin
     * names and the values are version numbers or GitHub tarball URLs
     * in string form.
     * Example:</p>
     * <pre class="prettyprint source lang-js"><code>Cordova.depends({
     *   &quot;org.apache.cordova.camera&quot;: &quot;0.3.0&quot;
     * });</code></pre><p>Alternatively, with a GitHub URL:</p>
     * <pre class="prettyprint source lang-js"><code>Cordova.depends({
     *   &quot;org.apache.cordova.camera&quot;:
     *     &quot;https://github.com/apache/cordova-plugin-camera/tarball/d84b875c&quot;
     * });</code></pre>
     */
    depends(dependencies:any):any;

}

declare var Cordova:ICordova;