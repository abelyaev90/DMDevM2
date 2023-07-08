package com.belyaev.service.hiber;

import com.belyaev.service.entity.User;
import com.belyaev.service.enums.Gender;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("Ivan")
                .gender(Gender.MALE)
                .email("123@mail.ru")
                .birthDate(LocalDate.of(2000, 10, 10))
                .password("123")
                .image("1111")
                .build();

        String sql = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(joining(", "));

        String columnValues = Arrays.stream(declaredFields)
                .map(field ->"?")
                .collect(joining(", "));

        System.out.println(sql.formatted(tableName, columnNames, columnValues));

//        Connection connection = null;
//        PreparedStatement prepareStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            prepareStatement.setObject(1, declaredField.get(user));
//        }

    }
}