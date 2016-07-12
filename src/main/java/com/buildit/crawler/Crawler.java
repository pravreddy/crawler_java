package com.buildit.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Praveen.R on 12/07/2016.
 */
public class Crawler {

    private String givenURL;
    private JsoupConnection jsoupConnection;
    private Map<String,List<String>> siteMap= new HashMap<String,List<String>>();
    private static String  HTTP ="http";
    private static String HTTPS="https";
    private static String WWW="www.";
    private static String ABS_HREF="abs:href";
    private static String A_HREF="a[href]";


    public Crawler(JsoupConnection jsoupConnection)
    {
        this.jsoupConnection = jsoupConnection;
    }

    public Map<String, List<String>> getSiteMap(String URL)
            throws IOException {
        this.givenURL = URL;
        buildSiteURLMap(URL);
        return siteMap;
    }

    private void buildSiteURLMap(String siteURL) throws IOException
    {
        if(siteMap.get(siteURL) == null) {
            List<String>subURLs = getSubURLs(siteURL);
            siteMap.put(siteURL,subURLs);
            for(String url:subURLs)
            {
                String[] splitURL = givenURL.split(WWW);
                if((url.startsWith(HTTP)||url.startsWith(HTTPS))&& url.contains(splitURL[1])){
                    buildSiteURLMap(url);
                }
            }
        }
    }

    private List<String> getSubURLs(String parentURL)
    {
        List<String> siteURLs = new ArrayList<String>();
        try {
            Elements elements = getElements(parentURL);
            for (Element link : elements) {
                siteURLs.add(link.attr(ABS_HREF));
            }
            return siteURLs;
        }catch(IOException e){
            System.out.println("Error connecting to URL \t"+parentURL+"\t"+e.getMessage());
            return siteURLs;

        }
    }

    private Elements getElements(String parentURL)throws IOException
    {
        Document document = jsoupConnection.getDocumentHelper(parentURL);
        return document.select(A_HREF);
    }
}


