/*
 *  InterestAreasResource
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
import converter.InterestAreasConverter;
import converter.InterestAreaConverter;
import persistence.InterestAreas;


/**
 *
 * @author dave
 */

@Path("/interestAreas/")
public class InterestAreasResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of InterestAreasResource */
    public InterestAreasResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public InterestAreasResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of InterestAreas instance in XML format.
     *
     * @return an instance of InterestAreasConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public InterestAreasConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new InterestAreasConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of InterestAreas using XML as the input format.
     *
     * @param data an InterestAreaConverter entity that is deserialized from an XML stream
     * @return an instance of InterestAreaConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(InterestAreaConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            InterestAreas entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of InterestAreaResource used for entity navigation.
     *
     * @return an instance of InterestAreaResource
     */
    @Path("{id}/")
    public InterestAreaResource getInterestAreaResource(@PathParam("id")
    String id) {
        return new InterestAreaResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of InterestAreas instances
     */
    protected Collection<InterestAreas> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM InterestAreas e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(InterestAreas entity) {
        new PersistenceServiceBean().persistEntity(entity);
    }
}
