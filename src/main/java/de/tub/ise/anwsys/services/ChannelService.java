package de.tub.ise.anwsys.services;

import de.tub.ise.anwsys.errors.ChannelAlreadyExistsException;
import de.tub.ise.anwsys.errors.ChannelNotFoundExeption;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.ChannelJSON;
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

    public Channel getChannel(int id) throws ChannelNotFoundExeption {
        if (channelRepository.findById(id).isPresent()) {
            Channel channel = channelRepository.findById(id).get();
            return channel;
        } else {
            throw new ChannelNotFoundExeption();
        }
    }

    public Channel postChannel(ChannelJSON c) throws ChannelAlreadyExistsException {

        if (!(channelRepository.findAll().stream().filter(channel -> channel.getName().equals(c.getName())).count() >= 1)) {
            Channel channel = mapChannelData(c);
            channelRepository.save(channel);
            return channel;
        } else {
            throw new ChannelAlreadyExistsException();
        }
    }


    protected Channel mapChannelData(ChannelJSON channelJSON) {
        Channel channel = new Channel();
        channel.setTopic(channelJSON.getTopic());
        channel.setName(channelJSON.getName());
        channel.setMessageList(null);
        return channel;
    }

}
