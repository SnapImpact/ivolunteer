/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.util.List;
import javax.ejb.Local;
import persistence.SourceOrgTypeMap;

/**
 * 
 * @author dave
 */
@Local
public interface SourceOrgTypeMapFacadeLocal {

	void create(SourceOrgTypeMap sourceOrgTypeMap);

	void edit(SourceOrgTypeMap sourceOrgTypeMap);

	void remove(SourceOrgTypeMap sourceOrgTypeMap);

	SourceOrgTypeMap find(Object id);

	List<SourceOrgTypeMap> findAll();

}
