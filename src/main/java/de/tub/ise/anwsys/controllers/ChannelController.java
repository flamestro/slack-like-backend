package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.errors.ChannelAlreadyExistsException;
import de.tub.ise.anwsys.errors.ChannelNotFoundException;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.dto.ChannelDTO;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.services.ChannelService;
import de.tub.ise.anwsys.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelRepository channelRepository;

    private final ChannelService channelService;

    private final UserService userService;

    @Autowired
    public ChannelController(UserService userService, ChannelService channelService, ChannelRepository channelRepository) {
        this.userService = userService;
        this.channelService = channelService;
        this.channelRepository = channelRepository;
    }

    @GetMapping(produces = "application/json")
    public PagedResources<Channel> getChannels(@PageableDefault Pageable p, PagedResourcesAssembler assembler) {
        Page<Channel> channels = channelRepository.findAll(p);
        return assembler.toResource(channels);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getChannel(@PathVariable("id") int channelId) {
        try {
            Channel channel = channelService.getChannel(channelId);
            return (new ResponseEntity<>(channel, HttpStatus.OK));
        } catch (ChannelNotFoundException e) {
            return (new ResponseEntity<>("No such Channel", HttpStatus.OK));
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> postChannel(@RequestBody ChannelDTO rawChannel) {
        try {
            Channel channel = channelService.postChannel(rawChannel);
            return (new ResponseEntity<>(channel, HttpStatus.OK));
        } catch (ChannelAlreadyExistsException e) {
            return (new ResponseEntity<>("Channel is already existing", HttpStatus.CONFLICT));
        }
    }

    @GetMapping(value = "/{id}/users", consumes = "application/json")
    private ResponseEntity<?> getChannelUsers(@PathVariable("id") int channelId) {
        return new ResponseEntity<>(userService.getChannelUsers(channelId), HttpStatus.OK);
    }
}

