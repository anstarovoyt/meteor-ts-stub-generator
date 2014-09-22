interface MeteorTemplate {

    /**
     * Provide a callback when an instance of a template is rendered.
     *
     * @locus Client
     */
    rendered:any;


    /**
     * Provide a callback when an instance of a template is created.
     *
     * @locus Client
     */
    created:any;


    /**
     * Provide a callback when an instance of a template is destroyed.
     *
     * @locus Client
     */
    destroyed:any;


    /**
     * Specify event handlers for this template.
     *
     * @locus Client
     *
     * @param {Event map} eventMap - Event handlers to associate with this template.
     */
    events(eventMap:any):any;


    /**
     * Specify template helpers available to this template.
     *
     * @locus Client
     *
     * @param {Object} helpers - Dictionary of helper functions by name.
     */
    helpers(helpers:any):any;

}