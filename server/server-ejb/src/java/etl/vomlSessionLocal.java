/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package etl;

import javax.ejb.Local;

/**
 * 
 * @author dave
 */
@Local
public interface vomlSessionLocal {

	void loadVoml();

}
