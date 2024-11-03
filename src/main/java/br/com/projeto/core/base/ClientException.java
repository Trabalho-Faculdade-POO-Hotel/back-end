package br.com.projeto.core.base;

public class ClientException extends Exception {
    private String reason;
    private int responseCode;

    public ClientException(String reason, int responseCode) {
        this.reason = reason;
        this.responseCode = responseCode;
    }

    public String getReason() {
        return this.reason;
    }

    public int getResponseCode() {
        return this.responseCode;
    }
}
