package com.ssolution.core.exception;

public enum HttpStatusErrorMessage {
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    NOT_ACCEPTABLE(406, "NOT_ACCEPTABLE"),
    PROXY_AUTHENTICATION_REQUIRED(407, "PROXY_AUTHENTICATION_REQUIRED"),
    REQUEST_TIMEOUT(408, "REQUEST_TIMEOUT"),
    CONFLICT(409, "CONFLICT"),
    LENGTH_REQUIRED(411, "LENGTH_REQUIRED"),
    PAYLOAD_TOO_LARGE(413, "PAYLOAD_TOO_LARGE"),
    URI_TOO_LONG(414, "URI_TOO_LONG"),
    UNSUPPORTED_MEDIA_TYPE(415, "UNSUPPORTED_MEDIA_TYPE"),
    TOO_MANY_REQUESTS(429, "TOO_MANY_REQUESTS"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private final int value;
    private final String reasonPhrase;

    private HttpStatusErrorMessage(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static HttpStatusErrorMessage resolveHttpStatus(int statusCode) {
        HttpStatusErrorMessage[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HttpStatusErrorMessage status = var1[var3];
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;

    }
}
