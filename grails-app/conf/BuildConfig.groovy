grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://www.hibernatespatial.org/repository"

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"

    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.16'
        compile 'com.vividsolutions:jts:1.12'
        compile 'org.hibernatespatial:hibernate-spatial-postgis:1.1.1'
        compile 'org.postgis:postgis-main:1.3.1'
        compile 'org.postgis:postgis-jdbc:1.3.1'
        //compile 'org.codehaus.groovy.modules.http-builder:http-builder:0.5.2'
        runtime 'postgresql:postgresql:8.4-702.jdbc4'
        build 'org.apache.httpcomponents:httpcore:4.1.2'
        build 'org.apache.httpcomponents:httpclient:4.1.2'
        runtime 'org.apache.httpcomponents:httpcore:4.1.2'
        runtime 'org.apache.httpcomponents:httpclient:4.1.2'
        build 'org.apache.commons:commons-lang3:3.1'
        runtime 'org.apache.commons:commons-lang3:3.1'


    }

    plugins {
        runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.7.1"
        runtime ":resources:1.1.6"
        compile ":rest:0.7"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

        build ":tomcat:$grailsVersion"
    }
}
