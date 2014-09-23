declare module Mongo {

    /**
     * Constructor for a Collection
     *
     * @locus Anywhere
     *
     * @param {String} name - <p>The name of the collection.  If null, creates an unmanaged (unsynchronized) local collection.</p>
     * @param {Options} [options]
     */
    function Collection(name:string,
               options?:{
                   connection:any;
                   idGeneration:string;
                   transform:Function
               }):Collection;


    /**
     * Create a Mongo-style `ObjectID`.  If you don't specify a `hexString`, the `ObjectID` will generated randomly (not using MongoDB's ID construction rules).
     *
     * @locus Anywhere
     *
     * @param {String} hexString - <p>Optional.  The 24-character hexadecimal contents of the ObjectID to create</p>
     */
    function ObjectID(hexString:string):ObjectID;

    interface Cursor {

        /**
         * Call `callback` once for each matching document, sequentially and synchronously.
         *
         * @locus Anywhere
         *
         * @param {function} callback - <p>Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.</p>
         * @param {Any} [thisArg] - <p>An object which will be the value of <code>this</code> inside <code>callback</code>.</p>
         */
        forEach(callback:Function, thisArg?:any):any;


        /**
         * Map callback over all matching documents.  Returns an Array.
         *
         * @locus Anywhere
         *
         * @param {function} callback - <p>Function to call. It will be called with three arguments: the document, a 0-based index, and <em>cursor</em> itself.</p>
         * @param {Any} [thisArg] - <p>An object which will be the value of <code>this</code> inside <code>callback</code>.</p>
         */
        map(callback:Function, thisArg?:any):any;


        /**
         * Return all matching documents as an Array.
         *
         * @locus Anywhere
         */
        fetch():any;


        /**
         * Returns the number of documents that match a query.
         *
         * @locus Anywhere
         */
        count():any;


        /**
         * Watch a query.  Receive callbacks as the result set changes.
         *
         * @locus Anywhere
         *
         * @param {Object} callbacks - <p>Functions to call to deliver the result set as it changes</p>
         */
        observe(callbacks:any):any;


        /**
         * Watch a query.  Receive callbacks as the result set changes.  Only the differences between the old and new documents are passed to the callbacks.
         *
         * @locus Anywhere
         *
         * @param {Object} callbacks - <p>Functions to call to deliver the result set as it changes</p>
         */
        observeChanges(callbacks:any):any;

    }


    interface Collection {

        /**
         * Insert a document in the collection.  Returns its unique _id.
         *
         * @locus Anywhere
         *
         * @param {Object} doc - <p>The document to insert. May not yet have an _id attribute, in which case Meteor will generate one for you.</p>
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the _id as the second.</p>
         */
        insert(doc:any, callback?:Function):any;


        /**
         * Modify one or more documents in the collection. Returns the number of affected documents.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to modify</p>
         * @param {MongoModifier} modifier - <p>Specifies how to modify the documents</p>
         * @param {Options} [options]
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.</p>
         */
        update(selector:any,
               modifier:any,
               options?:{
                   multi:boolean;
                   upsert:boolean
               },
               callback?:Function):any;


        /**
         * Find the documents in a collection that match the selector.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} [selector] - <p>A query describing the documents to find</p>
         * @param {Options} [options]
         */
        find(selector?:any,
             options?:{
                 sort:any;
                 skip:Number;
                 limit:Number;
                 fields:any;
                 reactive:boolean;
                 transform:Function
             }):Cursor;


        /**
         * Finds the first document that matches the selector, as ordered by sort and skip options.
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} [selector] - <p>A query describing the documents to find</p>
         * @param {Options} [options]
         */
        findOne(selector?:any,
                options?:{
                    sort:any;
                    skip:Number;
                    fields:any;
                    reactive:boolean;
                    transform:Function
                }):any;


        /**
         * Remove documents from the collection
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to remove</p>
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as its argument.</p>
         */
        remove(selector:any, callback?:Function):any;


        /**
         * Modify one or more documents in the collection, or insert one if no matching documents were found. Returns an object with keys `numberAffected` (the number of documents modified)  and `insertedId` (the unique _id of the document that was inserted, if any).
         *
         * @locus Anywhere
         *
         * @param {MongoSelector} selector - <p>Specifies which documents to modify</p>
         * @param {MongoModifier} modifier - <p>Specifies how to modify the documents</p>
         * @param {Options} [options]
         * @param {function} [callback] - <p>Optional.  If present, called with an error object as the first argument and, if no error, the number of affected documents as the second.</p>
         */
        upsert(selector:any,
               modifier:any,
               options?:{
                   multi:boolean
               },
               callback?:Function):any;


        /**
         * Allow users to write directly to this collection from client code, subject to limitations you define.
         *
         * @locus Server
         *
         * @param {Options} options
         */
        allow(options:{
                  insert:Function;
                  update:Function;
                  remove:Function;
                  fetch:any;
                  transform:Function
              }):any;


        /**
         * Override `allow` rules.
         *
         * @locus Server
         *
         * @param {Options} options
         */
        deny(options:{
                 insert:Function;
                 update:Function;
                 remove:Function;
                 fetch:any;
                 transform:Function
             }):any;

    }


    interface ObjectID {

    }


}