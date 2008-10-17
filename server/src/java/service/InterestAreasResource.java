/*
 *  InterestAreasResource
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
    @ProduceMime({"application/xml", "application/json"})
    public InterestAreasConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new InterestAreasConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            PersistenceService.getInstance().close();
        }
    }

    /**
     * Post method for creating an instance of InterestAreas using XML as the input format.
     *
     * @param data an InterestAreaConverter entity that is deserialized from an XML stream
     * @return an instance of InterestAreaConverter
     */
    @POST
    @ConsumeMime({"application/xml", "application/json"})
    public Response post(InterestAreaConverter data) {
        PersistenceService persistenceSvc = PersistenceService.getInstance();
        try {
            persistenceSvc.beginTx();
            InterestAreas entity = data.getEntity();
            createEntity(entity);
            persistenceSvc.commitTx();
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            persistenceSvc.close();
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
        return PersistenceService.getInstance().createQuery("SELECT e FROM InterestAreas e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(InterestAreas entity) {
        PersistenceService.getInstance().persistEntity(entity);
    }
}
