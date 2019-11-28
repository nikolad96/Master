package com.example.nc;

import org.apache.catalina.connector.Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

//  @Bean
//  public EmbeddedServletContainerFactory servletContainer() {
//    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//      @Override
//      protected void postProcessContext(Context context) {
//        SecurityConstraint securityConstraint = new SecurityConstraint();
//        securityConstraint.setUserConstraint("CONFIDENTIAL");
//        SecurityCollection collection = new SecurityCollection();
//        collection.addPattern("/*");
//        securityConstraint.addCollection(collection);
//        context.addConstraint(securityConstraint);
//      }
//    };
//
//    tomcat.addAdditionalTomcatConnectors(redirectConnector());
//    return tomcat;
//  }
//
//  private Connector getHttpConnector() {
//    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//    connector.setScheme("http");
//    connector.setPort(8080);
//    connector.setSecure(false);
//    connector.setRedirectPort(8084);
//    return connector;
//  }
}