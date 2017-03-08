package org.springboot.eventbus.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {

    private int statusCode;

    private String statusMessage;

    private Object data;

    public static Response of(int statusCode, String statusMessage) {
        return new Response(statusCode, statusMessage, null);
    }

    public static Response of(int statusCode, String statusMessage, Object data) {
        return new Response(statusCode, statusMessage, data);
    }

    public static Response success(String statusMessage, Object data) {
        return of(0, statusMessage, data);
    }

    public static Response success(Object data) {
        return of(0, null, data);
    }

    public static Response success() {
        return of(0, null, null);
    }

    public static Response failure(String statusMessage) {
        return of(-1, statusMessage);
    }

    public static Response failure(String statusMessage, Object data) {
        return of(-1, statusMessage, data);
    }

}
