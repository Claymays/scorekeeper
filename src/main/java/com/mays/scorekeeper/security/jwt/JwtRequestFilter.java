package com.mays.scorekeeper.security.jwt;

import com.mays.scorekeeper.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A filter class for authenticating requests with JWT tokens
 *
 * @author Clayton Mays
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    /**
     * Pulls and validates JWT tokens from http request headers
     * @param request the http server request
     * @param response the server http response being built
     * @param filterChain the server filter chain
     * @throws ServletException when applying filter chain fails
     * @throws IOException when applying filter chain fails
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                         HttpServletResponse response, FilterChain filterChain)
                         throws ServletException, IOException {
        var requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null) {
            String token = null;
            String username = null;

            var bearer = "Bearer";
            if (requestTokenHeader.startsWith(bearer)) {
                token = requestTokenHeader.substring(bearer.length());
                try {
                    username = jwtUtil.getUsernameFromToken(token);
                } catch(IllegalArgumentException e) {
                    log.error("unable to get jwt token", e);
                }
            } else {
                log.warn("JWT token does not begin with '" + bearer + "'");
            }

            var securityContext = SecurityContextHolder.getContext();
            if (username != null && securityContext.getAuthentication() == null) {
                var userDetails = userService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    var usernamePasswordAuthToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null, userDetails.getAuthorities());
                    var details = new WebAuthenticationDetailsSource()
                            .buildDetails(request);
                    usernamePasswordAuthToken.setDetails(details);

                    securityContext.setAuthentication(usernamePasswordAuthToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}