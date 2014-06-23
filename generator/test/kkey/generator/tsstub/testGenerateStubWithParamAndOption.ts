interface IMeteor {

    /**
     * Generate an absolute URL pointing to the application. The server reads from the `ROOT_URL` environment variable to determine where it is running. This is taken care of automatically for apps deployed with `meteor deploy`, but must be provided when using `meteor bundle`.
     *
     * @locus Anywhere
     *
     * @param {String} [path] - A path to append to the root URL. Do not include a leading "`/`".
     * @param {Options} [options]
     */
    absoluteUrl(path?:string,
                options?:{
                    secure?:boolean;
                    replaceLocalhost?:boolean;
                    rootUrl?:string
                }):any;

}

declare var Meteor:IMeteor;