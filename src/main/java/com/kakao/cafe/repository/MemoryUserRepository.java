package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MemoryUserRepository implements UserRepository {

    private List<UserInformation> userInformationStore = new ArrayList<>();

    @Override
    public UserInformation save(UserInformation userInformation) {
        try {
            int index = findUserId(userInformation.getUserId());
            if (index == -1) {
                userInformationStore.add(userInformation);
                return userInformation;
            }

            userInformationStore.set(index, userInformation);
        } catch (Exception e) {
            return null;
        }

        return userInformation;
    }

    private int findUserId(String userId) {
        return IntStream.range(0, userInformationStore.size())
                .filter(i -> userInformationStore.get(i).hasSameUserId(userId))
                .findFirst().orElse(-1);
    }

    @Override
    public Optional<UserInformation> findByUserId(String userId) {
        return userInformationStore.stream()
               .filter(userInformation -> userInformation.hasSameUserId(userId))
               .findAny();
    }

    @Override
    public List<UserInformation> findAll() {
        return new ArrayList<>(userInformationStore);
    }

    @Override
    public void clear() {
        userInformationStore.clear();
    }
}
