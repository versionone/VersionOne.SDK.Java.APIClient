package com.versionone.apiclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.versionone.apiclient.exceptions.AttachmentLengthException;
import com.versionone.apiclient.exceptions.ConnectionException;
import com.versionone.apiclient.interfaces.IAPIConnector;
import com.versionone.apiclient.interfaces.IAttachments;

/**
 * @deprecated This class has been deprecated. Please use methods of the Services class instead. 
 */
@Deprecated
public class Attachments implements IAttachments {
	private IAPIConnector _connector;
	private V1Connector _v1connector;

	public Attachments(IAPIConnector connector) {
		_connector = connector;
	}

	public Attachments(V1Connector v1connector) {
		_v1connector = v1connector;
	}

	/**
	 * Getting reader
	 *
	 * @param key
	 *            path to the data on server
	 * @return the stream for reading data
	 * @throws ConnectionException
	 *             if any connection problems occur
	 */
	public InputStream getReader(String key) throws ConnectionException {
		
		if (key != null && key.length() > 0) {
			final String path = key.substring(key.lastIndexOf('/') + 1);

			if (_connector != null) {
				_connector.beginRequest(path, null);
				return _connector.endRequest(path);
			} else if (this._v1connector != null) {
				return _v1connector.getAttachment(path);
			}
		}
		return null;
	}

	/**
	 * Getting writer
	 *
	 * @param key
	 *            path to the data on server
	 * @param contentType
	 *            Content-type of HTTP header
	 * @return the stream for writing data
	 * @throws ConnectionException
	 *             if any connection problems occur
	 */
	public OutputStream getWriter(String key, String contentType) throws ConnectionException {
		if (key != null && key.length() > 0) {
			if (_connector != null) {
				return _connector.beginRequest(key.substring(key.lastIndexOf('/') + 1), contentType);
			} else if (this._v1connector != null) {
				return this._v1connector.beginRequest(key.substring(key.lastIndexOf('/') + 1), contentType);
			}
		}
		return null;
	}

	/**
	 * Setting Writer
	 *
	 * @param key
	 *            path to the data on server
	 * @throws ConnectionException
	 *             if any connection problems occur
	 * @throws AttachmentLengthException
	 *             if connection problem occurs by attachment size is too big
	 */
	public void setWriter(String key) throws ConnectionException, AttachmentLengthException {
		try {
			if (key != null && key.length() > 0) {
				if (_connector != null) {
					_connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
				} else if (this._v1connector != null) {
					this._v1connector.endRequest(key.substring(key.lastIndexOf('/') + 1));
				}
			}
		} catch (ConnectionException e) {
			if (e.getServerResponseCode() == HttpURLConnection.HTTP_ENTITY_TOO_LARGE) {
				throw new AttachmentLengthException(e.getMessage());
			}
			throw e;

		}
	}

}