/*
 *  FiltersResource
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
import converter.FiltersConverter;
import converter.FilterConverter;
import persistence.Filter;


/**
 *
 * @author dave
 */

@Path("/filters/")
public class FiltersResource {
    @Context
    private UriInfo context;
  
    /** Creates a new instance of FiltersResource */
    public FiltersResource() {
    }

    /**
     * Constructor used for instantiating an instance of dynamic resource.
     *
     * @param context HttpContext inherited from the parent resource
     */
    public FiltersResource(UriInfo context) {
        this.context = context;
    }

    /**
     * Get method for retrieving a collection of Filter instance in XML format.
     *
     * @return an instance of FiltersConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public FiltersConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max) {
        try {
            return new FiltersConverter(getEntities(start, max), context.getAbsolutePath());
        } finally {
            
        }
    }

    /**
     * Post method for creating an instance of Filter using XML as the input format.
     *
     * @param data an FilterConverter entity that is deserialized from an XML stream
     * @return an instance of FilterConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(FilterConverter data) {
        PersistenceServiceBean persistenceSvc = new PersistenceServiceBean();
        try {
            
            Filter entity = data.getEntity();
            createEntity(entity);
            
            return Response.created(context.getAbsolutePath().resolve(entity.getId() + "/")).build();
        } finally {
            
        }
    }

    /**
     * Returns a dynamic instance of FilterResource used for entity navigation.
     *
     * @return an instance of FilterResource
     */
    @Path("{id}/")
    public FilterResource getFilterResource(@PathParam("id")
    String id) {
        return new FilterResource(id, context);
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Filter instances
     */
    protected Collection<Filter> getEntities(int start, int max) {
        return new PersistenceServiceBean().createQuery("SELECT e FROM Filter e").setFirstResult(start).setMaxResults(max).getResultList();
    }

    /**
     * Persist the given entity.
     *
     * @param entity the entity to persist
     */
    protected void createEntity(Filter entity) {
        new PersistenceServiceBean().persistEntity(entity);
    }
}
