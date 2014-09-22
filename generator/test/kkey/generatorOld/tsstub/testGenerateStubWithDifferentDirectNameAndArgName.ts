interface IPackage {

    /**
     * Define dependencies and expose package methods for unit tests.
     *
     * @locus package.js
     *
     * @param {Function} f - Function, taking in the package control 'api' object, which keeps track of dependencies and exports.
     */
    onTest(f:Function):any;

}

declare var Package:IPackage;