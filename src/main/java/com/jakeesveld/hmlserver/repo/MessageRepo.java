package com.jakeesveld.hmlserver.repo;

import com.jakeesveld.hmlserver.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {
}
