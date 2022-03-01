package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserInformation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {

    private List<UserInformation> userInformationList = new ArrayList<>();

    @Override
    public UserInformation savaUserInformation(UserInformation userInformation) {
        userInformationList.add(userInformation);
        return userInformation;
    }

    @Override
    public Optional<UserInformation> findUserInformationById(String userId) {
        return userInformationList.stream()
               .filter(userInformation -> userInformation.getUserId()
               .equals(userId)).findAny();
    }

    public void clearUserInformationList() {
        userInformationList.clear();
    }
}
