package com.belyaev.service.hiber;

import com.belyaev.service.enums.Gender;
import com.belyaev.service.enums.Role;
import com.belyaev.service.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("Ivan")
                    .birthDate(LocalDate.of(2000, 10, 10))
                    .gender(Gender.MALE)
                    .email("mail@mail.ru")
                    .password("1234")
                    .role(Role.USER)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}
