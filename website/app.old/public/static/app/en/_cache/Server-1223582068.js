/* Start ----------------------------------------------------- Server.js*/

// ==========================================================================
// App
// ==========================================================================

require('server/server') ;
App.Server = SC.Server.extend({

  _listSuccess: function(status, transport, cacheCode, context) {
    var json = eval('json='+transport.responseText) ;
    if (!json) { console.log('invalid json!'); return; }
    
    // first, build any records passed back
    if (json.organizations.records) {
      this.refreshRecordsWithData(json.records,context.recordType,cacheCode,false);
    }
    
    // next, convert the list of ids into records.
    var recs = (json.organizations.ids) ? json.ids.map(function(guid) {
      return SC.Store.getRecordFor(guid,context.recordType) ;
    }) : [] ;
    
    // now invoke callback
    if (context.callback) context.callback(recs,json.count,cacheCode) ;
  }
  
})

/* End ------------------------------------------------------- Server.js*/

