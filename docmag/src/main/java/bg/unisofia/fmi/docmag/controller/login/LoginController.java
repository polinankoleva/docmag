package bg.unisofia.fmi.docmag.controller.login;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
public class LoginController {

    public static final String
        SUSI_PARSER    = "http://susi.apphb.com/api",
        SUSI_LOGIN     = SUSI_PARSER + "/login",
        SUSI_STUDENT   = SUSI_PARSER + "/student",
        SUSI_ROLES     = SUSI_PARSER + "/roles",
        DEFAULT_ERROR  = "Something went terribly wrong.";

    @Autowired private UserService  usrService;
    private CloseableHttpClient     client  = HttpClients.createMinimal();
    private ResponseHandler<String> handler = new BasicResponseHandler();
    private ObjectMapper            mapper  = new ObjectMapper();

    @RequestMapping(value    = "/login",
                    method   = RequestMethod.POST,
                    produces = "application/json")
    public @ResponseBody String login(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password)
                    throws IOException {
        User user = usrService.getUserByUsername(username);
        if (user != null && user.getType() == UserType.Teacher) return success(user);
        HttpUriRequest req              = loginRequest(username, password);
        try (CloseableHttpResponse resp = client.execute(req)) {
            return handleLoginResponse(resp, username);
        } catch (Exception ex) { return error(); }
    }

    private HttpUriRequest loginRequest(String username, String password)
            throws IOException {
        HttpPost post = new HttpPost(SUSI_LOGIN);
        String   body = String.format("{ username: \"%s\", password: \"%s\" }",
                                      username, password);

        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(body));
        return post;
    }

    private HttpUriRequest profileRequest(String key)
            throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(SUSI_STUDENT);
        String   body = String.format("{ key: %s }", key);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(body));
        return post;
    }

    @SuppressWarnings("unused")
    private HttpUriRequest rolesRequest(String key)
            throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(SUSI_ROLES);
        String   body = String.format("{ key: %s }", key);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(body));
        return post;
    }

    private String handleLoginResponse(HttpResponse response,
                                        String      username)
            throws IOException {
        StatusLine status = response.getStatusLine();
        switch (status.getStatusCode()) {
            case 200:
                String key = handler.handleResponse(response);
                User user  = usrService.getUserByUsername(username);
                try {
                    if (user == null) {
                        HttpUriRequest req = profileRequest(key);
                        try (CloseableHttpResponse resp = client.execute(req)) {
                            return handleProfileResponse(resp, username);
                        } catch (Exception ex) { return error(); }
                    } else return success(user);
                } finally { dispose(key); }
            default: return error(status.getReasonPhrase());
        }
    }

    private String handleProfileResponse(HttpResponse response,
                                         String       username)
            throws IOException {
        StatusLine status = response.getStatusLine();
        switch (status.getStatusCode()) {
            case 200:
                String   json = handler.handleResponse(response);
                SusiInfo info = mapper.readValue(json, SusiInfo.class);
                User     user = info.toUser();
                user.setUsername(username);
                usrService.createUser(user);
                user = usrService.getUserByUsername(username);
                return success(user);
            default: return error(status.getReasonPhrase());
        }
    }

    private void dispose(String key) throws IOException {
        HttpDeleteWithBody delete = new HttpDeleteWithBody(SUSI_LOGIN);
        String             body   = String.format("{ key: %s }", key);
        delete.setHeader("Content-Type", "application/json");
        delete.setEntity(new StringEntity(body));
        client.execute(delete);
    }

    private String success(User user) {
        return String.format("{ userId: \"%s\", type: \"%s\" }",
                             user.getId(), user.getType());
    }

    private String error(String message) {
        return String.format("{ errorMessage: \"%s\" }", message);
    }

    private String error() {
        return error(DEFAULT_ERROR);
    }
    
    /** Must. Send. DELETE. With. Body. */
    private static class HttpDeleteWithBody extends HttpPost {
        @SuppressWarnings("unused")
        public HttpDeleteWithBody()           { super();         }
        @SuppressWarnings("unused")
        public HttpDeleteWithBody(URI    uri) { super(uri);      }
        public HttpDeleteWithBody(String uri) { super(uri);      }
        @Override public String getMethod()   { return "DELETE"; }
    }
}
