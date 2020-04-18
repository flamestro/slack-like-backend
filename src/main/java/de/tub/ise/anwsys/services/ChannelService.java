package de.tub.ise.anwsys.services;

import de.tub.ise.anwsys.errors.ChannelAlreadyExistsException;
import de.tub.ise.anwsys.errors.ChannelNotFoundException;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.dto.ChannelDTO;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel getChannel(int id) throws ChannelNotFoundException {
        if (channelRepository.findById(id).isPresent()) {
            return channelRepository.findById(id).get();
        } else {
            throw new ChannelNotFoundException();
        }
    }

    public Channel postChannel(ChannelDTO rawChannel) throws ChannelAlreadyExistsException {
        if (!(channelRepository.findAll().stream().filter(channel -> channel.getName().equals(rawChannel.getName())).count() >= 1)) {
            Channel channel = mapChannelData(rawChannel);
            channelRepository.save(channel);
            return channel;
        } else {
            throw new ChannelAlreadyExistsException();
        }
    }

    protected Channel mapChannelData(ChannelDTO channelDTO) {
        Channel channel = new Channel();
        channel.setTopic(channelDTO.getTopic());
        channel.setName(channelDTO.getName());
        channel.setMessageList(null);
        return channel;
    }
}
