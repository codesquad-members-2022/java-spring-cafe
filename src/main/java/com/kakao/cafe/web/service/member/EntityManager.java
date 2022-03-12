package com.kakao.cafe.web.service.member;

import com.kakao.cafe.config.Database;
import com.kakao.cafe.core.domain.article.Article;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

public class EntityManager<T> {

    private final Database database;

    public EntityManager(Database database) {
        this.database = database;
    }

    private static final String CREATE_AT = "createAt";
    private static final String LAST_MODIFIEDAT = "lastModifiedAt";

    public T persist(T entity) {
        String type = getClazz(entity);
        if (!database.getIdDatabase(type).contains(getId(entity).get())) {
            return database.save(type, entity);
        }
        return entity;
    }

    public Optional<Object> getId(T entity) {
        List<Field> fields = getAllFields(entity);
        for (Field field : fields) {
            Field entityFieldName = getFieldByName(entity, field.getName());
            if (entityFieldName.getName().equals("id")) {
                return Optional.ofNullable(getFieldValue(entity, entityFieldName.getName()));
            }
        }
        return Optional.empty();
    }

    public String getClazz(T entity) {
        Class<?> clazz = entity.getClass();
        return clazz.getSimpleName();
    }

    private <T, R> boolean isChanged(T originalEntity, R checkObject) {
        List<Field> fields = getAllFields(checkObject);
        for (Field field : fields) {
            Field originalEntityFieldName = getFieldByName(originalEntity, field.getName());
            Field checkObjectFieldName = getFieldByName(checkObject, field.getName());
            if (isAboutDate(originalEntityFieldName)) {
                continue;
            }
            if (hasChangedColumn(originalEntity, originalEntityFieldName, checkObject, checkObjectFieldName)) {
                return false;
            }
        }
        return true;
    }

    private <T> List<Field> getAllFields(T t) {
        Objects.requireNonNull(t);

        Class<?> clazz = t.getClass();
        List<Field> fields = new ArrayList<>();

        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private <T> Field getFieldByName(T t, String fieldName) {
        Objects.requireNonNull(t);

        Field field = null;
        for (Field eachField : getAllFields(t)) {
            if (eachField.getName().equals(fieldName)) {
                field = eachField;
                break;
            }
        }
        if (field != null) {
            field.setAccessible(true);
        }
        return field;
    }

    private boolean isAboutDate(Field field) {
        return field.getName().equals(CREATE_AT) || field.getName().equals(LAST_MODIFIEDAT);
    }

    private <T> boolean hasChangedColumn(T t, Field tField, T r, Field rField) {
        Optional<T> tOptionalValue = Optional.ofNullable(getFieldValue(t, tField.getName()));
        Optional<T> rOptionalValue = Optional.ofNullable(getFieldValue(r, rField.getName()));
        return !tOptionalValue.equals(rOptionalValue);
    }

    private <T> T getFieldValue(Object object, String fieldName) {
        Objects.requireNonNull(object);
        try {
            Field field = getFieldByName(object, fieldName);
            @SuppressWarnings("unchecked")
            T t = (T) field.get(object);
            return t;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Article articleA = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        Article articleB = new Article(1, "루시드", "그렇다", "Jun", LocalDateTime.now(), LocalDateTime.now(), 3);
        EntityManager<Article> entityManager = new EntityManager<>(new Database());
    }

    public List<Article> getArticles() {
        return database.getArticles();
    }
}
