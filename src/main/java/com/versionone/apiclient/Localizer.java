package com.versionone.apiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Localizer API on the VersionOne Server
 * @author jerry
 *
 */
public class Localizer implements ILocalizer {

	private Map<String, String> _map = new HashMap<String, String>();
	private IAPIConnector _connector;

	/**
	 * Create with a connection
	 * @param connector
	 */
	public Localizer(IAPIConnector connector)
	{
		_connector = connector;
	}

	/**
	 * Resolve the key to it's localized value
	 * @param key
	 * @return Localized String value
	 */
	public String resolve(String key)
	{
		if(!_map.containsKey(key)) {
			Reader reader = null;
			try {
				reader = _connector.getData("?"+key);
				loadData(key, reader);
			} catch (IOException e) {
				return key;
            } catch (ConnectionException e) {
                return key;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
		return _map.get(key);
	}

	private void loadData(String key, Reader reader) throws IOException {
		StringBuffer result = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(reader);
		String temp = bufferedReader.readLine();
		while(null != temp) {
			result.append(temp);
			temp = bufferedReader.readLine();
		}
		_map.put(key, result.toString());
	}

}
