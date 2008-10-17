/* Start ----------------------------------------------------- Server.js*/

// ==========================================================================
// App
// ==========================================================================

require('server/server') ;
IvServer = SC.Server.extend({

  _listSuccess: function(status, transport, cacheCode, context) {
    var json = eval('json='+transport.responseText) ;
    var wrapperElement = context.recordType.wrapperElement;
    if (!json) { console.log('invalid json!'); return; }
    
    // first, build any records passed back
    if (json.wrapperElement.records) {
      this.refreshRecordsWithData(json.wrapperElement.records,context.recordType,cacheCode,false);
    }
    
    // next, convert the list of ids into records.
    var recs = (json.wrapperElement.ids) ? json.ids.map(function(guid) {
      return SC.Store.getRecordFor(guid,context.recordType) ;
    }) : [] ;
    
    // now invoke callback
    if (context.callback) context.callback(recs,json.count,cacheCode) ;
  }
  
})

/* End ------------------------------------------------------- Server.js*/

