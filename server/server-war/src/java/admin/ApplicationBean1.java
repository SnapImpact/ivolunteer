/*
 *  Copyright (c) 2008 Boulder Community Foundation - iVolunteer
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package admin;

import com.sun.rave.web.ui.appbase.AbstractApplicationBean;
import javax.faces.FacesException;

/**
 * <p>
 * Application scope data bean for your application. Create properties here to
 * represent cached data that should be made available to all users and pages in
 * the application.
 * </p>
 * 
 * <p>
 * An instance of this class will be created for you automatically, the first
 * time your application evaluates a value binding expression or method binding
 * expression that references a managed bean using this class.
 * </p>
 * 
 * @version ApplicationBean1.java
 * @version Created on Oct 29, 2008, 12:02:08 PM
 * @author Dave Angulo
 */

public class ApplicationBean1 extends AbstractApplicationBean {
	// <editor-fold defaultstate="collapsed"
	// desc="Managed Component Definition">

	/**
	 * <p>
	 * Automatically managed component initialization. <strong>WARNING:</strong>
	 * This method is automatically generated, so any user-specified code
	 * inserted here is subject to being replaced.
	 * </p>
	 */
	private void _init() throws Exception {
	}

	// </editor-fold>

	/**
	 * <p>
	 * Construct a new application data bean instance.
	 * </p>
	 */
	public ApplicationBean1() {
	}

	/**
	 * <p>
	 * This method is called when this bean is initially added to application
	 * scope. Typically, this occurs as a result of evaluating a value binding
	 * or method binding expression, which utilizes the managed bean facility to
	 * instantiate this bean and store it into application scope.
	 * </p>
	 * 
	 * <p>
	 * You may customize this method to initialize and cache application wide
	 * data values (such as the lists of valid options for dropdown list
	 * components), or to allocate resources that are required for the lifetime
	 * of the application.
	 * </p>
	 */
	@Override
	public void init() {
		// Perform initializations inherited from our superclass
		super.init();
		// Perform application initialization that must complete
		// *before* managed components are initialized
		// TODO - add your own initialiation code here

		// <editor-fold defaultstate="collapsed"
		// desc="Managed Component Initialization">
		// Initialize automatically managed components
		// *Note* - this logic should NOT be modified
		try {
			_init();
		} catch (Exception e) {
			log("ApplicationBean1 Initialization Failure", e);
			throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
		}

		// </editor-fold>
		// Perform application initialization that must complete
		// *after* managed components are initialized
		// TODO - add your own initialization code here
	}

	/**
	 * <p>
	 * This method is called when this bean is removed from application scope.
	 * Typically, this occurs as a result of the application being shut down by
	 * its owning container.
	 * </p>
	 * 
	 * <p>
	 * You may customize this method to clean up resources allocated during the
	 * execution of the <code>init()</code> method, or at any later time during
	 * the lifetime of the application.
	 * </p>
	 */
	@Override
	public void destroy() {
	}

	/**
	 * <p>
	 * Return an appropriate character encoding based on the <code>Locale</code>
	 * defined for the current JavaServer Faces view. If no more suitable
	 * encoding can be found, return "UTF-8" as a general purpose default.
	 * </p>
	 * 
	 * <p>
	 * The default implementation uses the implementation from our superclass,
	 * <code>AbstractApplicationBean</code>.
	 * </p>
	 */
	@Override
	public String getLocaleCharacterEncoding() {
		return super.getLocaleCharacterEncoding();
	}
}
