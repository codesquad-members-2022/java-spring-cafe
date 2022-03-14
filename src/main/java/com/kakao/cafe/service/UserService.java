package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.UserLoginRequest;
import com.kakao.cafe.dto.UserResponse;
import com.kakao.cafe.dto.UserSaveRequest;
import com.kakao.cafe.exception.DuplicateException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.UserRepository;
import com.kakao.cafe.util.Mapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse register(UserSaveRequest request) {
        // UserSaveRequest DTO 객체를 User 도메인 객체로 변환
        User user = Mapper.map(request, User.class);

        // 중복된 userId 가 있는지 확인
        validateUserId(user.getUserId());

        // User 도메인 객체를 저장소에 저장
        User savedUser = userRepository.save(user);

        // User 도메인 객체를 UserResponse DTO 객체로 변환
        return Mapper.map(savedUser, UserResponse.class);
    }

    public List<UserResponse> findUsers() {
        // List<User> 도메인 객체를 저장소에서 반환
        List<User> users = userRepository.findAll();

        // List<User> 도메인 객체를 List<UserResponse> DTO 객체로 반환

        return users.stream()
            .map(user -> Mapper.map(user, UserResponse.class))
            .collect(Collectors.toList());
    }

    public UserResponse findUser(String userId) {
        // User 도메인 객체를 저장소에서 반환
        User user = userRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // User 도메인 객체를 UserResponse DTO 객체로 변환
        return Mapper.map(user, UserResponse.class);
    }

    private void validateUserId(String userId) {
        // 저장소에 중복된 userId 를 가진 레코드가 있는지 확인
        userRepository.findByUserId(userId)
            .ifPresent((user) -> {
                throw new DuplicateException(ErrorCode.DUPLICATE_USER);
            });
    }

    public UserResponse updateUser(User user, UserSaveRequest request) {
        // session 에서 반환된 객체를 검증
        user.checkPassword(request.getPassword());

        // User 도메인 객체를 저장소에서 반환
        User findUser = userRepository.findByUserId(user.getUserId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // User 도메인 객체에 대해 업데이트 요청사항을 변경
        User updatedUser = findUser.update(request.getName(), request.getEmail());

        // 저장소에 업데이트된 User 객체를 저장
        User savedUser = userRepository.save(updatedUser);

        // User 도메인 객체를 UserResponse 객체로 변환
        return Mapper.map(savedUser, UserResponse.class);
    }

    public UserResponse login(UserLoginRequest request) {
        // User 도메인 객체를 저장소에서 반환
        User user = userRepository.findByUserId(request.getUserId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호가 일치하는지 확인
        user.checkPassword(request.getPassword());

        // User 도메인 객체를 UserResponse 객체로 변환
        return Mapper.map(user, UserResponse.class);
    }
}
