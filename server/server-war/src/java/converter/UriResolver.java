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

package converter;

import javax.ws.rs.WebApplicationException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import javax.xml.bind.JAXBContext;

/**
 * Utility class for resolving an uri into an entity.
 * 
 * @author Dave Angulo
 */
public class UriResolver {

	private static ThreadLocal<UriResolver>	instance	= new ThreadLocal<UriResolver>() {
															protected UriResolver initialValue() {
																return new UriResolver();
															}
														};

	private boolean							inProgress	= false;

	private UriResolver() {
	}

	/**
	 * Returns an instance of UriResolver.
	 * 
	 * @return an instance of UriResolver.
	 */
	public static UriResolver getInstance() {
		return instance.get();
	}

	private static void removeInstance() {
		instance.remove();
	}

	/**
	 * Returns the entity associated with the given uri.
	 * 
	 * @param type
	 *            the converter class used to unmarshal the entity from XML
	 * @param uri
	 *            the uri identifying the entity
	 * @return the entity associated with the given uri
	 */
	public <T> T resolve(Class<T> type, URI uri) {
		if (inProgress)
			return null;

		inProgress = true;

		try {
			if (uri == null) {
				throw new RuntimeException("No uri specified in a reference.");
			}

			URL url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() == 200) {
				JAXBContext context = JAXBContext.newInstance(type);

				return (T) context.createUnmarshaller().unmarshal(conn.getInputStream());
			} else {
				throw new WebApplicationException(new Throwable("Resource for " + uri
						+ " does not exist."), 404);
			}
		} catch (WebApplicationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		} finally {
			removeInstance();
		}
	}
}