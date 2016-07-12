package com.buildit.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;

/**
 * Created by Praveen.R on 12/07/2016.
 */
public class JsoupConnection {

    public Document getDocumentHelper(String parentURL) throws IOException {
        return Jsoup.connect(parentURL).get();
    }
}
