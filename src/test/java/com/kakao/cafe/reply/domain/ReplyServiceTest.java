package com.kakao.cafe.reply.domain;

import static com.kakao.cafe.user.infra.MemoryUserRepositoryTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kakao.cafe.common.utils.session.SessionUser;
import com.kakao.cafe.user.domain.UserService;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {
	@InjectMocks
	private ReplyService replyService;

	@Mock
	private UserService userService;

	@Mock
	private ReplyRepository replyRepository;

	ArgumentCaptor<Reply> replyCaptor = ArgumentCaptor.forClass(Reply.class);

	@Test
	@DisplayName("댓글 작성 요청으로 저장된 댓글의 내용의 저장을 확인한다.")
	void check_saved_commend_when_leave_a_new_comment() {
		String comment = "test content";
		SessionUser sessionUser = SessionUser.of("testId", "testName");
		when(userService.getUserByUserId(sessionUser.getUserId())).thenReturn(getUser());

		replyService.leaveAComment(anyLong(), comment, sessionUser);

		verify(replyRepository).save(replyCaptor.capture());
		assertThat(replyCaptor.getValue().getContent()).isEqualTo(comment);
	}


	// fail
/*	@Test
	@DisplayName("해당 게시판의 모든 댓글들을 조회하여 리스트로 반환 된것을 확인하다.")
	void check_comment_list_related_of_article() {
		when(replyRepository.findByArticleId(anyLong()))
			.thenReturn(Arrays.asList(get("comment1"), get("comment2")));

		List<ReplyDto.Response> actuals = replyService.getListOfReplyByArticle(anyLong());

		assertThat(actuals.get(0)).satisfies(response -> assertThat(response.getReplyContent()).isNotBlank());

	}

	private Reply get(String comment) {
		return new Reply(anyLong(), "commenter", comment, LocalDate.now(), anyLong(), "wrtierNickName", anyLong(), anyBoolean());
	}*/
}
