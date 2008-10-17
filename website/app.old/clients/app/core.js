// ==========================================================================
// App
// ==========================================================================

require('server/server') ;
IvServer = SC.Server.extend({

  _listSuccess: function(status, transport, cacheCode, context) {
    var json = eval('json='+transport.responseText) ;
    var wrapperElement = context.recordType.prototype.wrapperElement ;
    if (!json) { console.log('invalid json!'); return; }
    
    // first, build any records passed back
    if (json[wrapperElement].records) {
      this.refreshRecordsWithData(json[wrapperElement].records,context.recordType,cacheCode,false);
    }
    
    // next, convert the list of ids into records.
    var recs = (json[wrapperElement].ids) ? json.ids.map(function(guid) {
      return SC.Store.getRecordFor(guid,context.recordType) ;
    }) : [] ;
    
    // now invoke callback
    if (context.callback) context.callback(recs,json.count,cacheCode) ;
  }
  
});

App = SC.Object.create({

  // This will create the server for your application.  Add any namespaces
  // your model objects are defined in to the prefix array.
  // server: SC.Server.create({ prefix: ['App'] }),
  server: IvServer.create({ prefix: ['App'], urlFormat: "server/%@/%@/" }),

  // When you are in development mode, this array will be populated with
  // any fixtures you create for testing and loaded automatically in your
  // main method.  When in production, this will be an empty array.
  FIXTURES: [],
  
  // Any keys in this array will be instantiated automatically from main.
  controllers: []

}) ;