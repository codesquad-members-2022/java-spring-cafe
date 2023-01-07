package com.kakao.cafe.service;

import com.kakao.cafe.domain.reply.Reply;
import com.kakao.cafe.domain.reply.ReplyRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ReplyResponseDto;
import com.kakao.cafe.web.dto.ReplyUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public ReplyResponseDto write(Integer articleId, String writer, String contents) {
        Reply reply = replyRepository.save(Reply.newInstance(articleId, writer, contents));
        return ReplyResponseDto.from(reply);
    }

    public List<ReplyResponseDto> showAllInArticle(Integer articleId) {
        return replyRepository.findAllByArticleId(articleId)
                .stream()
                .map(ReplyResponseDto::from)
                .collect(Collectors.toList());
    }

    public boolean delete(String writer, Integer id) {
        if(checkWriter(writer, id)) {
            return replyRepository.deleteOne(id);
        }
        throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
    }

    public boolean isDeletableArticle(Integer articleId, String writer) {
        return !replyRepository.hasReplyOfAnotherWriter(articleId, writer);
    }

    public ReplyResponseDto update(ReplyUpdateDto replyUpdateDto, String writer) {
        if(!replyUpdateDto.hasSameWriter(writer)) {
            throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        };
        return ReplyResponseDto.from(replyRepository.save(replyUpdateDto.toEntity()));
    }

    private boolean checkWriter(String writer, Integer id) {
        String targetWriter = replyRepository.findWriterById(id)
                .orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        return targetWriter.equals(writer);
    }


}
