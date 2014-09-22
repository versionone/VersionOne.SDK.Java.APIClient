package com.versionone.apiclient.fitnesse;

import java.io.Reader;

import com.versionone.apiclient.ClientConfiguration;
import com.versionone.apiclient.V1APIConnector;

/**
 * Fitnesse fixture to test basic authentication
 * @author jerry
 *
 */
public class BasicServerConnection extends fit.ColumnFixture {
	
	public String url = "";
	public String user = "";
	public String password = "";
	public boolean integrated  = false;
	
	public boolean valid() throws Exception {
		V1APIConnector testMe = null;
		if(integrated) {
			testMe = new V1APIConnector(url);
		}
		else {
            ClientConfiguration config = new ClientConfiguration(url, user, password);
            testMe = new V1APIConnector(url, config);
		}
		Reader temp = null;
		try {
			temp = testMe.getData();			
		}
		finally {
			if(null != temp){try{temp.close();}catch(Exception e){}}
		}
		return (temp != null);
	}
}
