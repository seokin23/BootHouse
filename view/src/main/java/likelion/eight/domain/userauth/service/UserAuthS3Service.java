package likelion.eight.domain.userauth.service;

import likelion.eight.certificationirequest.enums.AuthRequestStatus;
import likelion.eight.common.service.S3ServiceImpl;
import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.common.service.port.S3Service;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.domain.userauth.controller.model.UserAuthCreateRequest;
import likelion.eight.domain.userauth.converter.UserAuthConverter;
import likelion.eight.domain.userauth.model.UserAuth;
import likelion.eight.domain.userauth.service.port.UserAuthRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class UserAuthS3Service {

    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;
    private final S3Service s3ServiceImpl;
    private final ClockHolder clockHolder;

    public UserAuth create(UserAuthCreateRequest request){
        User client = userRepository.getById(request.getClientId());
        String imageUrl = s3ServiceImpl.saveFile(request.getImage());
        UserAuth userAuth = UserAuthConverter.toUserAuth(client, request, imageUrl);

        return userAuthRepository.save(userAuth);
    }

    @Transactional(readOnly = true)
    public Page<UserAuth> getUserAuthPage(int page, int size){
        return userAuthRepository.findAll(page, size);
    }

    public void delete(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        s3ServiceImpl.deleteFile(userAuth.getImageUrl());
        userAuthRepository.delete(userAuth);
    }

    public void approve(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.approve(clockHolder);
        userAuthRepository.save(userAuth);
    }

    public void deny(long id){
        UserAuth userAuth = userAuthRepository.getById(id);
        userAuth = userAuth.deny(clockHolder);
        userAuthRepository.save(userAuth);
    }

    public UserAuth getById(long id){
        return userAuthRepository.getById(id);
    }
}
