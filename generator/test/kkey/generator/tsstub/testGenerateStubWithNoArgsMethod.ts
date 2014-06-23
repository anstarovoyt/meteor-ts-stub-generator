interface IMeteor {

    /**
     * Get the current connection status. A reactive data source.
     *
     * @locus Client
     */
    status():any;

}

declare var Meteor:IMeteor;