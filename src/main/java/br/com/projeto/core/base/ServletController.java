package br.com.projeto.core.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletController extends HttpServlet {
    public interface Handler {
        void handleRequest(HttpServletRequest request, HttpServletResponse response);
    }

    enum Method {
        GET,
        POST,
        PUT,
        DELETE;

        public static Method fromString(String method) {
            switch (method) {
                case "GET":
                    return Method.GET;
            
                case "POST":
                    return Method.POST;

                case "PUT":
                    return Method.PUT;

                case "DELETE":
                    return Method.DELETE;

                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    HashMap<String, Handler> handlers;

    public ServletController() {
        this.handlers = new HashMap<>();
    }

    public void registerEndpointHandler(Method method, String path, Handler handler) {
        this.handlers.put(this.buildKey(method, path), handler);
    }

    private String buildKey(Method method, String path) {
        return method.toString() + path;
    }

    private Handler getRequestHandler(HttpServletRequest req, HttpServletResponse resp) {
        String path = req.getPathInfo();
        String method = req.getMethod();

        if (Objects.isNull(path)) {
            path = "/";
        }

        String key = this.buildKey(Method.fromString(method), path);
        Handler handler = this.handlers.get(key);
        
        return handler;
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Method method = Method.fromString(req.getMethod());
        Handler handler = this.getRequestHandler(req, resp);

        if (handler == null) {
            switch (method) {
                case GET:
                    super.doGet(req, resp);

                    return;
                case POST:
                    super.doPost(req, resp);

                    return;
                
                case PUT:
                    super.doPut(req, resp);

                    return;

                case DELETE:
                    super.doDelete(req, resp);

                    return;
                default:
                    resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

                    return;
            }
        }

        handler.handleRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.handleRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.handleRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.handleRequest(req, resp);
    }

}
