/*
 *  DistancesResource
 *
 * Created on October 24, 2008, 9:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import persistence.Distances;
import persistence.Filter;
import converter.DistancesConverter;
import converter.DistanceConverter;


/**
 *
 * @author dave
 */

@Path("/distances/")
public class DistancesResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of DistancesResource */
    public DistancesResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public DistancesResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Distances instance in XML format.
     *
     * @return an instance of DistancesConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public DistancesConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new DistancesConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Distances using XML as the input format.
     *
     * @param data an DistanceConverter entity that is deserialized from an XML stream
     * @return an instance of DistanceConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(DistanceConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Distances entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of DistanceResource used for entity navigation.
     *
     * @return an instance of DistanceResource
     */
    @Path("{id}/")
    public DistanceResource getDistanceResource(@PathParam("id")
    String id) {
        return new DistanceResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Distances instances
     */
    protected Collection<Distances> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Distances e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Distances entity) {
        new PersistenceServiceBean().persistEntity(entity);
        for (Filter value : entity.getFilterCollection()) {
            value.setDistanceId(entity);
        }
    }
}
