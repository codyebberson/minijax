package org.minijax.rs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;

/**
 * The MinijaxPathPattern class is a rich representation of a parameterized URL path.
 */
class MinijaxPathPattern {
    private static final String DIGITS_REGEX = "(\\p{Digit}+)";
    private static final String HEX_DIGITS_REGEX = "(\\p{XDigit}+)";
    private static final String EXP_REGEX = "[eE][+-]?" + DIGITS_REGEX;
    private static final String DOUBLE_REGEX = getDoubleRegex();
    private static final MultivaluedHashMap<String, String> EMPTY_PARAMS = new MultivaluedHashMap<>();
    private final String patternString;
    private final Pattern pattern;
    private final List<String> params;

    private MinijaxPathPattern(final String patternString, final List<String> params) {
        this.patternString = patternString;
        if (params == null) {
            this.pattern = null;
            this.params = null;
        } else {
            this.pattern = Pattern.compile(patternString);
            this.params = params;
        }
    }

    String getPatternString() {
        return patternString;
    }

    Pattern getPattern() {
        return pattern;
    }

    List<String> getParams() {
        return params;
    }

    public MultivaluedMap<String, String> tryMatch(final MinijaxUriInfo uriInfo) {
        final String requestPath = uriInfo.getRequestUri().getPath();

        if (params == null) {
            // Simple case, no params, no regex
            return patternString.equals(requestPath) ? EMPTY_PARAMS : null;
        }

        final Matcher matcher = pattern.matcher(requestPath);
        if (!matcher.matches()) {
            return null;
        }

        final MultivaluedMap<String, String> pathParameters = new MultivaluedHashMap<>();
        for (final String name : params) {
            pathParameters.add(name, matcher.group(name));
        }
        return pathParameters;
    }

    public static MinijaxPathPattern parse(final Method method, final String path) {
        return new Builder(method, path).build();
    }

    private static class Builder {
        private final Method method;
        private final String path;
        private final StringBuilder regexBuilder = new StringBuilder();
        private final StringBuilder paramBuilder = new StringBuilder();
        private List<String> params;
        private int curlyDepth;
        private int index;

        Builder(final Method method, final String path) {
            this.method = method;
            this.path = path;
        }

        MinijaxPathPattern build() {
            for (index = 0; index < path.length(); index++) {
                final char c = path.charAt(index);
                if (c == '{') {
                    handleOpen();
                } else if (c == '}') {
                    handleClose();
                } else {
                    handleOther(c);
                }
            }

            if (curlyDepth != 0) {
                throw new IllegalArgumentException("Unexpected end of input, missing '}'");
            }

            return new MinijaxPathPattern(regexBuilder.toString(), params);
        }

        private void handleOpen() {
            if (curlyDepth > 0) {
                paramBuilder.append('{');
            }
            curlyDepth++;
        }

        private void handleClose() {
            curlyDepth--;
            if (curlyDepth < 0) {
                throw new IllegalArgumentException("Unexpected '}' character at index " + index);
            } else if (curlyDepth > 0) {
                paramBuilder.append('}');
            } else {
                addParam();
            }
        }

        private void handleOther(final char c) {
            if (curlyDepth > 0) {
                paramBuilder.append(c);
            } else {
                regexBuilder.append(c);
            }
        }

        private void addParam() {
            final String paramStr = paramBuilder.toString();
            paramBuilder.setLength(0);

            final int colonIndex = paramStr.indexOf(':');
            if (colonIndex == 0) {
                throw new IllegalArgumentException("Unexpected ':' character at index " + index);
            }

            final String paramName;
            final String paramRegex;
            if (colonIndex > 0) {
                paramName = paramStr.substring(0, colonIndex).trim();
                paramRegex = paramStr.substring(colonIndex + 1).trim();
            } else {
                paramName = paramStr.trim();
                paramRegex = getDefaultPathParamRegex(paramName);
            }

            if (paramName.isEmpty()) {
                throw new IllegalArgumentException("Parameter name cannot be empty");
            }

            if (params == null) {
                params = new ArrayList<>();
            }
            params.add(paramName);
            regexBuilder.append("(?<").append(paramName).append(">").append(paramRegex).append(")");
        }

        private String getDefaultPathParamRegex(final String paramName) {
            final Class<?> c = getPathParamType(paramName);

            if (c == int.class || c == long.class || c == short.class) {
                return "-?[0-9]+";
            }

            if (c == double.class || c == float.class) {
                return DOUBLE_REGEX;
            }

            if (c == UUID.class) {
                return "[0-9a-f]{8}-?[0-9a-f]{4}-?[0-9a-f]{4}-?[0-9a-f]{4}-?[0-9a-f]{12}";
            }

            return "[^/]+";
        }

        private Class<?> getPathParamType(final String paramName) {
            if (paramName.isEmpty()) {
                throw new IllegalArgumentException("Parameter name cannot be empty");
            }

            for (final Parameter param : method.getParameters()) {
                final PathParam annotation = param.getAnnotation(PathParam.class);
                if (annotation != null && annotation.value().equals(paramName)) {
                    return param.getType();
                }
            }

            Class<?> currentClass = method.getDeclaringClass();
            while (currentClass != null) {
                for (final Field field : currentClass.getDeclaredFields()) {
                    final PathParam annotation = field.getAnnotation(PathParam.class);
                    if (annotation != null && annotation.value().equals(paramName)) {
                        return field.getType();
                    }
                }
                currentClass = currentClass.getSuperclass();
            }

            throw new IllegalArgumentException(String.format("Missing parameter \"%s\" (%s.%s)",
                    paramName,
                    method.getDeclaringClass().getName(),
                    method.getName()));
        }
    }

    /**
     * Returns a regular expression for strings parseable by Double.valueOf.
     *
     * See: https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#valueOf(java.lang.String)
     */
    private static String getDoubleRegex() {
        return
                ("[\\x00-\\x20]*" +  // Optional leading "whitespace"
                "[+-]?(" + // Optional sign character
                "NaN|" +           // "NaN" string
                "Infinity|" +      // "Infinity" string

                // A decimal floating-point string representing a finite positive
                // number without a leading sign has at most five basic pieces:
                // Digits . Digits ExponentPart FloatTypeSuffix
                //
                // Since this method allows integer-only strings as input
                // in addition to strings of floating-point literals, the
                // two sub-patterns below are simplifications of the grammar
                // productions from section 3.10.2 of
                // The Java??? Language Specification.

                // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                "(((" + DIGITS_REGEX + "(\\.)?(" + DIGITS_REGEX + "?)(" + EXP_REGEX + ")?)|" +

                // . Digits ExponentPart_opt FloatTypeSuffix_opt
                "(\\.(" + DIGITS_REGEX + ")(" + EXP_REGEX + ")?)|" +

                // Hexadecimal strings
                "((" +
                // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                "(0[xX]" + HEX_DIGITS_REGEX + "(\\.)?)|" +

                // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                "(0[xX]" + HEX_DIGITS_REGEX + "?(\\.)" + HEX_DIGITS_REGEX + ")" +

                ")[pP][+-]?" + DIGITS_REGEX + "))" +
                "[fFdD]?))" +

                // Optional trailing "whitespace"
                "[\\x00-\\x20]*");
    }
}
