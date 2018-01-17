package com.igate.ai.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.common.util.NamedList;



public class RunUploadFilesForIndex {

	static int fileCount = 0;
	private static final ResourceBundle bundle = ResourceBundle.getBundle("smartsearch");
	//private static final String url=bundle.getString("urlPath");
	private static final String iSpaceDir=bundle.getString("iSpaceDir");
	private static final String iSpaceLink=bundle.getString("iSpaceLink");
	private static final String synonymIndexPath=bundle.getString("synonymIndexPath");
	
	public void runUploadFiles(HttpServletRequest request) throws IOException, SolrServerException
	{
	    File path = new File(iSpaceDir);
	    String link = iSpaceLink;
	    File [] files = path.listFiles();
	    
	    for(int count =0 ; count < files.length ; count ++)
	    {
	    	if(files[count].isDirectory())
	    	{
	    		getFilePaths(files[count].toString() , link+ files[count].toString().substring(files[count].toString().lastIndexOf("/")+1, files[count].toString().length())+"/" ,request);
	    	}
	    	else
	    	{
	    		indexFile(files[count].toString() , link+ files[count].toString().substring(files[count].toString().lastIndexOf("/")+1, files[count].toString().length())+"/",request);
	    	}
	    }
	}
	private String getFilePaths(String dirpath , String link,HttpServletRequest request)
	{
		File path = new File(dirpath);
		File [] files = path.listFiles();
	    for(int count =0 ; count < files.length ; count ++)
	    {
	    	if(files[count].isDirectory())
	    	{
	    		getFilePaths( files[count].toString() , link+ files[count].toString().substring(files[count].toString().lastIndexOf("/")+1, files[count].toString().length())+"/",request);
	    	}
	    	else
	    	{
	    		indexFile(files[count].toString(),link+ files[count].toString().substring(files[count].toString().lastIndexOf("/")+1, files[count].toString().length())+"/",request);
	    	}
	    }
		return null;
	}
	
	
	private void indexFile(String filePath ,String fileURL,HttpServletRequest request)
	{
		fileURL = fileURL.substring(0,fileURL.length()-1);
		fileCount = fileCount +1;
	    try {
	    	String schema = request.getScheme();
			String serverName = request.getServerName();
			int serverPort = request.getServerPort();
			String contextPath = request.getContextPath();
			String url = schema + "://" + serverName + ":" + serverPort + contextPath;
			SolrServer server = new CommonsHttpSolrServer(url);
			ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
            //String  input = files[i].toString();
	           //  Pattern filePattern = Pattern.compile("\\.(doc|docx|ppt|pptx|xls|xlsx|pdf|htm|html)$", Pattern.CASE_INSENSITIVE);
	           // if (filePattern.matcher(input).find())
				String  input = fileURL.substring(fileURL.lastIndexOf("/")+1,fileURL.length());
	            input = input.replaceAll("\\.(doc|docx|ppt|pptx|xls|xlsx|pdf|htm|html)$", "");
				req.addFile(new File(filePath));
	    	    req.setParam("literal.id", "PD_"+fileCount);
	    	    req.setParam("literal.srch_links_name", input);
	    	    req.setParam("literal.srch_links", fileURL);
	    	    req.setParam("literal.srch_tags_auto_suggest", input);
	    	    req.setParam("literal.srch_spell", input);
	    	    req.setParam("literal.srch_summary","policy documents : " +input);
	    	    req.setParam("literal.srch_func", "policy docs");
	    	    req.setParam("literal.srch_catg", "Admin");
	    	    req.setParam("literal.srch_sub_catg", "Admin");
	    	   
	    	    // req.setParam("fmap.content", "srch_summary");
	    	    req.setParam("fmap.Last-Modified", "last_modified");
	    	    req.setParam("uprefix", "ignored_");
	    	    req.setParam("commit", "true");
	    	    NamedList<Object> result;

					result = server.request(req);

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void runSynonymIndex(HttpServletRequest request) throws IOException, SolrServerException
	{
		String schema = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		String url = schema + "://" + serverName + ":" + serverPort + contextPath;
	    SolrServer server = new CommonsHttpSolrServer(url);
	    ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
	    
     	    req.addFile( new File(synonymIndexPath));
	    	    req.setParam("literal.id", "synonyms");
	     	    req.setParam("fmap.content", "srch_spell");
	    	    req.setParam("fmap.Last-Modified", "last_modified");
	    	    req.setParam("uprefix", "ignored_");
	    	    req.setParam("commit", "true");
	    	    NamedList<Object> result = server.request(req);
	}
	
	
	
	
	
}