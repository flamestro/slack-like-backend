package de.tub.ise.anwsys.repositories;

import de.tub.ise.anwsys.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Integer> {
}
