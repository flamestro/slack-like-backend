package de.tub.ise.anwsys.services;

import de.tub.ise.anwsys.errors.ChannelNotFoundException;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.Message;
import de.tub.ise.anwsys.dto.MessageDTO;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageService {

    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.channelRepository = channelRepository;
    }

    public Message postMessage(int channelId, MessageDTO rawMessage) throws ChannelNotFoundException {
        if (channelRepository.findById(channelId).isPresent()) {
            Channel actualChannel = channelRepository.findById(channelId).get();
            Message msg = mapMessageData(rawMessage, actualChannel);
            messageRepository.save(msg);
            addMessage(channelId, msg);
            channelRepository.save(actualChannel);
            return msg;
        } else {
            throw new ChannelNotFoundException();
        }
    }

    public Page<Message> getMessages(int channelId) throws ChannelNotFoundException {
        if (channelRepository.findById(channelId).isPresent()) {
            Pageable pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"));
            Channel actualChannel = channelRepository.findById(channelId).get();
            return messageRepository.findByChannel(actualChannel, pageable);
        } else {
            throw new ChannelNotFoundException();
        }
    }

    protected Message mapMessageData(MessageDTO messageDTO, Channel channel) {
        Message message = new Message();
        message.setTimestamp(Instant.now());
        message.setContent(messageDTO.getContent());
        message.setCreator(messageDTO.getCreator());
        message.setChannel(channel);
        return message;
    }

    public void addMessage(int channelId, Message message) throws ChannelNotFoundException {
        if (channelRepository.findById(channelId).isPresent()) {
            Channel actualChannel = channelRepository.findById(channelId).get();
            actualChannel.getMessageList().add(message);
            channelRepository.save(actualChannel);
        } else {
            throw new ChannelNotFoundException();
        }
    }
}
