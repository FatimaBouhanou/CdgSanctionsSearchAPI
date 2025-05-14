package com.example.demo.config;

import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;


public class RegisterConfiguration extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedToLocalhost()
                .usingSsl(buildSSLContext())
                .withBasicAuth(username,password)
                .build();

    }

    private static SSLContext buildSSLContext() {
        try{
            return new SSLContextBuilder().
                    loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
