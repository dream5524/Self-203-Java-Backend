package com.kms.seft203.repository;

import com.kms.seft203.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableSpringDataWebSupport
public interface ContactRepository extends JpaRepository<Contact, Integer>, PagingAndSortingRepository<Contact, Integer> {

    @Query("select contact from Contact contact where contact.user.email = ?1")
    Optional<Contact> findByEmail(String email);

    @Query("select c from Contact c" +
            " where (c.id = :id or :id is null)" +
            " and   (CONCAT(c.firstName, ' ' , c.lastName) like :fullName or :fullName is null)" +
            " and   (c.title = :title or :title is null)")
    Page<Contact> findAllByInputField(Integer id, String fullName, String title, Pageable pageable);
}
