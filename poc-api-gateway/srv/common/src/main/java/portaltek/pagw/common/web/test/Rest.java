package portaltek.pagw.common.web.test;


import org.springframework.boot.test.web.client.TestRestTemplate;


public class Rest {

    final private Integer port;
    final private String host;
    final private String urlBase;
    final private TestRestTemplate template;


    public Rest(TestRestTemplate template, String host, Integer port) {
        this.template = template;
        this.port = port;
        this.host = host;
        this.urlBase = host + port;
    }

    public Integer getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String url(String endpoint) {
        return urlBase + endpoint;
    }

    public String urlBase() {
        return urlBase;
    }

    public TestRestTemplate template() {
        return template;
    }


}




