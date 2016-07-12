package com.buildit.crawler;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Praveen.R on 12/07/2016.
 */
public class CrawlerTest {

    @Mock
    JsoupConnection jsoupConnection;

    @InjectMocks
    Crawler crawler;

    private String URL ;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        this.URL = "http://www.lastmilelink.com";
        crawler = new Crawler(jsoupConnection);
    }

    @Test
    public void testIOException()throws IOException
    {
        Mockito.when(jsoupConnection.getDocumentHelper(URL)).thenThrow(IOException.class);
        Map<String,List<String>> actualMap = crawler.getSiteMap(this.URL);
        List<String> output = actualMap.get(this.URL);
        assertTrue(output.isEmpty());
    }

    @Test
    public void testURLNotDuplicatedInSiteMap()throws IOException
    {
        Document document = Jsoup.parse(getSelfLinkPage());
        Mockito.when(jsoupConnection.getDocumentHelper(URL)).thenReturn(document);

        List<String> expectedList = new ArrayList<String>();
        expectedList.add(this.URL);
        Map<String,List<String>> expectedMapResult = new HashMap<String,List<String>>();
        expectedMapResult.put(this.URL,expectedList);
        Map<String,List<String>> actual = crawler.getSiteMap(this.URL);
        assertEquals(expectedMapResult,actual);
    }

    @Test
    public void testReturnSiteURLs()throws IOException
    {
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("http://www.lastmilelink.com/home");
        expectedResult.add("http://www.lastmilelink.com/aboutus");
        expectedResult.add("https://www.lastmilelink.com/contactus");

        Document document = Jsoup.parse(getLastMileLinksMainPage());
        Mockito.when(jsoupConnection.getDocumentHelper(Mockito.anyString())).thenReturn(document);

        Map<String,List<String>> actualMap = crawler.getSiteMap(this.URL);
        List<String> siteURLs = actualMap.get(this.URL);
        assertEquals(expectedResult,siteURLs);
    }

    @Test
    public void testReturnSiteMap()throws IOException
    {
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("https://www.twitter.com/lastmilelink");
        expectedResult.add("mailto:hello@lastmaillink.com");
        expectedResult.add("https://www.linkedin.com/company/10475014");
        expectedResult.add("https://www.youtube.com/embed/I4uz6XRto5M");

        Map<String,List<String>> expectedMapResult = new HashMap<String,List<String>>();
        expectedMapResult.put(this.URL,expectedResult);
        Document document = Jsoup.parse(getLastMileLinksHome());
        Mockito.when(jsoupConnection.getDocumentHelper(URL)).thenReturn(document);
        Map<String,List<String>> actualMap = crawler.getSiteMap(this.URL);
        assertEquals(expectedMapResult,actualMap);
    }

    private String getSelfLinkPage()
    {
        return "<html><head><title>Self link page</title></head><body>blank page<a href=\"http://www.lastmilelink.com\">Home</a></body></html>";
    }
    private String getLastMileLinksMainPage()
    {
        String html =  "<html><head><title> Last Mile Links</title></head><body><ul><li><a href=\"http://www.lastmilelink.com/home\">HOME</a></li>"+
                "<li><a href=\"http://www.lastmilelink.com/aboutus\">ABOUT US</a></li>"+
                "<li><a href=\"https://www.lastmilelink.com/contactus\">CONTACT US</a></li></ul></body></html>";
        return html;
    }

    private String getLastMileLinksHome()
    {
        String html=  "<html><head><title> Last Mile Links Social </title></head><body><ul><li><a href=\"https://www.twitter.com/lastmilelink\">Twitter" +
                "</a></li><li><a href=\"mailto:hello@lastmaillink.com\">Email</a></li>"+
                "<li><a href=\"https://www.linkedin.com/company/10475014\">Linked in</a></li>" +
                "<li><a href=\"https://www.youtube.com/embed/I4uz6XRto5M\">Video</a></li>"+
                "</ul></body></html>";
        return html;
    }
}
