    interface IMeteor {

        /**
         * Run code when a client or a server starts.
         *
         * @locus Anywhere
         *
         * @param {Function} func - A function to run on startup.
         */
        startup(func:Function):void;


        /**
         * Boolean variable.  True if running in client environment.
         *
         * @locus Anywhere
         */
        isClient:boolean;

    }

    declare var Meteor:IMeteor;