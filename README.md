# Crawler

[![Build Status](https://travis-ci.org/pravreddy/crawler_java.svg)](https://travis-ci.org/pravreddy/crawler_java)
[![Coverage Status](https://coveralls.io/repos/github/pravreddy/crawler_java/badge.svg?branch=master)](https://coveralls.io/github/pravreddy/crawler_java?branch=master)


About crawler
-------------
Given a starting URL to crawler - it should visit all pages within the 
domain, but not follow the links to external sites such as Google or Twitter.
The crawler is limited to one domain.
The output is a simple site map, showing links to other pages under the 
same domain, links to static content such as images, and to external URLs.

Build:
------
use 'mvn clean' to clean the project.
And 'mvn install' to build and run the unit test cases.

Coverage Report:
----------------
Once 'mvn install' is run can find the coverage report,
under :   'crawler_java/target/site/cobertura/index.html'