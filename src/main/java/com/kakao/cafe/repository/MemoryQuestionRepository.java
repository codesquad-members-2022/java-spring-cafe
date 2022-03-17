package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryQuestionRepository implements QuestionRepository {

    private final HashMap<Long, Question> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Question findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Question save(Question question) {
        question.setId(++sequence);
        store.put(question.getId(), question);
        return question;
    }
}
