package br.com.projeto.core.base;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE;

    public static HttpMethod fromString(String method) {
        switch (method) {
            case "GET":
                return HttpMethod.GET;

            case "POST":
                return HttpMethod.POST;

            case "PUT":
                return HttpMethod.PUT;

            case "DELETE":
                return HttpMethod.DELETE;

            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
