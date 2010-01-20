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

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.model.SingleSelectOptionsList;
import etl.geocodeSessionLocal;
import etl.locationencoderSessionLocal;
import etl.vomlSessionLocal;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.event.ValueChangeEvent;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.
 * </p>
 * 
 * @version Admin.java
 * @version Created on Oct 29, 2008, 12:02:09 PM
 * @author Dave Angulo
 */

public class Admin extends AbstractPageBean {
	@EJB
	private vomlSessionLocal	vomlSessionBean;
	@EJB
	private locationencoderSessionLocal	locationencoderSessionBean;
	@EJB
	private geocodeSessionLocal	geocoderSessionBean;
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
		radioButtonGroup1DefaultOptions.setOptions(new com.sun.webui.jsf.model.Option[] {
				new com.sun.webui.jsf.model.Option("file", "Test File"),
				new com.sun.webui.jsf.model.Option("server", "Server") });
		radioButtonGroup1DefaultOptions.setSelectedValue("file");
		radioButtonGroup2DefaultOptions.setOptions(new com.sun.webui.jsf.model.Option[] {
				new com.sun.webui.jsf.model.Option("encode", "Encode Locations"),
				new com.sun.webui.jsf.model.Option("dummy", "Nothing") });
		radioButtonGroup2DefaultOptions.setSelectedValue("encode");
	}

    private String latitude = "";

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    private String longitude = "";

    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }

    private SingleSelectOptionsList	radioButtonGroup1DefaultOptions	= new SingleSelectOptionsList();
	private SingleSelectOptionsList	radioButtonGroup2DefaultOptions	= new SingleSelectOptionsList();

	public SingleSelectOptionsList getRadioButtonGroup1DefaultOptions() {
		return radioButtonGroup1DefaultOptions;
	}

	public void setRadioButtonGroup1DefaultOptions(SingleSelectOptionsList ssol) {
		this.radioButtonGroup1DefaultOptions = ssol;
	}
	public SingleSelectOptionsList getRadioButtonGroup2DefaultOptions() {
		return radioButtonGroup2DefaultOptions;
	}

	public void setRadioButtonGroup2DefaultOptions(SingleSelectOptionsList ssol) {
		this.radioButtonGroup2DefaultOptions = ssol;
	}

	// </editor-fold>

	/**
	 * <p>
	 * Construct a new Page bean instance.
	 * </p>
	 */
	public Admin() {
	}

	/**
	 * <p>
	 * Callback method that is called whenever a page is navigated to, either
	 * directly via a URL, or indirectly via page navigation. Customize this
	 * method to acquire resources that will be needed for event handlers and
	 * lifecycle methods, whether or not this page is performing post back
	 * processing.
	 * </p>
	 * 
	 * <p>
	 * Note that, if the current request is a postback, the property values of
	 * the components do <strong>not</strong> represent any values submitted
	 * with this request. Instead, they represent the property values that were
	 * saved for this view when it was rendered.
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
			log("Admin Initialization Failure", e);
			throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
		}

		// </editor-fold>
		// Perform application initialization that must complete
		// *after* managed components are initialized
		// TODO - add your own initialization code here
	}

	/**
	 * <p>
	 * Callback method that is called after the component tree has been
	 * restored, but before any event processing takes place. This method will
	 * <strong>only</strong> be called on a postback request that is processing
	 * a form submit. Customize this method to allocate resources that will be
	 * required in your event handlers.
	 * </p>
	 */
	@Override
	public void preprocess() {
	}

	/**
	 * <p>
	 * Callback method that is called just before rendering takes place. This
	 * method will <strong>only</strong> be called for the page that will
	 * actually be rendered (and not, for example, on a page that handled a
	 * postback and then navigated to a different page). Customize this method
	 * to allocate resources that will be required for rendering this page.
	 * </p>
	 */
	@Override
	public void prerender() {
	}

	/**
	 * <p>
	 * Callback method that is called after rendering is completed for this
	 * request, if <code>init()</code> was called (regardless of whether or not
	 * this was the page that was actually rendered). Customize this method to
	 * release resources acquired in the <code>init()</code>,
	 * <code>preprocess()</code>, or <code>prerender()</code> methods (or
	 * acquired during execution of an event handler).
	 * </p>
	 */
	@Override
	public void destroy() {
	}

	/**
	 * <p>
	 * Return a reference to the scoped data bean.
	 * </p>
	 * 
	 * @return reference to the scoped data bean
	 */
	protected SessionBean1 getSessionBean1() {
		return (SessionBean1) getBean("SessionBean1");
	}

	/**
	 * <p>
	 * Return a reference to the scoped data bean.
	 * </p>
	 * 
	 * @return reference to the scoped data bean
	 */
	protected RequestBean1 getRequestBean1() {
		return (RequestBean1) getBean("RequestBean1");
	}

	/**
	 * <p>
	 * Return a reference to the scoped data bean.
	 * </p>
	 * 
	 * @return reference to the scoped data bean
	 */
	protected ApplicationBean1 getApplicationBean1() {
		return (ApplicationBean1) getBean("ApplicationBean1");
	}

	public void radioButtonGroup1_processValueChange(ValueChangeEvent vce) {
		vomlSessionBean.loadVoml();
	}
	public void radioButtonGroup2_processValueChange(ValueChangeEvent vce) {
		locationencoderSessionBean.updateLocationTableLatLon();
	}
    public void encodeAddress_processValueChange(ValueChangeEvent vce) {
        persistence.Location loc = new persistence.Location();
        loc.setStreet(vce.getNewValue().toString());
		geocoderSessionBean.encodeAddress(loc);
        latitude = loc.getLatitude();
        if (latitude == null) latitude = "";
        longitude = loc.getLongitude();
        if (longitude == null) longitude = "";
	}
}
