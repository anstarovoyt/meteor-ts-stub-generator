interface ITemplate {

}

declare var Template:ITemplate;


declare module Meteor {

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
     * Run code when a client or a server starts.
     *
     * @locus Anywhere
     *
     * @param {Function} func - A function to run on startup.
     */
    function startup(func:Function):void;


    /**
     * Generate an absolute URL pointing to the application. The server reads from the `ROOT_URL` environment variable to determine where it is running. This is taken care of automatically for apps deployed with `meteor deploy`, but must be provided when using `meteor bundle`.
     *
     * @locus Anywhere
     *
     * @param {String} [path] - A path to append to the root URL. Do not include a leading "`/`".
     * @param {Options} [options]
     */
    function absoluteUrl(path?:string,
                options?:{
                    secure?:boolean;
                    replaceLocalhost?:boolean;
                    rootUrl?:string
                }):string;


    /**
     * `Meteor.settings` contains deployment-specific configuration options. You can initialize settings by passing the `--settings` option (which takes the name of a file containing JSON data) to `meteor run` or `meteor deploy`. When running your server directly (e.g. from a bundle), you instead specify settings by putting the JSON directly into the `METEOR_SETTINGS` environment variable. If you don't provide any settings, `Meteor.settings` will be an empty object.  If the settings object contains a key named `public`, then `Meteor.settings.public` will be available on the client as well as the server.  All other properties of `Meteor.settings` are only defined on the server.
     *
     * @locus Anywhere
     */
    var settings:any;


    /**
     * `Meteor.release` is a string containing the name of the [release](#meteorupdate) with which the project was built (for example, `"something"`). It is `undefined` if the project was built using a git checkout of Meteor.
     *
     * @locus Anywhere
     */
    var release:string;


    /**
     * Publish a record set.
     *
     * @locus Server
     *
     * @param {String} name - Name of the record set.  If `null`, the set has no name, and the record set is automatically sent to all connected clients.
     * @param {Function} func - Function called on the server each time a client subscribes.  Inside the function, `this` is the publish handler object, described below.  If the client passed arguments to `subscribe`, the function is called with the same arguments.
     */
    function publish(name:string, func:Function):string;


    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - Name of the subscription.  Matches the name of the server's `publish()` call.
     * @param {Any} [arg1, arg2, ...] - Optional arguments passed to publisher function on server.
     * @param {Function or Object} [callbacks] - Optional. May include `onError` and `onReady` callbacks. If a function is passed instead of an object, it is interpreted as an `onReady` callback.
     */
    function subscribe(name:string, ...args:any[]):any;


    /**
     * Defines functions that can be invoked over the network by clients.
     *
     * @locus Anywhere
     *
     * @param {Object} methods - Dictionary whose keys are method names and values are functions.
     */
    function methods(methods:any):any;


    /**
     * Invokes a method passing any number of arguments.
     *
     * @locus Anywhere
     *
     * @param {String} name - Name of method to invoke
     * @param {EJSON} param1, param2, ... - Optional method arguments
     * @param {Function} [asyncCallback] - Optional callback, which is called asynchronously with the error or result after the method is complete. If not provided, the method runs synchronously if possible (see below).
     */
    function call(name:string, ...params:any[]):any;


    /**
     * Invoke a method passing an array of arguments.
     *
     * @locus Anywhere
     *
     * @param {String} name - Name of method to invoke
     * @param {Array} params - Method arguments
     * @param {Options} [options]
     * @param {Function} [asyncCallback] - Optional callback; same semantics as in [`Meteor.call`](#meteor_call).
     */
    function apply(name:string,
          params:any,
          options?:{
              wait?:boolean;
              onResultReceived?:Function
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
     * This method does nothing if the client is already connected.
     *
     * @locus Client
     */
    function reconnect():void;


    /**
     * Disconnect the client from the server.
     *
     * @locus Client
     */
    function disconnect():void;


    /**
     * Register a callback to be called when a new DDP connection is made to the server.
     *
     * @locus Server
     *
     * @param {function} callback - The function to call when a new DDP connection is established.
     */
    function onConnection(callback:Function):any;


    /**
     * Get the current user record, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    function user():any;


    /**
     * Get the current user id, or `null` if no user is logged in. A reactive data source.
     *
     * @locus Anywhere but publish functions
     */
    function userId():any;


    /**
     * A [Meteor.Collection](#collections) containing user documents.
     *
     * @locus Anywhere
     */
    var users:any;


    /**
     * True if a login method (such as `Meteor.loginWithPassword`, `Meteor.loginWithFacebook`, or `Accounts.createUser`) is currently in progress. A reactive data source.
     *
     * @locus Client
     */
    function loggingIn():any;


    /**
     * Log the user out.
     *
     * @locus Client
     *
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    function logout(callback?:Function):any;


    /**
     * Log out other clients logged in as the current user, but does not log out the client that calls this function.
     *
     * @locus Client
     *
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    function logoutOtherClients(callback?:Function):any;


    /**
     * Log the user in with a password.
     *
     * @locus Client
     *
     * @param {Object or String} user - Either a string interpreted as a username or an email; or an object with a single key: `email`, `username` or `id`.
     * @param {String} password - The user's password. This is __not__ sent in plain text over the wire &mdash; it is secured with [SRP](http://en.wikipedia.org/wiki/Secure_Remote_Password_protocol).
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    function loginWithPassword(user:any, password:string, callback?:Function):any;


    /**
     * Call a function in the future after waiting for a specified delay.
     *
     * @locus Anywhere
     *
     * @param {Function} func - The function to run
     * @param {Number} delay - Number of milliseconds to wait before calling function
     */
    function setTimeout(func:Function, delay:Number):any;


    /**
     * Call a function repeatedly, with a time delay between calls.
     *
     * @locus Anywhere
     *
     * @param {Function} func - The function to run
     * @param {Number} delay - Number of milliseconds to wait between each function call.
     */
    function setInterval(func:Function, delay:Number):any;


    /**
     * Cancel a function call scheduled by `Meteor.setTimeout`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - The handle returned by `Meteor.setTimeout`
     */
    function clearTimeout(id:Number):any;


    /**
     * Cancel a repeating function call scheduled by `Meteor.setInterval`.
     *
     * @locus Anywhere
     *
     * @param {Number} id - The handle returned by `Meteor.setInterval`
     */
    function clearInterval(id:Number):any;

    interface Collection {

        /**
         * Find the documents in a collection that match the selector.
         *
         * @locus Anywhere
         *
         * @param {Mongo selector (Object or String)} selector - The query
         * @param {Options} [options]
         */
        find(selector:any,
             options?:{
                 sort?:any;
                 skip?:Number;
                 limit?:Number;
                 fields?:any;
                 reactive?:boolean;
                 transform?:Function
             }):MeteorCursor;


        /**
         * Finds the first document that matches the selector, as ordered by sort and skip options.
         *
         * @locus Anywhere
         *
         * @param {Mongo selector (Object or String)} selector - The query
         * @param {Options} [options]
         */
        findOne(selector:any,
                options?:{
                    sort?:any;
                    skip?:Number;
                    fields?:any;
                    reactive?:boolean;
                    transform?:Function
                }):any;


        /**
         * Insert a document in the collection.  Returns its unique _id.
         *
         * @locus Anywhere
         *
         * @param {Object} doc - The document to insert. May not yet have an _id attribute, in which case Meteor will generate one for you.
         * @param {Function} [callback] - Optional.  If present, called with an error object as the first argument and, if no error, the _id as the second.
         */
        insert(doc:any, callback?:Function):any;


        /**
         * Modify one or more documents in the collection. Returns the number of affected documents.
         *
         * @locus Anywhere
         *
         * @param {Mongo selector, or object id} selector - Specifies which documents to modify
         * @param {Mongo modifier} modifier - Specifies how to modify the documents
         * @param {Options} [options]
         * @param {Function} [callback] - Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.
         */
        update(selector:any,
               modifier:any,
               options?:{
                   multi?:boolean;
                   upsert?:boolean
               },
               callback?:Function):any;


        /**
         * Modify one or more documents in the collection, or insert one if no matching documents were found. Returns an object with keys `numberAffected` (the number of documents modified)  and `insertedId` (the unique _id of the document that was inserted, if any).
         *
         * @locus Anywhere
         *
         * @param {Mongo selector, or object id} selector - Specifies which documents to modify
         * @param {Mongo modifier} modifier - Specifies how to modify the documents
         * @param {Options} [options]
         * @param {Function} [callback] - Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.
         */
        upsert(selector:any,
               modifier:any,
               options?:{
                   multi?:boolean
               },
               callback?:Function):any;


        /**
         * Remove documents from the collection
         *
         * @locus Anywhere
         *
         * @param {Mongo selector, or object id} selector - Specifies which documents to remove
         * @param {Function} [callback] - Optional.  If present, called with an error object as its argument.
         */
        remove(selector:any, callback?:Function):any;


        /**
         * Allow users to write directly to this collection from client code, subject to limitations you define.
         *
         * @locus Server
         *
         * @param {Options} options
         */
        allow(options:{
                  insert?:Function;
                  update?:Function;
                  remove?:Function;
                  fetch?:any;
                  transform?:Function
              }):any;


        /**
         * Override `allow` rules.
         *
         * @locus Server
         *
         * @param {Options} options
         */
        deny(options:{
                 insert?:Function;
                 update?:Function;
                 remove?:Function;
                 fetch?:String[];
                 transform?:Function
             }):any;

    }


}



interface IEJSON {

    /**
     * Parse a string into an EJSON value. Throws an error if the string is not valid EJSON.
     *
     * @locus Anywhere
     *
     * @param {String} str - A string to parse into an EJSON value.
     */
    parse(str:string):any;


    /**
     * Serialize a value to a string.
     * 
     * For EJSON values, the serialization fully represents the value. For non-EJSON values, serializes the same way as `JSON.stringify`.
     *
     * @locus Anywhere
     *
     * @param {EJSON-compatible value} val - A value to stringify.
     * @param {Options} [options]
     */
    stringify(val:any,
              options?:{
                  indent?:any;
                  canonical?:boolean
              }):any;


    /**
     * Deserialize an EJSON value from its  plain JSON representation.
     *
     * @locus Anywhere
     *
     * @param {JSON-compatible value} val - A value to deserialize into EJSON.
     */
    fromJSONValue(val:any):any;


    /**
     * Serialize an EJSON-compatible value into its plain JSON representation.
     *
     * @locus Anywhere
     *
     * @param {EJSON-compatible value} val - A value to serialize to plain JSON.
     */
    toJSONValue(val:any):any;


    /**
     * Return true if `a` and `b` are equal to each other.  Return false otherwise.  Uses the `equals` method on `a` if present, otherwise performs a deep comparison.
     *
     * @locus Anywhere
     *
     * @param {EJSON-compatible object} a
     * @param {EJSON-compatible object} b
     * @param {Options} [options]
     */
    equals(a:any,
           b:any,
           options?:{
               keyOrderSensitive?:boolean
           }):any;


    /**
     * Return a deep copy of `val`.
     *
     * @locus Anywhere
     *
     * @param {EJSON-compatible value} val - A value to copy.
     */
    clone(val:any):any;


    /**
     * Allocate a new buffer of binary data that EJSON can serialize.
     *
     * @locus Anywhere
     *
     * @param {Number} size - The number of bytes of binary data to allocate.
     */
    newBinary(size:Number):any;


    /**
     * Returns true if `x` is a buffer of binary data, as returned from [`EJSON.newBinary`](#ejson_new_binary).
     *
     * @locus Anywhere
     *
     * @param x
     */
    isBinary(x:any):any;


    /**
     * Add a custom datatype to EJSON.
     *
     * @locus Anywhere
     *
     * @param {String} name - A tag for your custom type; must be unique among custom data types defined in your project, and must match the result of your type's `typeName` method.
     * @param {Function} factory - A function that deserializes a JSON-compatible value into an instance of your type.  This should match the serialization performed by your type's `toJSONValue` method.
     */
    addType(name:string, factory:Function):any;

}

declare var EJSON:IEJSON;


interface IDDP {

    /**
     * Connect to the server of a different Meteor application to subscribe to its document sets and invoke its remote methods.
     *
     * @locus Anywhere
     *
     * @param {String} url - The URL of another Meteor application.
     */
    connect(url:string):any;

}

declare var DDP:IDDP;


interface MeteorCursor {

    /**
     * Returns the number of documents that match a query.
     *
     * @locus Anywhere
     */
    count():any;


    /**
     * Return all matching documents as an Array.
     *
     * @locus Anywhere
     */
    fetch():any;


    /**
     * Call `callback` once for each matching document, sequentially and synchronously.
     *
     * @locus Anywhere
     *
     * @param {Function} callback - Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.
     * @param {Any} [thisArg] - An object which will be the value of `this` inside `callback`.
     */
    forEach(callback:Function, thisArg?:any):any;


    /**
     * Map callback over all matching documents.  Returns an Array.
     *
     * @locus Anywhere
     *
     * @param {Function} callback - Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.
     * @param {Any} [thisArg] - An object which will be the value of `this` inside `callback`.
     */
    map(callback:Function, thisArg?:any):any;


    /**
     * Watch a query.  Receive callbacks as the result set changes.
     *
     * @locus Anywhere
     *
     * @param {Object} callbacks - Functions to call to deliver the result set as it changes
     */
    observe(callbacks:any):any;


    /**
     * Watch a query.  Receive callbacks as the result set changes.  Only the differences between the old and new documents are passed to the callbacks.
     *
     * @locus Anywhere
     *
     * @param {Object} callbacks - Functions to call to deliver the result set as it changes
     */
    observeChanges(callbacks:any):any;

}



interface IRandom {

    /**
     * Return a unique identifier.
     *
     * @locus Anywhere
     */
    id():any;

}

declare var Random:IRandom;


interface IDeps {

    /**
     * Run a function now and rerun it later whenever its dependencies change. Returns a Computation object that can be used to stop or observe the rerunning.
     *
     * @locus Client
     *
     * @param {Function} runFunc - The function to run. It receives one argument: the Computation object that will be returned.
     */
    autorun(runFunc:Function):any;


    /**
     * Process all reactive updates immediately and ensure that all invalidated computations are rerun.
     *
     * @locus Client
     */
    flush():any;


    /**
     * Run a function without tracking dependencies.
     *
     * @locus Client
     *
     * @param {Function} func - A function to call immediately.
     */
    nonreactive(func:Function):any;


    /**
     * True if there is a current computation, meaning that dependencies on reactive data sources will be tracked and potentially cause the current computation to be rerun.
     *
     * @locus Client
     */
    active:any;


    /**
     * The current computation, or `null` if there isn't one.  The current computation is the [`Deps.Computation`](#deps_computation) object created by the innermost active call to `Deps.autorun`, and it's the computation that gains dependencies when reactive data sources are accessed.
     *
     * @locus Client
     */
    currentComputation:any;


    /**
     * Registers a new [`onInvalidate`](#computation_oninvalidate) callback on the current computation (which must exist), to be called immediately when the current computation is invalidated or stopped.
     *
     * @locus Client
     *
     * @param {Function} callback - A callback function that will be invoked as `func(c)`, where `c` is the computation on which the callback is registered.
     */
    onInvalidate(callback:Function):any;


    /**
     * Schedules a function to be called during the next flush, or later in the current flush if one is in progress, after all invalidated computations have been rerun.  The function will be run once and not on subsequent flushes unless `afterFlush` is called again.
     *
     * @locus Client
     *
     * @param {Function} callback - A function to call at flush time.
     */
    afterFlush(callback:Function):any;

}

declare var Deps:IDeps;


interface IAccounts {

    /**
     * Set global accounts options.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     */
    config(options:{
               sendVerificationEmail?:boolean;
               forbidClientAccountCreation?:boolean;
               restrictCreationByEmailDomain?:any;
               loginExpirationInDays?:Number;
               oauthSecretKey?:string
           }):any;


    /**
     * Set restrictions on new user creation.
     *
     * @locus Server
     *
     * @param {Function} func - Called whenever a new user is created. Takes the new user object, and returns true to allow the creation or false to abort.
     */
    validateNewUser(func:Function):any;


    /**
     * Customize new user creation.
     *
     * @locus Server
     *
     * @param {Function} func - Called whenever a new user is created. Return the new user object, or throw an `Error` to abort the creation.
     */
    onCreateUser(func:Function):any;


    /**
     * Validate login attempts.
     *
     * @locus Server
     *
     * @param {Function} func - Called whenever a login is attempted (either successful or unsuccessful).  A login can be aborted by returning a falsy value or throwing an exception.
     */
    validateLoginAttempt(func:Function):any;


    /**
     * Register a callback to be called after a login attempt.
     *
     * @locus Server
     *
     * @param {Function} func - The callback to be called after the login attempt
     */
    onLogin(func:Function):any;


    /**
     * Register a callback to be called after a login attempt.
     *
     * @locus Server
     *
     * @param {Function} func - The callback to be called after the login attempt
     */
    onLoginFailure(func:Function):any;


    /**
     * Create a new user.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     * @param {Function} [callback] - Client only, optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    createUser(options:{
                   username?:string;
                   email?:string;
                   password?:string;
                   profile?:any
               },
               callback?:Function):any;


    /**
     * Change the current user's password. Must be logged in.
     *
     * @locus Client
     *
     * @param {String} oldPassword - The user's current password. This is __not__ sent in plain text over the wire.
     * @param {String} newPassword - A new password for the user. This is __not__ sent in plain text over the wire.
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    changePassword(oldPassword:string, newPassword:string, callback?:Function):any;


    /**
     * Request a forgot password email.
     *
     * @locus Client
     *
     * @param {Options} options
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    forgotPassword(options:{
                       email?:string
                   },
                   callback?:Function):any;


    /**
     * Reset the password for a user using a token received in email. Logs the user in afterwards.
     *
     * @locus Client
     *
     * @param {String} token - The token retrieved from the reset password URL.
     * @param {String} newPassword - A new password for the user. This is __not__ sent in plain text over the wire.
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    resetPassword(token:string, newPassword:string, callback?:Function):any;


    /**
     * Forcibly change the password for a user.
     *
     * @locus Server
     *
     * @param {String} userId - The id of the user to update.
     * @param {String} newPassword - A new password for the user.
     */
    setPassword(userId:string, newPassword:string):any;


    /**
     * Marks the user's email address as verified. Logs the user in afterwards.
     *
     * @locus Client
     *
     * @param {String} token - The token retrieved from the verification URL.
     * @param {Function} [callback] - Optional callback. Called with no arguments on success, or with a single `Error` argument on failure.
     */
    verifyEmail(token:string, callback?:Function):any;


    /**
     * Send an email with a link the user can use to reset their password.
     *
     * @locus Server
     *
     * @param {String} userId - The id of the user to send email to.
     * @param {String} [email] - Optional. Which address of the user's to send the email to. This address must be in the user's `emails` list. Defaults to the first email in the list.
     */
    sendResetPasswordEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use to set their initial password.
     *
     * @locus Server
     *
     * @param {String} userId - The id of the user to send email to.
     * @param {String} [email] - Optional. Which address of the user's to send the email to. This address must be in the user's `emails` list. Defaults to the first email in the list.
     */
    sendEnrollmentEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use verify their email address.
     *
     * @locus Server
     *
     * @param {String} userId - The id of the user to send email to.
     * @param {String} [email] - Optional. Which address of the user's to send the email to. This address must be in the user's `emails` list. Defaults to the first unverified email in the list.
     */
    sendVerificationEmail(userId:string, email?:string):any;


    /**
     * Options to customize emails sent from the Accounts system.
     *
     * @locus Anywhere
     */
    emailTemplates:any;

}

declare var Accounts:IAccounts;


interface IMatch {

    /**
     * Returns true if the value matches the [pattern](#matchpatterns).
     *
     * @locus Anywhere
     *
     * @param {Any} value - The value to check
     * @param {Match pattern} pattern - The [pattern](#matchpatterns) to match `value` against
     */
    test(value:any, pattern:any):any;

}

declare var Match:IMatch;


interface ISession {

    /**
     * Set a variable in the session. Notify any listeners that the value has changed (eg: redraw templates, and rerun any [`Deps.autorun`](#deps_autorun) computations, that called [`Session.get`](#session_get) on this `key`.)
     *
     * @locus Client
     *
     * @param {String} key - The key to set, eg, `selectedItem`
     * @param {EJSON-able object or undefined} value - The new value for `key`
     */
    set(key:string, value:any):any;


    /**
     * Set a variable in the session if it is undefined. Otherwise works exactly the same as [`Session.set`](#session_set).
     *
     * @locus Client
     *
     * @param {String} key - The key to set, eg, `selectedItem`
     * @param {EJSON-able object or undefined} value - The new value for `key`
     */
    setDefault(key:string, value:any):any;


    /**
     * Get the value of a session variable. If inside a [reactive computation](#reactivity), invalidate the computation the next time the value of the variable is changed by [`Session.set`](#session_set). This returns a clone of the session value, so if it's an object or an array, mutating the returned value has no effect on the value stored in the session.
     *
     * @locus Client
     *
     * @param {String} key - The name of the session variable to return
     */
    get(key:string):any;


    /**
     * Test if a session variable is equal to a value. If inside a [reactive computation](#reactivity), invalidate the computation the next time the variable changes to or from the value.
     *
     * @locus Client
     *
     * @param {String} key - The name of the session variable to test
     * @param {String, Number, Boolean, null, or undefined} value - The value to test against
     */
    equals(key:string, value:any):any;

}

declare var Session:ISession;


interface IHTTP {

    /**
     * Perform an outbound HTTP request.
     *
     * @locus Anywhere
     *
     * @param {String} method - The [HTTP method](http://en.wikipedia.org/wiki/HTTP_method) to use, such as "`GET`", "`POST`", or "`HEAD`".
     * @param {String} url - The URL to retrieve.
     * @param {Options} [options]
     * @param {Function} [asyncCallback] - Optional callback.  If passed, the method runs asynchronously, instead of synchronously, and calls asyncCallback.  On the client, this callback is required.
     */
    call(method:string,
         url:string,
         options?:{
             content?:string;
             data?:any;
             query?:string;
             params?:any;
             auth?:string;
             headers?:any;
             timeout?:Number;
             followRedirects?:boolean
         },
         asyncCallback?:Function):any;


    /**
     * Send an HTTP GET request.  Equivalent to `HTTP.call("GET", ...)`.
     *
     * @locus Anywhere
     *
     * @param url
     * @param {Options} [options]
     * @param [asyncCallback]
     */
    get(url:any,
        options?:{
            
        },
        asyncCallback?:any):any;


    /**
     * Send an HTTP POST request.  Equivalent to `HTTP.call("POST", ...)`.
     *
     * @locus Anywhere
     *
     * @param url
     * @param {Options} [options]
     * @param [asyncCallback]
     */
    post(url:any,
         options?:{
             
         },
         asyncCallback?:any):any;


    /**
     * Send an HTTP PUT request.  Equivalent to `HTTP.call("PUT", ...)`.
     *
     * @locus Anywhere
     *
     * @param url
     * @param {Options} [options]
     * @param [asyncCallback]
     */
    put(url:any,
        options?:{
            
        },
        asyncCallback?:any):any;


    /**
     * Send an HTTP DELETE request.  Equivalent to `HTTP.call("DELETE", ...)`.  (Named `del` to avoid conflict with JavaScript's `delete`.)
     *
     * @locus Anywhere
     *
     * @param url
     * @param {Options} [options]
     * @param [asyncCallback]
     */
    del(url:any,
        options?:{
            
        },
        asyncCallback?:any):any;

}

declare var HTTP:IHTTP;


interface IUI {

    /**
     * Defines a [helper function](#template_helpers) which can be used from all templates.
     *
     * @locus Client
     *
     * @param {String} name - The name of the helper function you are defining.
     * @param {Function} func - The helper function itself.
     */
    registerHelper(name:string, func:Function):any;


    /**
     * The [component object](#templates_api) representing your `<body>` tag.
     *
     * @locus Client
     */
    body:any;


    /**
     * Inserts an instantiated component into the DOM and calls its [`rendered`](#template_rendered) callback.
     *
     * @locus Client
     *
     * @param {Instantiated component object} instantiatedComponent - The return value from `UI.render` or `UI.renderWithData`.
     * @param {DOM Node} parentNode - The node that will be the parent of the rendered template.
     * @param {DOM Node} [nextNode] - If provided, must be a child of <em>parentNode</em>; the template will be inserted before this node. If not provided, the template will be inserted as the last child.
     */
    insert(instantiatedComponent:any, parentNode:any, nextNode?:any):any;


    /**
     * Returns the data context that was used when rendering a DOM element from a Meteor template.
     *
     * @locus Client
     *
     * @param {DOM Element} el - An element that was rendered by a Meteor template
     */
    getElementData(el:any):any;

}

declare var UI:IUI;


interface IEmail {

    /**
     * Send an email. Throws an `Error` on failure to contact mail server or if mail server returns an error.
     *
     * @locus Server
     *
     * @param {Options} options
     */
    send(options:{
             from?:string;
             to?:any;
             cc?:any;
             bcc?:any;
             replyTo?:any;
             subject?:string;
             text?:string;
             html?:string;
             headers?:any
         }):any;

}

declare var Email:IEmail;


interface IAssets {

    /**
     * Retrieve the contents of the static server asset as a UTF8-encoded string.
     *
     * @locus Server
     *
     * @param {String} assetPath - The path of the asset, relative to the application's `private` subdirectory.
     * @param {Function} [asyncCallback] - Optional callback, which is called asynchronously with the error or result after the function is complete. If not provided, the function runs synchronously.
     */
    getText(assetPath:string, asyncCallback?:Function):any;


    /**
     * Retrieve the contents of the static server asset as an [EJSON Binary](#ejson_new_binary).
     *
     * @locus Server
     *
     * @param {String} assetPath - The path of the asset, relative to the application's `private` subdirectory.
     * @param {Function} [asyncCallback] - Optional callback, which is called asynchronously with the error or result after the function is complete. If not provided, the function runs synchronously.
     */
    getBinary(assetPath:string, asyncCallback?:Function):any;

}

declare var Assets:IAssets;