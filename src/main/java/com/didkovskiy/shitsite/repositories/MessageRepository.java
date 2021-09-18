package com.didkovskiy.shitsite.repositories;

import com.didkovskiy.shitsite.domains.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByTag(String tag);
}
