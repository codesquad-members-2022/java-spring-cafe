package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserInformation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {

    private List<UserInformation> userInformationList = new ArrayList<>();
    private long id = 0L;

    @Override
    public void savaUserInformation(UserInformation userInformation) {
        userInformation.setId(++id);
        userInformationList.add(userInformation);
    }

    @Override
    public Optional<UserInformation> findUserInformationById(String userId) {
        return userInformationList.stream()
                .filter(userInformation -> userInformation.getUserId()
                        .equals(userId)).findAny();
    }

    @Override
    public List<UserInformation> findAllUserInformation() {
        return new ArrayList<>(userInformationList);
    }

    public void clearUserInformationList() {
        userInformationList.clear();
    }
}
