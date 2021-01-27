package com.narrator.util;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Slf4j
public class PayloadUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private PayloadUtil() {
        // restricting instantiation
    }

    public static <T> T request(final String request, final Class<T> type) {
        try {
            return MAPPER.readValue(request, type);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> String response(final T response) {
        try {
            if (response == null) {
                log.debug("No content: {}", response);
                return noContent().toString();
            }
            return MAPPER.writeValueAsString(response);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return serverError(e).toString();
        }
    }

    private static ObjectNode serverError(final Throwable e) {
        final ObjectNode error = JsonNodeFactory.instance.objectNode();
        error.put("message", e.getMessage());
        error.put("code", 500);
        return error;
    }

    private static ObjectNode noContent() {
        final ObjectNode error = JsonNodeFactory.instance.objectNode();
        error.put("message", "No content");
        error.put("code", 204);
        return error;
    }

}
