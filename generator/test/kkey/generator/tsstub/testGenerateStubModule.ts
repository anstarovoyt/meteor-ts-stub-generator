declare module IMeteor {

    /**
     * Run code when a client or a server starts.
     *
     * @locus Anywhere
     *
     * @param {Function} func - A function to run on startup.
     */
    function startup(func:Function):void;


    /**
     * Boolean variable.  True if running in client environment.
     *
     * @locus Anywhere
     */
    var isClient:boolean;

}