interface IMeteor {

    /**
     * Run code when a client or a server starts.
     *
     * @locus Anywhere
     *
     * @param {Function} func - A function to run on startup.
     */
    startup(func:Function):any;

}

declare var Meteor:IMeteor;