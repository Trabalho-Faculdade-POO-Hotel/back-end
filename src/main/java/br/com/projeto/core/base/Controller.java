package br.com.projeto.core.base;

import java.util.HashMap;
import java.util.Objects;

import br.com.projeto.core.base.ServletRouter.Handler;

public class Controller {
    private HashMap<HttpMethod, HashMap<String, Handler>> routeHandlers = new HashMap<>();

    public HashMap<HttpMethod, HashMap<String, Handler>> getControllerRoutes() {
        return this.routeHandlers;
    };

    protected void registerRoute(HttpMethod method, String path, Handler handler) {
        HashMap<String, Handler> methodRoutes = this.routeHandlers.get(method);

        if (Objects.isNull(methodRoutes)) {
            methodRoutes = new HashMap<>();

            this.routeHandlers.put(method, methodRoutes);
        }

        methodRoutes.put(path, handler);
    }
}
