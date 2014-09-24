declare module Accounts {

    /**
     * Options to customize emails sent from the Accounts system.
     *
     * @locus Anywhere
     */
    var emailTemplates:any;


    /**
     * Set global accounts options.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     */
    function config(options:{
               sendVerificationEmail?:boolean;
               forbidClientAccountCreation?:boolean;
               restrictCreationByEmailDomain?:any;
               loginExpirationInDays?:Number;
               oauthSecretKey?:string
           }):any;


    /**
     * Validate login attempts.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a login is attempted (either successful or unsuccessful).  A login can be aborted by returning a falsy value or throwing an exception.</p>
     */
    function validateLoginAttempt(func:Function):any;


    /**
     * Register a callback to be called after a login attempt succeeds.
     *
     * @locus Server
     *
     * @param {function} func - <p>The callback to be called when login is successful.</p>
     */
    function onLogin(func:Function):any;


    /**
     * Register a callback to be called after a login attempt fails.
     *
     * @locus Server
     *
     * @param {function} func - <p>The callback to be called after the login has failed.</p>
     */
    function onLoginFailure(func:Function):any;


    /**
     * Customize new user creation.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a new user is created. Return the new user object, or throw an <code>Error</code> to abort the creation.</p>
     */
    function onCreateUser(func:Function):any;


    /**
     * Set restrictions on new user creation.
     *
     * @locus Server
     *
     * @param {function} func - <p>Called whenever a new user is created. Takes the new user object, and returns true to allow the creation or false to abort.</p>
     */
    function validateNewUser(func:Function):any;


    /**
     * Create a new user.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     * @param {function} [callback] - <p>Client only, optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function createUser(options:{
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
     * @param {String} oldPassword - <p>The user's current password. This is <strong>not</strong> sent in plain text over the wire.</p>
     * @param {String} newPassword - <p>A new password for the user. This is <strong>not</strong> sent in plain text over the wire.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function changePassword(oldPassword:string, newPassword:string, callback?:Function):any;


    /**
     * Request a forgot password email.
     *
     * @locus Client
     *
     * @param {Options} options
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function forgotPassword(options:{
                       email?:string
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
    function resetPassword(token:string, newPassword:string, callback?:Function):any;


    /**
     * Marks the user's email address as verified. Logs the user in afterwards.
     *
     * @locus Client
     *
     * @param {String} token - <p>The token retrieved from the verification URL.</p>
     * @param {function} [callback] - <p>Optional callback. Called with no arguments on success, or with a single <code>Error</code> argument on failure.</p>
     */
    function verifyEmail(token:string, callback?:Function):any;


    /**
     * Forcibly change the password for a user.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to update.</p>
     * @param {String} newPassword - <p>A new password for the user.</p>
     */
    function setPassword(userId:string, newPassword:string):any;


    /**
     * Send an email with a link the user can use to reset their password.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first email in the list.</p>
     */
    function sendResetPasswordEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use to set their initial password.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first email in the list.</p>
     */
    function sendEnrollmentEmail(userId:string, email?:string):any;


    /**
     * Send an email with a link the user can use verify their email address.
     *
     * @locus Server
     *
     * @param {String} userId - <p>The id of the user to send email to.</p>
     * @param {String} [email] - <p>Optional. Which address of the user's to send the email to. This address must be in the user's <code>emails</code> list. Defaults to the first unverified email in the list.</p>
     */
    function sendVerificationEmail(userId:string, email?:string):any;

    interface Iui {

        /**
         * Configure the behavior of [`{{> loginButtons}}`](#accountsui).
         *
         * @locus Client
         *
         * @param {Options} options
         */
        config(options:{
                   requestPermissions?:any;
                   requestOfflineToken?:any;
                   forceApprovalPrompt?:boolean;
                   passwordSignupFields?:string
               }):any;

    }

    declare var ui:Iui;

}



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
                    secure?:boolean;
                    replaceLocalhost?:boolean;
                    rootUrl?:string
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



declare module Mongo {

    /**
     * Constructor for a Collection
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>The name of the collection.  If null, creates an unmanaged (unsynchronized) local collection.</p>
     * @param {Options} [options]
     */
    function Collection(name:string,
               options?:{
                   connection?:any;
                   idGeneration?:string;
                   transform?:Function
               }):Collection;


    /**
     * Create a Mongo-style `ObjectID`.  If you don't specify a `hexString`, the `ObjectID` will generated randomly (not using MongoDB's ID construction rules).
     *
     * @locus Anywhere
     *
     * @param {String} hexString - <p>Optional.  The 24-character hexadecimal contents of the ObjectID to create</p>
     */
    function ObjectID(hexString:string):ObjectID;

    interface Cursor {

        /**
         * Call `callback` once for each matching document, sequentially and synchronously.
         *
         * @locus Anywhere
         *
         * @param {function} callback - <p>Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.</p>
         * @param {Any} [thisArg] - <p>An object which will be the value of <code>this</code> inside <code>callback</code>.</p>
         */
        forEach(callback:Function, thisArg?:any):any;


        /**
         * Map callback over all matching documents.  Returns an Array.
         *
         * @locus Anywhere
         *
         * @param {function} callback - <p>Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.</p>
         * @param {Any} [thisArg] - <p>An object which will be the value of <code>this</code> inside <code>callback</code>.</p>
         */
        map(callback:Function, thisArg?:any):any;


        /**
         * Return all matching documents as an Array.
         *
         * @locus Anywhere
         */
        fetch():any;


        /**
         * Returns the number of documents that match a query.
         *
         * @locus Anywhere
         */
        count():any;


        /**
         * Watch a query.  Receive callbacks as the result set changes.
         *
         * @locus Anywhere
         *
         * @param {Object} callbacks - <p>Functions to call to deliver the result set as it changes</p>
         */
        observe(callbacks:any):any;


        /**
         * Watch a query.  Receive callbacks as the result set changes.  Only the differences between the old and new documents are passed to the callbacks.
         *
         * @locus Anywhere
         *
         * @param {Object} callbacks - <p>Functions to call to deliver the result set as it changes</p>
         */
        observeChanges(callbacks:any):any;

    }


    interface Collection {

        /**
         * Insert a document in the collection.  Returns its unique _id.
         *
         * @locus Anywhere
         *
         * @param {Object} doc - <p>The document to insert. May not yet have an _id attribute, in which case Meteor will generate one for you.</p>
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the _id as the second.</p>
         */
        insert(doc:any, callback?:Function):any;


        /**
         * Modify one or more documents in the collection. Returns the number of affected documents.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to modify</p>
         * @param {MongoModifier} modifier - <p>Specifies how to modify the documents</p>
         * @param {Options} [options]
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.</p>
         */
        update(selector:any,
               modifier:any,
               options?:{
                   multi?:boolean;
                   upsert?:boolean
               },
               callback?:Function):any;


        /**
         * Find the documents in a collection that match the selector.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} [selector] - <p>A query describing the documents to find</p>
         * @param {Options} [options]
         */
        find(selector?:any,
             options?:{
                 sort?:any;
                 skip?:Number;
                 limit?:Number;
                 fields?:any;
                 reactive?:boolean;
                 transform?:Function
             }):Cursor;


        /**
         * Finds the first document that matches the selector, as ordered by sort and skip options.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} [selector] - <p>A query describing the documents to find</p>
         * @param {Options} [options]
         */
        findOne(selector?:any,
                options?:{
                    sort?:any;
                    skip?:Number;
                    fields?:any;
                    reactive?:boolean;
                    transform?:Function
                }):any;


        /**
         * Remove documents from the collection
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to remove</p>
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as its argument.</p>
         */
        remove(selector:any, callback?:Function):any;


        /**
         * Modify one or more documents in the collection, or insert one if no matching documents were found. Returns an object with keys `numberAffected` (the number of documents modified)  and `insertedId` (the unique _id of the document that was inserted, if any).
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to modify</p>
         * @param {MongoModifier} modifier - <p>Specifies how to modify the documents</p>
         * @param {Options} [options]
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.</p>
         */
        upsert(selector:any,
               modifier:any,
               options?:{
                   multi?:boolean
               },
               callback?:Function):any;


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
                 fetch?:any;
                 transform?:Function
             }):any;

    }


    interface ObjectID {

    }


}



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


interface MethodInvocationMember {

    /**
     * Access inside a method invocation.  Boolean value, true if this invocation is a stub.
     *
     * @locus Anywhere
     */
    isSimulation:any;


    /**
     * The id of the user that made this method call, or `null` if no user was logged in.
     *
     * @locus Anywhere
     */
    userId:any;


    /**
     * Access inside a method invocation. The [connection](#meteor_onconnection) that this method was received on. `null` if the method is not associated with a connection, eg. a server initiated method call.
     *
     * @locus Server
     */
    connection:any;


    /**
     * Call inside a method invocation.  Allow subsequent method from this client to begin running in a new fiber.
     *
     * @locus Server
     */
    unblock():any;


    /**
     * Set the logged in user.
     *
     * @locus Server
     *
     * @param {String or null} userId - <p>The value that should be returned by <code>userId</code> on this connection.</p>
     */
    setUserId(userId:any):any;

}



interface SubscriptionMember {

    /**
     * Access inside the publish function. The incoming [connection](#meteor_onconnection) for this subscription.
     *
     * @locus Server
     */
    connection:any;


    /**
     * Access inside the publish function. The id of the logged-in user, or `null` if no user is logged in.
     *
     * @locus Server
     */
    userId:any;


    /**
     * Call inside the publish function.  Stops this client's subscription, triggering a call on the client to the `onError` callback passed to [`Meteor.subscribe`](#meteor_subscribe), if any. If `error` is not a [`Meteor.Error`](#meteor_error), it will be [sanitized](#meteor_error).
     *
     * @locus Server
     *
     * @param {Error} error - <p>The error to pass to the client.</p>
     */
    error(error:any):any;


    /**
     * Call inside the publish function.  Stops this client's subscription; the `onError` callback is *not* invoked on the client.
     *
     * @locus Server
     */
    stop():any;


    /**
     * Call inside the publish function.  Registers a callback function to run when the subscription is stopped.
     *
     * @locus Server
     *
     * @param {function} func - <p>The callback function</p>
     */
    onStop(func:Function):any;


    /**
     * Call inside the publish function.  Informs the subscriber that a document has been added to the record set.
     *
     * @locus Server
     *
     * @param {String} collection - <p>The name of the collection that contains the new document.</p>
     * @param {String} id - <p>The new document's ID.</p>
     * @param {Object} fields - <p>The fields in the new document.  If <code>_id</code> is present it is ignored.</p>
     */
    added(collection:string, id:string, fields:any):any;


    /**
     * Call inside the publish function.  Informs the subscriber that a document in the record set has been modified.
     *
     * @locus Server
     *
     * @param {String} collection - <p>The name of the collection that contains the changed document.</p>
     * @param {String} id - <p>The changed document's ID.</p>
     * @param {Object} fields - <p>The fields in the document that have changed, together with their new values.  If a field is not present in <code>fields</code> it was left unchanged; if it is present in <code>fields</code> and has a value of <code>undefined</code> it was removed from the document.  If <code>_id</code> is present it is ignored.</p>
     */
    changed(collection:string, id:string, fields:any):any;


    /**
     * Call inside the publish function.  Informs the subscriber that a document has been removed from the record set.
     *
     * @locus Server
     *
     * @param {String} collection - <p>The name of the collection that the document has been removed from.</p>
     * @param {String} id - <p>The ID of the document that has been removed.</p>
     */
    removed(collection:string, id:string):any;


    /**
     * Call inside the publish function.  Informs the subscriber that an initial, complete snapshot of the record set has been sent.  This will trigger a call on the client to the `onReady` callback passed to  [`Meteor.subscribe`](#meteor_subscribe), if any.
     *
     * @locus Server
     */
    ready():any;

}



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
                  indent?:any;
                  canonical?:boolean
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
               keyOrderSensitive?:boolean
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


declare module Tracker {

    /**
     * True if there is a current computation, meaning that dependencies on reactive data sources will be tracked and potentially cause the current computation to be rerun.
     *
     * @locus Client
     */
    var active:any;


    /**
     * The current computation, or `null` if there isn't one.  The current computation is the [`Tracker.Computation`](#tracker_computation) object created by the innermost active call to `Tracker.autorun`, and it's the computation that gains dependencies when reactive data sources are accessed.
     *
     * @locus Client
     */
    var currentComputation:any;


    /**
     * Process all reactive updates immediately and ensure that all invalidated computations are rerun.
     *
     * @locus Client
     */
    function flush():any;


    /**
     * Run a function now and rerun it later whenever its dependencies change. Returns a Computation object that can be used to stop or observe the rerunning.
     *
     * @locus Client
     *
     * @param {function} runFunc - <p>The function to run. It receives one argument: the Computation object that will be returned.</p>
     */
    function autorun(runFunc:Function):Computation;


    /**
     * Run a function without tracking dependencies.
     *
     * @locus Client
     *
     * @param {function} func - <p>A function to call immediately.</p>
     */
    function nonreactive(func:Function):any;


    /**
     * Registers a new [`onInvalidate`](#computation_oninvalidate) callback on the current computation (which must exist), to be called immediately when the current computation is invalidated or stopped.
     *
     * @locus Client
     *
     * @param {function} callback - <p>A callback function that will be invoked as <code>func(c)</code>, where <code>c</code> is the computation on which the callback is registered.</p>
     */
    function onInvalidate(callback:Function):any;


    /**
     * Schedules a function to be called during the next flush, or later in the current flush if one is in progress, after all invalidated computations have been rerun.  The function will be run once and not on subsequent flushes unless `afterFlush` is called again.
     *
     * @locus Client
     *
     * @param {function} callback - <p>A function to call at flush time.</p>
     */
    function afterFlush(callback:Function):any;


    /**
     * A Dependency represents an atomic unit of reactive data that a
     * computation might depend on. Reactive data sources such as Session or
     * Minimongo internally create different Dependency objects for different
     * pieces of data, each of which may be depended on by multiple computations.
     * When the data changes, the computations are invalidated.
     *
     */
    function Dependency():Dependency;

    interface Computation {

        /**
         * True if this computation has been stopped.
         *
         * @locus Client
         */
        stopped:any;


        /**
         * True if this computation has been invalidated (and not yet rerun), or if it has been stopped.
         *
         * @locus Client
         */
        invalidated:any;


        /**
         * True during the initial run of the computation at the time `Tracker.autorun` is called, and false on subsequent reruns and at other times.
         *
         * @locus Client
         */
        firstRun:any;


        /**
         * Registers `callback` to run when this computation is next invalidated, or runs it immediately if the computation is already invalidated.  The callback is run exactly once and not upon future invalidations unless `onInvalidate` is called again after the computation becomes valid again.
         *
         * @locus Client
         *
         * @param {function} callback - <p>Function to be called on invalidation. Receives one argument, the computation that was invalidated.</p>
         */
        onInvalidate(callback:Function):any;


        /**
         * Invalidates this computation so that it will be rerun.
         *
         * @locus Client
         */
        invalidate():any;


        /**
         * Prevents this computation from rerunning.
         *
         * @locus Client
         */
        stop():any;

    }


    interface Dependency {

        /**
         * Declares that the current computation (or `fromComputation` if given) depends on `dependency`.  The computation will be invalidated the next time `dependency` changes.
         * 
         * If there is no current computation and `depend()` is called with no arguments, it does nothing and returns false.
         * 
         * Returns true if the computation is a new dependent of `dependency` rather than an existing one.
         *
         * @locus Client
         *
         * @param {Tracker.Computation} [fromComputation] - <p>An optional computation declared to depend on <code>dependency</code> instead of the current computation.</p>
         */
        depend(fromComputation?:any):any;


        /**
         * Invalidate all dependent computations immediately and remove them as dependents.
         *
         * @locus Client
         */
        changed():any;


        /**
         * True if this Dependency has one or more dependent Computations, which would be invalidated if this Dependency were to change.
         *
         * @locus Client
         */
        hasDependents():any;

    }


}



interface IDDP {

    /**
     * Connect to the server of a different Meteor application to subscribe to its document sets and invoke its remote methods.
     *
     * @locus Anywhere
     *
     * @param {String} url - <p>The URL of another Meteor application.</p>
     */
    connect(url:string):any;

}

declare var DDP:IDDP;


interface IHTTP {

    /**
     * Perform an outbound HTTP request.
     *
     * @locus Anywhere
     *
     * @param {String} method - <p>The <a href="http://en.wikipedia.org/wiki/HTTP_method">HTTP method</a> to use, such as &quot;<code>GET</code>&quot;, &quot;<code>POST</code>&quot;, or &quot;<code>HEAD</code>&quot;.</p>
     * @param {String} url - <p>The URL to retrieve.</p>
     * @param {Options} [options]
     * @param {function} [asyncCallback] - <p>Optional callback.  If passed, the method runs asynchronously, instead of synchronously, and calls asyncCallback.  On the client, this callback is required.</p>
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

}

declare var HTTP:IHTTP;


interface ISession {

    /**
     * Set a variable in the session. Notify any listeners that the value has changed (eg: redraw templates, and rerun any [`Tracker.autorun`](#tracker_autorun) computations, that called [`Session.get`](#session_get) on this `key`.)
     *
     * @locus Client
     *
     * @param {String} key - <p>The key to set, eg, <code>selectedItem</code></p>
     * @param {EJSONable or undefined} value - <p>The new value for <code>key</code></p>
     */
    set(key:string, value:any):any;


    /**
     * Set a variable in the session if it is undefined. Otherwise works exactly the same as [`Session.set`](#session_set).
     *
     * @locus Client
     *
     * @param {String} key - <p>The key to set, eg, <code>selectedItem</code></p>
     * @param {EJSONable or undefined} value - <p>The new value for <code>key</code></p>
     */
    setDefault(key:string, value:any):any;


    /**
     * Get the value of a session variable. If inside a [reactive computation](#reactivity), invalidate the computation the next time the value of the variable is changed by [`Session.set`](#session_set). This returns a clone of the session value, so if it's an object or an array, mutating the returned value has no effect on the value stored in the session.
     *
     * @locus Client
     *
     * @param {String} key - <p>The name of the session variable to return</p>
     */
    get(key:string):any;


    /**
     * Test if a session variable is equal to a value. If inside a [reactive computation](#reactivity), invalidate the computation the next time the variable changes to or from the value.
     *
     * @locus Client
     *
     * @param {String} key - <p>The name of the session variable to test</p>
     * @param {String or Number or Boolean or null or undefined} value - <p>The value to test against</p>
     */
    equals(key:string, value:any):any;

}

declare var Session:ISession;