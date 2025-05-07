package ua.karazin.moviesorderservice.profile;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.karazin.moviesbaseevents.moneyaccount.revision1.AccountCreatedEvent1;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileCreatedEvent1;
import ua.karazin.moviesbaseevents.profiles.revision1.ProfileUpdatedEvent1;

@Component
@RequiredArgsConstructor
public class ProfileProjectionHandler {
    private final ProfileRepository repository;

    @EventHandler
    private void on(ProfileCreatedEvent1 event) {
        repository.save(Profile.builder()
                .id(event.id())
                .name(event.dto().name())
                .email(event.dto().email())
                .build());
    }

    @EventHandler
    @Transactional
    public void on(ProfileUpdatedEvent1 event) {
        var profile = repository.findById(event.id()).orElseThrow();
        profile.setName(event.dto().name());
        profile.setEmail(event.dto().email());
        repository.save(profile);
    }

    @EventHandler
    @Transactional
    public void on(AccountCreatedEvent1 event) {
        var profile = repository.findById(event.profileId()).orElseThrow();
        profile.setAccountId(event.accountId());
        repository.save(profile);
    }
}
