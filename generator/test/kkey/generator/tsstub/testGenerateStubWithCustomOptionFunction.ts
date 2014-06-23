interface IMeteor {

    /**
     * Allow users to write directly to this collection from client code, subject to limitations you define.
     *
     * @locus Server
     *
     * @param {Options} options
     */
    allow(options:{
              insert?:Function;
              update?:Function;
              remove?:Function;
              fetch?:any;
              transform?:Function
          }):any;

}

declare var Meteor:IMeteor;