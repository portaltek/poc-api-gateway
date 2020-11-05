package portaltek.pagw.common.web.test;


import org.springframework.http.HttpHeaders;


public class Header {

    private Api api;

    public Header(Api api) {
        this.api = api;
    }


    public HttpHeaders admin() {
        return api.header("admin", "admin");
    }

    public HttpHeaders user() {
        return api.header("user", "user");
    }

    public HttpHeaders adminNotValid() {
        return api.header("admin", "admin2");
    }

}




