package com.kakao.cafe.common.utils.sql;

public class JdbcTypeConvertor<T> {
	/*
		List로 자료구조 변경하면서 공통된 로직의 메서드가 중복되어 별도의 메서드로 꺼내어 작업해보고자 생각했습니다.
	 */
	public static int getDataIdx(Long entityIdx) {
		return Math.toIntExact(entityIdx-1);
	}

	public static Long getNextId(int size) {
		return size + 1L;
	}

	/**
	 * 도매인 객체를 매개변수로 전달 받아서 도메인을 담은 자료구조 사이즈 보다 1씩 작은 객체의 id 값으로
	 * 변경하여 반환하는 메서드 입니다.
	 * - 객체의 값을 꺼내서 매개변수로 전달보다 객체를 통해 작업이루어지는게 좋다고 생각해서 아래와 같은 방식으로 해봤는데요.
	 * - 리플랙션 까지 쓰고, 제네릭 타입으로 static 은 안되고, 생성시키자니 애매해서 빈으로 등록하여 생성자 주입까지 생각하지 과하다는 생각도 들었습니다.
	 *
	 * - 어떻게 판단하는게 좋을지 모르겠고 아는 기회가 되면 좋겠다 싶어서, 주석처리 해놧습니다.
	 * @param entity
	 * @return
	 */
/*	public int getDataIdx(T entity) {
		try {
			Field field = entity.getClass().getDeclaredField("id");
			field.setAccessible(true);
			String longTypeIdx = String.valueOf(field.get(entity));
			return Math.toIntExact(Long.parseLong(longTypeIdx) - 1);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return -1;
	}*/
}
