package com.kakao.cafe.common.utils.sql;

public class JdbcTypeConvertor<T> {
	/*
		List로 자료구조 변경하면서 공통된 로직의 메서드가 중복되어 별도의 메서드로 꺼내어 작업해보고자 생각했습니다.
	 */
	public static int getDataIdx(Long entityIdx) {
		return Math.toIntExact(entityIdx-1);
	}

	// TODO KeyHolder
	public static Long getNextId(int size) {
		return size + 1L;
	}
}
