package com.kakao.cafe.service;

import com.kakao.cafe.domain.Question;
import com.kakao.cafe.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Long create(Question question) {
        // TODO : validate and format of writer, title, contents
        questionRepository.save(question);
        return question.getId();
    }

    public List<Question> findQuestions() {
        List<Question> questions = questionRepository.findAll();
        questions.sort(Comparator.comparingInt(q -> q.getId().intValue()));
        return questions;
    }

    public Question findOne(Long id) {
        return questionRepository.findById(id);
    }
}
