package de.tub.ise.anwsys.services;

import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.Message;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final ChannelRepository channelRepository;

    @Autowired
    public UserService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public ArrayList<String> getChannelUsers(int channelId) {

        ArrayList<Message> messages = new ArrayList<>();
        ArrayList<String> users = new ArrayList<>();
        if (channelRepository.findById(channelId).isPresent()) {
            Channel actualChannel = channelRepository.findById(channelId).get();
            messages.addAll(actualChannel.getMessageList());
        }

        for (Message m : messages) {
            users.add(m.getCreator());
        }
        Set<String> usersSet = new HashSet<>(users);
        users = new ArrayList<>(usersSet);

        return users;
    }
}
