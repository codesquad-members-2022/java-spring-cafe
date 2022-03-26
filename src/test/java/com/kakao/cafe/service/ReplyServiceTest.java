package com.kakao.cafe.service;

import com.kakao.cafe.domain.reply.Reply;
import com.kakao.cafe.domain.reply.ReplyRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ReplyDto;
import com.kakao.cafe.web.dto.ReplyResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    private ReplyDto replyDto;
    private String writer;
    private Reply reply;

    @BeforeEach
    void setUp() {
        writer = "writer";
        replyDto = new ReplyDto(1, "contents");
        reply = Reply.of(1, 1, writer, "contents", LocalDateTime.now());
    }

    @Test
    @DisplayName("writer와 replyDto를 매개변수로, 저장한 후 replyResponseDto를 반환한다.")
    void writeTest() {
        //given
        given(replyRepository.save(any())).willReturn(reply);

        //when
        ReplyResponseDto replyResponseDto = replyService.write(writer, replyDto);

        //then
        assertThat(replyResponseDto.getId()).isEqualTo(reply.getId());
        assertThat(replyResponseDto.getArticleId()).isEqualTo(reply.getArticleId());
        assertThat(replyResponseDto.getWriter()).isEqualTo(reply.getWriter());
        assertThat(replyResponseDto.getContents()).isEqualTo(reply.getContents());
        assertThat(replyResponseDto.getWrittenTime()).isEqualTo(reply.getWrittenTime());
    }

    @Test
    @DisplayName("articleId로 해당하는 article의 답변들을 검색하면, 해당 articleId를 가지는 reply들을 replyResponseDto로 변환해서 리스트로 반환한다.")
    void showAllInArticleTest() {
        //given
        given(replyRepository.findAllByArticleId(1)).willReturn(List.of(reply));

        //when
        List<ReplyResponseDto> replyResponseDtos = replyService.showAllInArticle(1);

        //then
        ReplyResponseDto replyResponseDto = replyResponseDtos.get(0);
        assertThat(replyResponseDtos.size()).isEqualTo(1);
        assertThat(replyResponseDto.getId()).isEqualTo(reply.getId());
        assertThat(replyResponseDto.getArticleId()).isEqualTo(reply.getArticleId());
        assertThat(replyResponseDto.getWriter()).isEqualTo(reply.getWriter());
        assertThat(replyResponseDto.getContents()).isEqualTo(reply.getContents());
        assertThat(replyResponseDto.getWrittenTime()).isEqualTo(reply.getWrittenTime());
    }

    @Test
    @DisplayName("작성자는 직접 작성한 답변이라면, 해당하는 reply의 id로 답변을 삭제할 수 있다.")
    void delete_owned_reply_test() {
        //given
        given(replyRepository.deleteOne(1)).willReturn(true);
        given(replyRepository.findWriterById(1)).willReturn(Optional.of(writer));

        //when
        boolean isDeleted = replyService.delete(writer, 1);

        //then
        assertThat(isDeleted).isTrue();
    }

    @Test
    @DisplayName("다른 작성자의 답변을 삭제하려하면, ClientException이 발생한다")
    void delete() {
        //given
        given(replyRepository.findWriterById(1)).willReturn(Optional.of(writer));

        //when & then
        assertThatThrownBy(()->replyService.delete("AnotherWriter", 1))
                .isInstanceOf(ClientException.class)
                .hasMessage("접근 권한이 없습니다.");

    }

    @Test
    void isDeletableArticle() {
        //given
        given(replyRepository.hasReplyOfAnotherWriter(1, writer)).willReturn(false);

        //when
        boolean isDeletable = replyService.isDeletableArticle(1, writer);

        //then
        assertThat(isDeletable).isTrue();

    }
}
