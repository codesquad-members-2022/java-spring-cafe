package com.kakao.cafe.common.utils.sql;

import java.util.List;
import java.util.Objects;

import com.kakao.cafe.user.infra.JdbcTemplateUserRepository;

public class SqlFormatter {
	private static final String SQL_SUBSTITUTE = " = ";
	private static final String SQL_COMMA = ",";

	private static final String SQL_UPDATE_PREFIX = "update ";
	private static final String SQL_UPDATE_POSTFIX = " set ";
	private static final String SQL_SELECT_PREFIX = "select ";
	private static final String SQL_SELECT_POSTFIX = " from ";
	private static final String SQL_CONDITIONAL = " where ";

	public static String getSqlOfUpdate(String tableName,
								List<SqlColumns> columns,
								SqlColumns conditioner) {
		String updateSql = getUpdateSqlWithTableName(tableName);

		StringBuffer sb = new StringBuffer();
		sb.append(updateSql);
		toMapper(columns, sb);
		sb.append(SQL_CONDITIONAL)
			.append(conditioner.getColumnName())
			.append(SQL_SUBSTITUTE)
			.append(conditioner.getNamedParameter());
		return sb.toString();
	}

	private static String getUpdateSqlWithTableName(String tableName) {
		return SQL_UPDATE_PREFIX + tableName + SQL_UPDATE_POSTFIX;
	}

	private static void toMapper(List<SqlColumns> columns, StringBuffer sb) {
		for (SqlColumns column : columns) {
			sb.append(column.getColumnName())
				.append(SQL_SUBSTITUTE)
				.append(column.getUpdateParameter())
				.append(SQL_COMMA);
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	public static String getSqlOfSelect(String tableName, List<SqlColumns> columns, SqlColumns conditioner) {
		StringBuffer sb = new StringBuffer();
		String selectSuffix = getSelectSuffixSqlWithTableName(tableName);
		sb.append(SQL_SELECT_PREFIX);
		addColumnNames(columns, sb);
		sb.append(selectSuffix);

		if (!Objects.isNull(conditioner.getColumnName())) {
			sb.append(SQL_CONDITIONAL)
				.append(conditioner.getColumnName())
				.append(SQL_SUBSTITUTE)
				.append(conditioner.getNamedParameter());
		}
		return sb.toString();
	}

	private static String getSelectSuffixSqlWithTableName(String tableName) {
		return SQL_SELECT_POSTFIX + tableName;
	}

	private static void addColumnNames(List<SqlColumns> columns, StringBuffer sb) {
		for (SqlColumns column : columns) {
			sb.append(column.getColumnName())
				.append(SQL_COMMA);
		}
		sb.deleteCharAt(sb.length() - 1);
	}
}
