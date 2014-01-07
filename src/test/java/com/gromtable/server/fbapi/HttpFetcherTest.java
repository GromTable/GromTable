package com.gromtable.server.fbapi;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;

public class HttpFetcherTest extends BaseTest {
  @Test
  public void testGenContent() {
    String url = "https://www.google.com/";
    HttpFetcher fetcher = new HttpFetcher();
    String content = fetcher.genContent(url);
    Assert.assertTrue(content.length() > 0);
  }
}
