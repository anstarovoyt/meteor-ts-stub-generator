interface IMeteor {

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

}

declare var Meteor:IMeteor;

