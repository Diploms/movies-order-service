package ua.karazin.moviesorderservice.service.mail;

import org.springframework.lang.NonNull;

public interface EmailService {
    void sendEmail(@NonNull EmailDetails details);
}
