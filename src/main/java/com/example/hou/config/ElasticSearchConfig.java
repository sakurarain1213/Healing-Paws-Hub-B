package com.example.hou.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.expression.Lists;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.rest.uris}")
    private String hosts;

    @Value("${spring.elasticsearch.rest.host}")
    private String host;

    @Value("${spring.elasticsearch.rest.port}")
    private Integer port;

    @Value("${spring.elasticsearch.rest.username}")
    private String name;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @Value("${spring.elasticsearch.rest.enable}")
    private boolean enable;


    //配置类的依赖和版本bug  再调

    //注入IOC容器
    @Bean
    public ElasticsearchClient elasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(null);
        if (enable){
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            //设置账号密码
            credentialsProvider.setCredentials(
                    AuthScope.ANY, new UsernamePasswordCredentials(name, password));

//        RestClients restClients =
            RestClient restClient = RestClient.builder(new HttpHost(host, port))
                    .setHttpClientConfigCallback(httpClientBuilder->httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)).build();

            ElasticsearchTransport transport = new RestClientTransport(restClient,new JacksonJsonpMapper());
            // And create the API client
            client = new ElasticsearchClient(transport);
        }
        return client;

    }



    /*
    @Bean
    public ElasticsearchClient docqaElasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(name, password));
        List<HttpHost> httpHosts = new ArrayList<>();
        String[] split = hosts.split(",");
        for (int i = 0; i < split.length; i++) {
            httpHosts.add(HttpHost.create(split[i]));
        }
        HttpHost[] httpHosts1 = httpHosts.toArray(new HttpHost[0]);
        RestClient client = RestClient
                .builder(httpHosts1)
                .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider).setKeepAliveStrategy((response, context) -> 180 * 1000))
                .build();

        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
  */

}






/*

package com.example.hou.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;


/**
 * @program: Healing-Paws-Hub-B
 * @description: //   有很多不同配置写法
 * @author: 作者
 * @create: 2024-03-14 16:58

@Configuration
@Slf4j
public class ElasticSearchConfig {
    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.port}")
    private int port;

    @Value("${spring.elasticsearch.hosts}")
    private String hosts;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Value("${spring.elasticsearch.apikey}")
    private String apikey;

    /**
     * 单节点没密码连接
     *
     * @return

    @Bean
    public ElasticsearchClient client() {
        ElasticsearchTransport transport = null;
        // 不是集群时
        if (hosts.split(",").length == 1) {
            // 无账号、密码
            if (org.apache.commons.lang3.StringUtils.isEmpty(username) && org.apache.commons.lang3.StringUtils.isEmpty(password)) {
                RestClient client = RestClient.builder(new HttpHost(host, port, "http")).build();
                transport = new RestClientTransport(client, new JacksonJsonpMapper());
            } else {
                // 账号密码的配置
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                // 自签证书的设置，并且还包含了账号密码
                RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setSSLContext(buildSSLContext())
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setDefaultHeaders(
                                Stream.of(new BasicHeader(
                                        HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())).collect(toList())
                        ).addInterceptorLast(
                                (HttpResponseInterceptor)
                                        (response, context) ->
                                                response.addHeader("X-Elastic-Product", "Elasticsearch"));

                RestClient client = RestClient.builder(new HttpHost(host, port, "http"))
                        .setHttpClientConfigCallback(callback)
                        .build();
                transport = new RestClientTransport(client, new JacksonJsonpMapper());
            }
        } else {
            // 无账号、密码
            if (org.apache.commons.lang3.StringUtils.isEmpty(username) && org.apache.commons.lang3.StringUtils.isEmpty(password)) {
                transport = getElasticsearchTransport(toHttpHost());
            } else {
                transport = getElasticsearchTransport(username, password, toHttpHost());
            }
        }
        return new ElasticsearchClient(transport);
    }

    /**
     * 多接点账号密码连接
     *
     * @return

    @Bean
    public ElasticsearchClient clientByPwd() {
        ElasticsearchTransport transport = null;
        // 不是集群时
        if (hosts.split(",").length == 1) {
            // 无账号、密码
            if (org.apache.commons.lang3.StringUtils.isEmpty(username) && org.apache.commons.lang3.StringUtils.isEmpty(password)) {
                RestClient client = RestClient.builder(new HttpHost(host, port, "http")).build();
                transport = new RestClientTransport(client, new JacksonJsonpMapper());
            } else {
                // 账号密码的配置
                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                // 自签证书的设置，并且还包含了账号密码
                RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setSSLContext(buildSSLContext())
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setDefaultHeaders(
                                Stream.of(new BasicHeader(
                                        HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())).collect(toList())
                        ).addInterceptorLast(
                                (HttpResponseInterceptor)
                                        (response, context) ->
                                                response.addHeader("X-Elastic-Product", "Elasticsearch"));

                RestClient client = RestClient.builder(new HttpHost(host, port, "http"))
                        .setHttpClientConfigCallback(callback)
                        .build();
                transport = new RestClientTransport(client, new JacksonJsonpMapper());
            }
        } else {
            // 无账号、密码
            if (org.apache.commons.lang3.StringUtils.isEmpty(username) && org.apache.commons.lang3.StringUtils.isEmpty(password)) {
                transport = getElasticsearchTransport(toHttpHost());
            } else {
                transport = getElasticsearchTransport(username, password, toHttpHost());
            }
        }
        return new ElasticsearchClient(transport);
    }

    /**
     * ES自签证书连接
     *
     * @return

    @Bean
    public ElasticsearchClient clientByApiKey() {
        ElasticsearchTransport transport = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(apikey)) {
            transport = getElasticsearchTransport(apikey, toHttpHost());
        }
        return new ElasticsearchClient(transport);
    }


    private HttpHost[] toHttpHost() {
        if (!StringUtils.hasLength(hosts)) {
            throw new RuntimeException("invalid elasticsearch configuration");
        }
        String[] hostArray = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[hostArray.length];
        HttpHost httpHost;
        for (int i = 0; i < hostArray.length; i++) {
            String[] strings = hostArray[i].split(":");
            httpHost = new HttpHost(strings[0], Integer.parseInt(strings[1]), "http");
            httpHosts[i] = httpHost;
        }
        return httpHosts;
    }

    private static ElasticsearchTransport getElasticsearchTransport(String username, String password, HttpHost... hosts) {
        // 账号密码的配置
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // 自签证书的设置，并且还包含了账号密码
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultCredentialsProvider(credentialsProvider)
                .setDefaultHeaders(
                        Stream.of(new BasicHeader(
                                HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())).collect(toList())
                ).addInterceptorLast(
                        (HttpResponseInterceptor)
                                (response, context) ->
                                        response.addHeader("X-Elastic-Product", "Elasticsearch"))
                .addInterceptorLast((HttpResponseInterceptor) (response, context)
                        -> response.addHeader("X-Elastic-Product", "Elasticsearch"));
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(callback)
                .build();

        return new RestClientTransport(client, new JacksonJsonpMapper());
    }

    private static ElasticsearchTransport getElasticsearchTransport(HttpHost... hosts) {
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .build();

        return new RestClientTransport(client, new JacksonJsonpMapper());
    }

    private static SSLContext buildSSLContext() {
        ClassPathResource resource = new ClassPathResource("es01.crt");
        SSLContext sslContext = null;
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream is = resource.getInputStream()) {
                trustedCa = factory.generateCertificate(is);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
            sslContext = sslContextBuilder.build();
        } catch (CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException |
                 KeyManagementException e) {
            log.error("ES连接认证失败", e);
        }
        return sslContext;
    }

    private static ElasticsearchTransport getElasticsearchTransport(String apiKey, HttpHost... hosts) {
        // 将ApiKey放入header中
        Header[] headers = new Header[]{new BasicHeader("Authorization", "ApiKey " + apiKey)};

        // es自签证书的设置
        RestClientBuilder.HttpClientConfigCallback callback = httpAsyncClientBuilder -> httpAsyncClientBuilder
                .setSSLContext(buildSSLContext())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setDefaultHeaders(
                        Stream.of(new BasicHeader(
                                HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())).collect(toList())
                ).addInterceptorLast(
                        (HttpResponseInterceptor)
                                (response, context) ->
                                        response.addHeader("X-Elastic-Product", "Elasticsearch"));
        // 用builder创建RestClient对象
        RestClient client = RestClient
                .builder(hosts)
                .setHttpClientConfigCallback(callback)
                .setDefaultHeaders(headers)
                .build();

        return new RestClientTransport(client, new JacksonJsonpMapper());
    }
}





*/