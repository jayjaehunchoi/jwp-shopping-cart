package woowacourse.auth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    private static final String MEMBERS_RESOURCE = "/api/members";

    private final JwtTokenProvider jwtTokenProvider;
    private final Logger logger;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("request uri = {}, request method = {} ", request.getRequestURI(), request.getMethod());
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.validateToken(token);
    }
}
