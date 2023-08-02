package com.example.novel_backend.core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Xss request filter
 *
 * @author :Contanstin
 * @create 2023/8/2 13:04
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Map<String, String> REPLACE_RULE = new HashMap<>();

    static {
        REPLACE_RULE.put("&", "&amp;");
        REPLACE_RULE.put("<", "&lt;");
        REPLACE_RULE.put(">", "&gt;");
        REPLACE_RULE.put("\"", "&quot;");
        REPLACE_RULE.put("'", "&apos;");
    }

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = values[i];
                for (Map.Entry<String, String> entry : REPLACE_RULE.entrySet()) {
                    escapeValues[i] = escapeValues[i].replace(entry.getKey(), entry.getValue());
                }
            }
            return escapeValues;
        }
        return new String[0];
    }
}
