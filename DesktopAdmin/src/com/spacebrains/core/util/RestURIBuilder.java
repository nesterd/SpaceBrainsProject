package com.spacebrains.core.util;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RestURIBuilder {
    public static URI buildURI(String requestString) {
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(BaseParams.BaseURL)
                    .setPath(requestString)
                    .setCharset(StandardCharsets.UTF_8)
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            return uri;
        }
    }
}
