package org.springstarter;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationPid;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.context.ContextConfiguration;

/**
 * <p>
 * Main class for the Spring Starter Project
 * </p>
 * @FileName    : SpringStarterApplication.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 23.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@SpringBootApplication
//@ComponentScan(basePackages = { "com.spring" }, useDefaultFilters=false, includeFilters = { 
//		@Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class) })
//@ComponentScan(basePackages = { "org.springstarter.dao" }, includeFilters = { 
//		@Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Service.class) })
//@EnableAutoConfiguration(
//        exclude = {
//                org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class})
//@ContextConfiguration(locations = { "classpath:/src/main/resources/applicationContext.xml" })
public class SpringStarterApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringStarterApplication.class);
	
    public static void main(String[] args) {
    	LOGGER.info("The Spring Starter application started.");
    	
        SpringApplication.run(SpringStarterApplication.class, args);
    }
    
    /**
     * <p>
     * Create dataSource bean for JPA
     * </p>
     * @return dataSource
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    /**
     * <p>
     * Leave process id into application.pid file. 
     * </p>
     * @throws IOException
     */
    @PostConstruct
    private void pid() throws IOException {
        File file = new File("application.pid");
        new ApplicationPid().write(file);
        file.deleteOnExit();
    }
    
    /**
     * <p>
     * Leave shutdown logs when the service shutdown.
     * </p>
     */
    @PreDestroy
    public void shutdownProc() {
    	LOGGER.info("The Spring Starter application shutdown.");
    }
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringStarterApplication.class);
	}
    
    /**
     * <p>
     * This bean support customized HTTP or HTTPS web service for apache tomcat
     * If you want to change key info for https, then you may modify application.yml
     * WARNING : The keystore file was for testing. You must re-make keystore or 
     * 			issue SSL certificate from certificate authority.
     * </p>
     * @param port
     * @param keystoreFile
     * @param keystorePass
     * @param keyPass
     * @return
     * @throws Exception
     */
    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer(
    		@Value("${server.port}") Integer port,
            @Value("${server.ssl.key-store}") String keystoreFile,
            @Value("${server.ssl.key-store-password}") final String keystorePass,
            @Value("${server.ssl.key-password}") final String keyPass) throws Exception {
   	
 		if(keystoreFile == null || keystoreFile.length() <= 0) { // http service
	        return new EmbeddedServletContainerCustomizer () {
	
				@Override
				public void customize(ConfigurableEmbeddedServletContainer container) {
		            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
		            tomcat.addConnectorCustomizers(
		                    new TomcatConnectorCustomizer() {
								@Override
								public void customize(Connector connector) {
									connector.setPort(port);
			                        connector.setSecure(false);
			                        connector.setScheme("http");
	
			                        Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
			                        proto.setSSLEnabled(false);
								}
		                    });
			    
				}
	        };
 		} else { // https service
	        final String absoluteKeystoreFile = new File(keystoreFile).getAbsolutePath();
	
	        return new EmbeddedServletContainerCustomizer () {
	
				@Override
				public void customize(ConfigurableEmbeddedServletContainer container) {
		            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
		            tomcat.addConnectorCustomizers(
		                    new TomcatConnectorCustomizer() {
								@Override
								public void customize(Connector connector) {
									connector.setPort(port);
			                        connector.setSecure(true);
			                        connector.setScheme("https");
	
			                        Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
			                        proto.setSSLEnabled(true);
			                        proto.setKeystoreFile(absoluteKeystoreFile);
			                        proto.setKeystorePass(keystorePass);
			                        proto.setKeyPass(keyPass);
			                        proto.setKeystoreType("JKS");
			                        proto.setKeyAlias("tomcat");
								}
		                    });
			    
				}
	        };
 		}
    }
}
