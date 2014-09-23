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