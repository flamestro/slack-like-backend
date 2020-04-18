package de.tub.ise.anwsys.filter;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class PreAuthTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final String authHeaderName;

    public PreAuthTokenFilter(String authHeaderName) {
        this.authHeaderName = authHeaderName;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(authHeaderName);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}