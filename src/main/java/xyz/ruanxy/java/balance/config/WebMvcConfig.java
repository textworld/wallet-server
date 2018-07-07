package xyz.ruanxy.java.balance.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${http.connectTimeout}")
    private int connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private int socketTimeout;

    @Value("${http.maxTotal}")
    private int maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private int defaultMaxPerRoute;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    public static final String BASE_PATH = "api/v1/";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(maxTotal);
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(manager);
        return builder.build();
    }

    @Bean
    public RequestConfig requestConfig(){
        RequestConfig.Builder builder = RequestConfig.custom();
        RequestConfig requestConfig = builder
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
        return requestConfig;
    }






}

