/*
 *  SourcesResource
 *
 * Created on October 11, 2008, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.ProduceMime;
import javax.ws.rs.ConsumeMime;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import persistence.Organizations;
import persistence.Events;
import converter.SourcesConverter;
import converter.SourceConverter;
import persistence.Sources;


/**
 *
 * @author dave
 */

@Path("/sources/")
public class SourcesResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of SourcesResource */
    public SourcesResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public SourcesResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Sources instance in XML format.
     *
     * @return an instance of SourcesConverter
     */
    @GET
    @ProduceMime({"application/xml", "application/json"})
    public SourcesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new SourcesConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Post method for creating an instance of Sources using XML as the input format.
     *
     * @param data an SourceConverter entity that is deserialized from an XML stream
     * @return an instance of SourceConverter
     */
    @POST
    @ConsumeMime({"application/xml", "application/json"})
    public Response post(SourceConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            Sources entity = data.getEntity();
            createEntity(entity);
            persistenceSvc.commitTx();
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
        }
    }

    /**
     * Returns a dynamic instance of SourceResource used for entity navigation.
     *
     * @return an instance of SourceResource
     */
    @Path("{id}/")
    public SourceResource getSourceResource(@PathParam("id")
    String id) {
        return new SourceResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Sources instances
     */
    protected Collection<Sources> getEntities(int start, int max) {
        return PersistenceService.getInstance().createQuery("SELECT e FROM Sources e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Sources entity) {
        PersistenceService.getInstance().persistEntity(entity);
        for (Organizations value : entity.getOrganizationsCollection()) {
            value.setSourceId(entity);
        }
        for (Events value : entity.getEventsCollection()) {
            value.setSourceId(entity);
        }
    }
}
