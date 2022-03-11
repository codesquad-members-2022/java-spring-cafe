package com.kakao.cafe.user.infra;

import static com.kakao.cafe.common.utils.sql.JdbcTypeConvertor.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kakao.cafe.common.exception.DomainNotFoundException;
import com.kakao.cafe.qna.infra.MemoryArticleRepository;
import com.kakao.cafe.user.domain.User;
import com.kakao.cafe.user.domain.UserRepository;

@Repository
public class MemoryUserRepository implements UserRepository {
	public static final String ERROR_OF_USER_ID = "user id";

	private final List<User> data = new CopyOnWriteArrayList<>();
	private Logger logger = LoggerFactory.getLogger(MemoryArticleRepository.class);

	/**
	 * JPA save() 로직을 학습하면서 담아봤습니다.
	 * 영속성 컨택스트 내에서 id를 통해 더티채킹 후 update() 또는 insert() 진행 합니다.
	 * @param entity
	 * @return
	 */
	@Override
	public User save(User entity) {
		Long id = entity.getId();
		if (id != null && this.hasId(id)) {
			data.set(getDataIdx(entity), entity);
			logger.info("update user : {}", entity.getUserId());
			return entity;
		}
		id = getNextId(data.size());
		entity.setId(id);
		data.add(entity);
		logger.info("insert user : {}", entity.getUserId());
		return entity;
	}

	private int getDataIdx(User entity) {
		return Math.toIntExact(entity.getId() - 1);
	}

	/**
	 * 저장된 정보 안에서 매개변수로 전달된 id를 가진 객체를 판별하는 메서드 입니다.
	 * - 해당 id를 가진 경우에만 update() 될 수 있습니다.
	 * @param id
	 * @return
	 */
	private boolean hasId(Long id) {
		Optional<User> user = getUserById(id);
		if (user.isEmpty()) {
			logger.error("not exist of user id : {}", id);
			throw new DomainNotFoundException("없는 사용자 정보 요청입니다.");
		}
		return user.get().hasId(id);
	}

	@Override
	public Optional<User> findById(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException(ERROR_OF_USER_ID);
		}
		if (data.size() < id) {
			throw new DomainNotFoundException(ERROR_OF_USER_ID);
		}
		return getUserById(id);
	}

	private Optional<User> getUserById(Long id) {
		return data.stream()
			.parallel()
			.filter(it -> it.hasId(id))
			.findAny();
	}

	@Override
	public List<User> findAll() {
		return data.stream()
			.collect(Collectors.toUnmodifiableList());
	}

	@Override
	public boolean existByUserId(String userId) {
		return this.data.stream()
			.anyMatch(it -> it.hasUserId(userId));
	}

	@Override
	public boolean existByName(String name) {
		return this.data.stream()
			.anyMatch(user -> user.hasName(name));
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return this.data.stream()
			.parallel()
			.filter(it -> it.hasUserId(userId))
			.findAny();
	}

	@Override
	public void deleteAll() {
		this.data.clear();
	}
}
