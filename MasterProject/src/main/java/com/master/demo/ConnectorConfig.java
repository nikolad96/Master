package com.master.demo;

import org.apache.catalina.connector.Connector;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectorConfig {

//  private Connector getHttpConnector() {
//    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//    connector.setScheme("http");
//    connector.setPort(8080);
//    connector.setSecure(false);
//    connector.setRedirectPort(8081);
//    return connector;
//  }
}