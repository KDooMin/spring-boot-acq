package com.acq.collection.acqcollectionbook.config.web;

import com.acq.collection.acqcollectionbook.common.converter.HtmlCharacterEscapes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import org.egovframe.rte.ptl.mvc.filter.HTMLTagFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.*;

import javax.servlet.Filter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan(basePackages = "com.acq.collection.acqcollectionbook")
public class WebConfig implements WebMvcConfigurer {
    private static final String[] RESOURCES_LOCATIONS;
    private static final String[] RESOURCES_PATTERNS;

    static {
        RESOURCES_LOCATIONS = new String[] {
                "classpath:/static/images/",
                "classpath:/static/upload/"
        };

        RESOURCES_PATTERNS = new String[] {
                "/images/**",
                "/upload/**"
        };
    }

    @Bean
    public FilterRegistrationBean<Filter> getFilterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HTMLTagFilter());
        registrationBean.setFilter(new XssEscapeServletFilter());
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(this.htmlEscapingConverter());
    }

    private HttpMessageConverter<?> htmlEscapingConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());

        MappingJackson2HttpMessageConverter htmlEscapingConverter = new MappingJackson2HttpMessageConverter();
        htmlEscapingConverter.setObjectMapper(objectMapper);

        return htmlEscapingConverter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(RESOURCES_PATTERNS)
                .addResourceLocations(RESOURCES_LOCATIONS)
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePublic())
                .resourceChain(false)
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver().addVersionStrategy(new ContentVersionStrategy(), "/**"))
                .addTransformer(new CssLinkResourceTransformer());
    }

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name()
                );
    }
}
