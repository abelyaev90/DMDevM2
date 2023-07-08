package com.belyaev.service.hiber;

import com.belyaev.service.entity.Role;
import com.belyaev.service.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateRunner {

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("Ivan")
                    .birthDate(LocalDate.of(2000, 10, 10))
                    .gender("male")
                    .email("mail@mail.ru")
                    .pass("1234")
                    .role(Role.USER)
                    .build();
            session.save(user);

            session.getTransaction().commit();
        }
    }
}
