Estimation made easy
====================

Estimation made easy is a Java application written using the Play! Framwork (http://www.playframework.org/).

A simple application by Phil Wilson, 2010
MIT licensed.
http://philwilson.org

Getting started
---------------

1) Make sure you have a Java Development Kit installed (I use Java 6)

2) Download Play 1.0.1 from http://download.playframework.org/releases/play-1.0.1.zip

3) Unzip Play

4) Place the estimationmadeeasy/ directory into your play/ directory

5) Open a command prompt and cd to the play/ directory

6) Run: play run estimationmadeeasy

7) Visit http://localhost:9000


To deploy to Google App Engine
------------------------------

There are some generic instructions on http://philwilson.org/blog/2009/12/setting-up-the-play-framework-on-google-app-engine


Technical stuff
---------------

Estimation made easy makes no use of a database, storing everything in the Cache. This is deliberate.

It is designed to be deployed onto Google App Engine (http://appengine.google.com/) where the Cache is provided by memcached.

There are the beginnings of Jira integration in jira/XmlRpcJiraClient.java