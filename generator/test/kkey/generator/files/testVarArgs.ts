interface IMeteor {

    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - <p>Name of the subscription.  Matches the name of the server's <code>publish()</code> call.</p>
     * @param {Any} [arg1, arg2...] - <p>Optional arguments passed to publisher function on server.</p>
     */
    subscribeWithVargsLast(name:string, ...args:any[]):any;


    /**
     * Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.
     *
     * @locus Client
     *
     * @param {String} name - <p>Name of the subscription.  Matches the name of the server's <code>publish()</code> call.</p>
     * @param {Any} [arg1, arg2...] - <p>Optional arguments passed to publisher function on server.</p>
     * @param {function or Object} [callbacks] - <p>Optional. May include <code>onError</code> and <code>onReady</code> callbacks. If a function is passed instead of an object, it is interpreted as an <code>onReady</code> callback.</p>
     */
    subscribe(name:string, ...args:any[]):any;

}

declare var Meteor:IMeteor;