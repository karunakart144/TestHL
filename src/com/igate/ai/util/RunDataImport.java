package com.igate.ai.util;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.common.params.ModifiableSolrParams;

public class RunDataImport {
	
	private static final ResourceBundle bundle = ResourceBundle.getBundle("smartsearch");
	private static final String urlPath=bundle.getString("urlPath");
  public  void runImport(String url, String serverAddr) throws MalformedURLException, SolrServerException {

	  /*Properties systemProps = System.getProperties();
	  systemProps.put("javax.net.ssl.trustStore","jssecacerts");
	  System.setProperties(systemProps);*/
    // http://localhost:8983/solr/spellCheckCompRH?q=epod&spellcheck=on&spellcheck.build=true
    CommonsHttpSolrServer server = new CommonsHttpSolrServer(urlPath);
    ModifiableSolrParams params = new ModifiableSolrParams();
    params.set("command", "full-import");
    QueryRequest request = new QueryRequest(params);
    request.setPath("/dataconfig_16");
    try {
		server.request(request);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
}