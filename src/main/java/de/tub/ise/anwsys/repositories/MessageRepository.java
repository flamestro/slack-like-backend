package de.tub.ise.anwsys.repositories;

import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Integer> {
    Page<Message> findByChannel(Channel channel, Pageable pageable);
}
