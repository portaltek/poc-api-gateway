package portaltek.pagw.common.web.test;


import org.springframework.http.HttpHeaders;


public class Header {

    private Api api;

    public Header(Api api) {
        this.api = api;
    }


    public HttpHeaders admin() {
        return api.createHeaderWithNewToken("admin", "admin");
    }

    public HttpHeaders user() {
        return api.createHeaderWithNewToken("user", "user");
    }

    public HttpHeaders adminNotValid() {
        return api.createHeaderWithNewToken("admin", "admin2");
    }

}




