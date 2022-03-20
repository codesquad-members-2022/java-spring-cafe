package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Question;

import java.util.List;

public interface QuestionRepository {
    Question findById(Long Id);
    List<Question> findAll();
    Question save(Question question);
}
