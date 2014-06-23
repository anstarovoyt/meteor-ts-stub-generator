interface IMeteor {

    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - Name of the subscription.  Matches the name of the server's `publish()` call.
     * @param {Any} [arg1, arg2, ...] - Optional arguments passed to publisher function on server.
     */
    subscribe(name:string, ...args:any[]):any;

}

declare var Meteor:IMeteor;