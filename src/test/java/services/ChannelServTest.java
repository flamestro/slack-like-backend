package services;

import de.tub.ise.anwsys.Application;
import de.tub.ise.anwsys.dto.ChannelDTO;
import de.tub.ise.anwsys.errors.ChannelAlreadyExistsException;
import de.tub.ise.anwsys.errors.ChannelNotFoundException;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.services.ChannelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.event.ChangeEvent;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ChannelServTest {

    @Autowired
    private ChannelService channelService;

    @Test
    public void whenPostValidChannel_thenChannelIsPersisted() throws ChannelNotFoundException, ChannelAlreadyExistsException {
        // given
        ChannelDTO rawChannel = new ChannelDTO("SomeName", "SomeTopic");

        // when
        channelService.postChannel(rawChannel);

        // then
        assertThat(channelService.getChannel(1).getName()).isEqualTo(rawChannel.getName());
    }

    @Test(expected = ChannelAlreadyExistsException.class)
    public void whenPostSameChannelNameTwice_thenChannelAlreadyExistsException() throws ChannelAlreadyExistsException {
        // given
        ChannelDTO rawChannel = new ChannelDTO("SomeName", "SomeTopic");

        // when
        channelService.postChannel(rawChannel);
        channelService.postChannel(rawChannel);

        //then Exception is thrown
    }

    @Test(expected = ChannelNotFoundException.class)
    public void whenGetChannelThatIsNotExisting_thenChannelNotFoundException() throws ChannelNotFoundException {
        // when
        channelService.getChannel(1);

        //then Exception is thrown
    }
}
