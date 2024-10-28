package br.com.projeto.core.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projeto.utils.Utils;

public class ServletRouter extends HttpServlet {
    public interface Handler {
        void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }

    HashMap<String, Handler> handlers;

    protected ServletRouter() {
        this.handlers = new HashMap<>();
    }

    protected void registerEndpointHandler(HttpMethod method, String path, Handler handler) {
        this.handlers.put(this.buildKey(method, path), handler);
    }

    protected void registerController(Controller controller) {
        HashMap<HttpMethod, HashMap<String, Handler>> controllerRoutes = controller.getControllerRoutes();

        for (Map.Entry<HttpMethod, HashMap<String, Handler>> entry : controllerRoutes.entrySet()) {
            HttpMethod method = entry.getKey();

            for (Map.Entry<String, Handler> methodRouteHandler : entry.getValue().entrySet()) {
                this.registerEndpointHandler(method, methodRouteHandler.getKey(), methodRouteHandler.getValue());
            }
        }
    }

    private String buildKey(HttpMethod method, String path) {
        String finalPath = path;

        if (Objects.isNull(finalPath)) {
            return null;
        }

        if (finalPath.charAt(finalPath.length() - 1) != '/') {
            finalPath += "/";
        }

        return method.toString() + finalPath;
    }

    private Handler getRequestHandler(HttpServletRequest req) {
        String path = req.getPathInfo();
        String method = req.getMethod();

        String key = this.buildKey(HttpMethod.fromString(method), path);
        Handler handler = this.handlers.get(key);

        return handler;
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpMethod method = HttpMethod.fromString(req.getMethod());
        Handler handler = this.getRequestHandler(req);

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

        try {
            handler.handleRequest(req, resp);
        } catch (Exception e) {
            e.printStackTrace();

            try {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                Utils.sendJsonResponse("{ \"error\": \"Ocorreu um erro inesperado ao processar a requisição\" }", resp);
            } catch (Exception responseError) {
                System.out.println("Erro ao fornecer resposta de erro: ");
                responseError.printStackTrace();
            }
        }
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
