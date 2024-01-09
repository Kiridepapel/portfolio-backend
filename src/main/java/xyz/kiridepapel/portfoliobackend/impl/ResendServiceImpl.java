package xyz.kiridepapel.portfoliobackend.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Value("${APP_PRODUCTION}")
    private String APP_PRODUCTION;

    @Autowired
    private LogEmailRepository logEmailRepository;

    private int maxEmailsPerDay = 2;
    private int hoursToMinus = 5;
    private String mainEmailFrom = "portfolio@monalek.xyz";
    private List<String> mainEmailTo = List.of("brian.uceda@hotmail.com", "monalek22@gmail.com");

    public ResponseDTO sendEmail(ResendRequestDTO rq) {
        Resend resend = new Resend(RESEND_SECRET_KEY);
        String ip = getClientIp();

        validateData(rq);

        if(!canSendMoreMails(ip)) {
            throw new FailSendEmail("No puedes enviar más emails por hoy!");
        };

        CreateEmailOptions params = createEmail(rq);

        try {
            CreateEmailResponse data = resend.emails().send(params);
            saveLogEmail(rq, ip, data.getId());
            return new ResponseDTO("Email enviado correctamente!", 200);
        } catch (ResendException e) {
            e.printStackTrace();
            throw new FailSendEmail("Ocurrió un error al enviar el email!");
        }
    }

    private void validateData(ResendRequestDTO rq) {
        if (rq.getTitle() == null || rq.getTitle().isEmpty()) {
            throw new FailSendEmail("El título es obligatorio!");
        }
        if (rq.getEmail() == null || rq.getEmail().isEmpty()) {
            throw new FailSendEmail("El email es obligatorio!");
        }
        if (rq.getMessage() == null || rq.getMessage().isEmpty()) {
            throw new FailSendEmail("Debes escribir un mensaje!");
        }
        if (rq.getSendMeCopy() == null) {
            throw new FailSendEmail("Debes indicar si quieres recibir una copia del correo!");
        }
    }

    private CreateEmailOptions createEmail(ResendRequestDTO rq) {
        List<String> emailsTo = new ArrayList<>(this.mainEmailTo);

        if (rq.getSendMeCopy()) {
            emailsTo.add(rq.getEmail());
        }

        Timestamp now = Timestamp.valueOf(LocalDateTime.now().minusHours(this.hoursToMinus));
        if (this.APP_PRODUCTION.equals("false")) {
            now = Timestamp.valueOf(LocalDateTime.now());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return CreateEmailOptions.builder()
            .to(emailsTo)
            .from(mainEmailFrom)
            .subject("Portfolio - " + rq.getTitle())
            .html(
                "<div style='padding: 10px; background: #ebebeb; border-radius: 10px; margin-bottom: 12px; display: flex; flex-wrap: wrap'>" +
                    "<p style='color: #6200ff; font-size: 14px; width: 50%; text-align: center;'>Email: " +
                        "<span style='color: #343434'>" + rq.getEmail() + "</span>" +
                    "</p>" +
                    "<p style='color: #6200ff; font-size: 14px; width: 50%; text-align: center;'>Date: " +
                        "<span style='color: #343434'>" + dateFormat.format(now) + "</span>" +
                    "</p>" +
                "</div>" +
                "<div style='padding: 50px 20%; background: #ebebeb; border-radius: 10px; text-align: center;'>" +
                    "<span style='color: #343434; font-size: 14px;'>" + rq.getMessage() + "</span>" +
                "</div>"
            )
            .build();
    }

    private boolean canSendMoreMails(String ip) {
        Timestamp yesterday = Timestamp.valueOf(LocalDateTime.now().minusDays(1).minusHours(this.hoursToMinus));
        Timestamp now = Timestamp.valueOf(LocalDateTime.now().minusHours(this.hoursToMinus));

        if (this.APP_PRODUCTION.equals("false")) {
            yesterday = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
            now = Timestamp.valueOf(LocalDateTime.now());
        }

        Long quantity = logEmailRepository.countEmailsByIpBetweenDates(ip, yesterday, now);

        return !(quantity >= maxEmailsPerDay);
    }

    private void saveLogEmail(ResendRequestDTO rq, String ip, String id) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now().minusHours(this.hoursToMinus));

        if (this.APP_PRODUCTION.equals("false")) {
            now = Timestamp.valueOf(LocalDateTime.now());
        }

        LogEmailEntity logEmailEntity = LogEmailEntity.builder()
            .title(rq.getTitle())
            .email(rq.getEmail())
            .message(rq.getMessage())
            .sendMeCopy(rq.getSendMeCopy())
            .createdAt(now)
            .responseId(id)
            .ip(ip)
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

        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }
}
