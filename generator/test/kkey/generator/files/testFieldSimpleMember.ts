interface IMeteor {

    /**
     * Boolean variable.  True if running in client environment.
     *
     * @locus Anywhere
     */
    isClient:boolean;

}

declare var Meteor:IMeteor;