package likelion.eight.domain.token.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import likelion.eight.common.domain.exception.CertificationFailedException;
import likelion.eight.domain.token.model.TokenResponse;
import likelion.eight.domain.token.service.TokenService;
import likelion.eight.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCookieService {

    public static final String USER_TOKEN_CODE = "userTokenCode";
    public static final String ADMIN_TOKEN_CODE = "adminTokenCode";
    private final TokenService tokenService;

    public void createUserCookie(
            HttpServletResponse response,
            User loginUser
    ){
        TokenResponse token = tokenService.issueToken(loginUser);
        createCookie(response, token, USER_TOKEN_CODE);
    }

    public void  createAdminCookie(
            HttpServletResponse response,
            User loginUser
    ){
        TokenResponse token = tokenService.issueToken(loginUser);
        createCookie(response, token, ADMIN_TOKEN_CODE);
    }

    private void  createCookie(HttpServletResponse response, TokenResponse token, String tokenKey) {
        Cookie cookie = new Cookie(tokenKey, token.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

    public void expiredCookie(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        //deleteSession(request);
        deleteCookie(request, response);
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(USER_TOKEN_CODE.equals(cookie.getName()) || ADMIN_TOKEN_CODE.equals(cookie.getName())){
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    response.addCookie(cookie);
                }
            }
        }
    }

    private void deleteSession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }else{
            throw new CertificationFailedException("유효 하지 않은 세션");
        }
    }

    public Long getIdWithValidateAdminToken(HttpServletRequest request){
        String accessToken = getAccessToken(request, ADMIN_TOKEN_CODE);
        return tokenService.validationToken(accessToken);
    }

    public Long getIdWithValidateUserToken(HttpServletRequest request){
        String accessToken = getAccessToken(request, USER_TOKEN_CODE);
        return tokenService.validationToken(accessToken);
    }

    private String getAccessToken(HttpServletRequest request, String tokenKey){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(tokenKey.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        throw new CertificationFailedException("유효 하지 않은 토큰");
    }

    // 로그인 여부 확인 메서드 추가
    public boolean isUserLoggedIn(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if (USER_TOKEN_CODE.equals(cookie.getName()) || ADMIN_TOKEN_CODE.equals(cookie.getName()) ){
                    try {
                        tokenService.validationToken(cookie.getValue()); // 토큰 유효성 검사
                        return true; // 유효한 경우, 로그인 상태
                    } catch (Exception e){
                        // validationToken에서 발생한 예외로, 로그인하지 않은 상태로 간주
                    }
                }
            }
        }
        return false; // 쿠키가 없거나, 유효한 토큰이 없는 경우
    }
}
