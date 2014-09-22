interface IAccounts {

    /**
     * Set global accounts options.
     *
     * @locus Anywhere
     *
     * @param {Options} options
     */
    config(options:{
               sendVerificationEmail?:boolean;
               forbidClientAccountCreation?:boolean;
               restrictCreationByEmailDomain?:any;
               loginExpirationInDays?:Number;
               oauthSecretKey?:string
           }):any;

}

declare var Accounts:IAccounts;