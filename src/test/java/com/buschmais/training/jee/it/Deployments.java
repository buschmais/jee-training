package com.buschmais.training.jee.it;

import lombok.extern.slf4j.Slf4j;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.ConfigurableMavenResolverSystem;

import java.io.File;

import static org.jboss.shrinkwrap.resolver.api.maven.Maven.configureResolver;
import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.*;

@Slf4j
@ArquillianSuiteDeployment
public class Deployments {

    /**
     * The system property specifying the Maven settings.xml to use
     */
    private static final String IT_MAVEN_SETTINGS = "it.maven-settings";

    @Deployment
    public static WebArchive deployment() {
        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "jee.war");

        webArchive.addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"), "faces-config.xml");
        webArchive.addAsWebInfResource(new File("src/test/webapp/WEB-INF/jboss-web.xml"), "jboss-web.xml");
        webArchive.setWebXML(new File("src/main/webapp/WEB-INF/web.xml"));
        webArchive.addAsWebResource(new File("src/main/webapp/index.xhtml"), "index.xhtml");
        webArchive.addAsWebResource(new File("src/main/webapp/secure/index.xhtml"), "secure/index.xhtml");

        webArchive.addPackages(true, "com.buschmais.training.jee")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml");

        ConfigurableMavenResolverSystem mavenResolverSystem = configureResolver();
        String mavenSettings = System.getProperty(IT_MAVEN_SETTINGS);
        if (mavenSettings != null) {
            log.info("Using Maven settings from {}.", mavenSettings);
            mavenResolverSystem.fromFile(mavenSettings);
        }
        File[] libs = mavenResolverSystem.withClassPathResolution(true)
//                .workOffline()
                .loadPomFromFile("pom.xml")
                .importDependencies(COMPILE, RUNTIME, TEST)
                .resolve()
                .withTransitivity()
                .asFile();
        for (File lib : libs) {
            webArchive.addAsLibrary(lib);
        }
        return webArchive;
    }
}
