package xyz.kiridepapel.portfoliobackend.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import jakarta.servlet.http.HttpServletRequest;
import xyz.kiridepapel.portfoliobackend.dto.ResponseDTO;
import xyz.kiridepapel.portfoliobackend.entity.LogEmailEntity;
import xyz.kiridepapel.portfoliobackend.dto.ResendDTO.ResendRequestDTO;
import xyz.kiridepapel.portfoliobackend.exception.ResendExceptions.FailSendEmail;
import xyz.kiridepapel.portfoliobackend.repository.LogEmailRepository;

@Service
public class ResendServiceImpl {
    @Value("${RESEND_SECRET_KEY}")
    private String RESEND_SECRET_KEY;

    @Autowired
    private LogEmailRepository logEmailRepository;

    private String mainEmailFrom = "portfolio@monalek.xyz";
    private List<String> mainEmailTo = List.of("brian.uceda@hotmail.com", "monalek22@gmail.com");

    public ResponseDTO sendEmail(ResendRequestDTO rq) {
        Resend resend = new Resend(RESEND_SECRET_KEY);

        validateData(rq);
        if(cantSendMoreMails(rq)) {
            throw new FailSendEmail("You can't send more than 3 emails per day!");
        };

        CreateEmailOptions params = createEmail(rq);

        try {
            CreateEmailResponse data = resend.emails().send(params);
            saveLogEmail(rq, data);
            return new ResponseDTO("Email sent successfully!", 200);
        } catch (ResendException e) {
            e.printStackTrace();
            throw new FailSendEmail("Error sending email: " + e.getMessage());
        }
    }

    private void validateData(ResendRequestDTO rq) {
        if (rq.getTitle() == null || rq.getTitle().isEmpty()) {
            throw new FailSendEmail("Title is required!");
        }
        if (rq.getEmail() == null || rq.getEmail().isEmpty()) {
            throw new FailSendEmail("Email is required!");
        }
        if (rq.getBody() == null || rq.getBody().isEmpty()) {
            throw new FailSendEmail("Body is required!");
        }
        if (rq.getSendMeCopy() == null) {
            throw new FailSendEmail("You need to specificate if you can send a copy to you!");
        }
    }

    private CreateEmailOptions createEmail(ResendRequestDTO rq) {
        List<String> emailsTo = new ArrayList<>(this.mainEmailTo);

        if (rq.getSendMeCopy()) {
            emailsTo.add(rq.getEmail());
        }

        LocalDateTime now = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.SECONDS);

        return CreateEmailOptions.builder()
            .to(emailsTo)
            .from(mainEmailFrom)
            .subject("Portfolio - " + rq.getTitle())
            .html(
                "<div style='padding: 10px; background: #ebebeb; border-radius: 10px; margin-bottom: 12px;'>" +
                    "<p style='color: #6200ff; font-size: 14px; width: 50%; display: inline-block; text-align: center;'>Email: " +
                        "<span style='color: #343434'>" + rq.getEmail() + "</span>" +
                    "</p>" +
                    "<p style='color: #6200ff; font-size: 14px; width: 50%; display: inline-block; text-align: center;'>Date: " +
                        "<span style='color: #343434'>" + Timestamp.valueOf(now) + "</span>" +
                    "</p>" +
                "</div>" +
                "<div style='padding: 50px 20%; background: #ebebeb; border-radius: 10px;'>" +
                    "<span style='color: #343434; font-size: 14px;'>" + rq.getBody() + "</span>" +
                "</div>"
            )
            .build();
    }

    private boolean cantSendMoreMails(ResendRequestDTO rq) {
        Timestamp yesterday = Timestamp.valueOf(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS).minusDays(1));
        Timestamp now = Timestamp.valueOf(LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS));

        List<LogEmailEntity> logEmails = logEmailRepository.findByIpAndCreatedAtBetween(rq.getIp(), yesterday, now);

        return logEmails.size() >= 3;
    }

    private void saveLogEmail(ResendRequestDTO rq, CreateEmailResponse data) {
        LogEmailEntity logEmailEntity = LogEmailEntity.builder()
            .title(rq.getTitle())
            .email(rq.getEmail())
            .body(rq.getBody())
            .sendMeCopy(rq.getSendMeCopy())
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .responseId(data.getId())
            .ip(rq.getIp())
            .cityName(rq.getCityName())
            .build();

        logEmailRepository.save(logEmailEntity);
    }

    public String getClientIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}
