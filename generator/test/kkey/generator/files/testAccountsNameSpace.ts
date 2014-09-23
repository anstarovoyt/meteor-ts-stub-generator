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
                   requestPermissions:any;
                   requestOfflineToken:any;
                   forceApprovalPrompt:boolean;
                   passwordSignupFields:string
               }):any;

    }

    declare var ui:Iui;

}