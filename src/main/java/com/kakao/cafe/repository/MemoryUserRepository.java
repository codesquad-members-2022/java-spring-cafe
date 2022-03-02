package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private List<UserInformation> userInformationList = new ArrayList<>();
    private long id = 0L;

    @Override
    public UserInformation savaUserInformation(UserInformation userInformation) {
        userInformation.setId(++id);
        userInformationList.add(userInformation);

        return userInformation;
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

    @Override
    public void clearUserInformationList() {
        userInformationList.clear();
    }

    public int getCountOfUserInformationElement() {
        return userInformationList.size();
    }


}
