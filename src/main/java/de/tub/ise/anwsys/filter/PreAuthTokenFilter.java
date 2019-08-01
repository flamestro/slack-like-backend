package de.tub.ise.anwsys.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication
        .preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthTokenFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String authHeaderName;

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