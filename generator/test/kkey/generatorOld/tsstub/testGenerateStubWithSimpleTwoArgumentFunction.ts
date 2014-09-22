interface IMeteor {

    /**
     * Publish a record set.
     *
     * @locus Server
     *
     * @param {String} name - Name of the record set.  If `null`, the set has no name, and the record set is automatically sent to all connected clients.
     * @param {Function} func - Function called on the server each time a client subscribes.  Inside the function, `this` is the publish handler object, described below.  If the client passed arguments to `subscribe`, the function is called with the same arguments.
     */
    publish(name:string, func:Function):string;

}

declare var Meteor:IMeteor;