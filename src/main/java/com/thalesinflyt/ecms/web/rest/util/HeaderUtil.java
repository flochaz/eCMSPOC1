package com.thalesinflyt.ecms.web.rest.util;

import java.util.List;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-ecmspoc1App-alert", message);
        headers.add("X-ecmspoc1App-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntitiesCreationAlert(String entityName, List<String> params) {
        return createAlert("Several new " + entityName + " are created with identifiers " + params, params.toString());
    }
    
    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }
    
    public static HttpHeaders createEntitiesUpdateAlert(String entityName, List<String> params) {
        return createAlert("Several " + entityName + " are updated with identifiers " + params, params.toString());
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-ecmspoc1App-error", defaultMessage);
        headers.add("X-ecmspoc1App-params", entityName);
        return headers;
    }
}
