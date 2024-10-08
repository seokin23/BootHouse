package likelion.eight.domain.token.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.domain.token.helper.ifs.TokenHelperIfs;
import likelion.eight.domain.token.model.Token;
import likelion.eight.domain.token.model.TokenResponse;
import likelion.eight.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    public static final String USER_ID = "userId";
    private final TokenHelperIfs tokenHelper;

    public TokenResponse issueToken(User user){

        return Optional.ofNullable(user)
                .map(User::getId)
                .map(id -> {
                    Token accessToken = createAccessToken(id);
                    Token refreshToken = createRefreshToken(id);
                    return TokenResponse.from(accessToken, refreshToken);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Token User", user.getId()));
    }

    private Token createAccessToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelper.issueAccessToken(data);
    }

    private Token createRefreshToken(Long userId){
        HashMap<String, Object> data = new HashMap<>();
        data.put(USER_ID, userId);
        return tokenHelper.issueRefreshToken(data);
    }

    // JWT 토큰의 유효성 검사 후, 유효한 경우 사용자 ID 반환. 토큰이 유효하지 않거나, 사용자 ID가 없다면 예외를 던짐
    public Long validationToken(String token){
        Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);
        Object userId = map.get(USER_ID);

        Objects.requireNonNull(userId, () -> {
            throw new ResourceNotFoundException("Token UserId Null");
        });

        return Long.parseLong(userId.toString());
    }

}
