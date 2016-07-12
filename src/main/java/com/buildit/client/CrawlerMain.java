package com.buildit.client;

import com.buildit.crawler.JsoupConnection;
import com.buildit.crawler.Crawler;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Praveen.R on 12/07/2016.
 */
public class CrawlerMain {
    public static void main(String[] args)
    {
        System.out.println("Enter URL of the domain");
        Scanner sc = new Scanner(System.in);
        String URL = sc.next();

        JsoupConnection jsoupConnection = new JsoupConnection();
        Crawler crawler = new Crawler(jsoupConnection);
        try {
            Map<String,List<String>> siteMap= crawler.getSiteMap(URL);
            for (Map.Entry<String, List<String>> entry : siteMap.entrySet()) {
                System.out.println("Parent URL : " + entry.getKey());
                System.out.println("Child URLS:");
                for(String url :entry.getValue())
                {
                    System.out.println(url);
                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
