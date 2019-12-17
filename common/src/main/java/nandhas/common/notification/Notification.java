package nandhas.common.notification;

import java.util.HashMap;

import nandhas.common.utils.SpringContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

/**
 * NotificationDto
 */
public class Notification {

    private String from;

    private String to;

    private String cc;

    private String bcc;

    private String subject;

    private String mobileNumber;

    private String body;

    private NotificationTemplate notificationTemplate;

    private HashMap<String, Object> params;

    public Notification(String from, String to, String cc, String bcc, String subject, String mobileNumber, String body,
            NotificationTemplate notificationTemplate, HashMap<String, Object> params) {
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.mobileNumber = mobileNumber;
        this.body = body;
        this.notificationTemplate = notificationTemplate;
        this.params = params;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getCc() {
        return this.cc;
    }

    public String getBcc() {
        return this.bcc;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public NotificationTemplate getNotificationTemplate() {
        return this.notificationTemplate;
    }

    public HashMap<String,Object> getParams() {
        return this.params;
    }

    public void Send() {
        NotificationSource notificationSource = SpringContext.getBean(NotificationSource.class);
        notificationSource.output().send(MessageBuilder.withPayload(this).build());
    }

    @Override
    public String toString() {
        return "{" +
            " from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", cc='" + getCc() + "'" +
            ", bcc='" + getBcc() + "'" +
            ", subject='" + getSubject() + "'" +
            ", mobileNumber='" + getMobileNumber() + "'" +
            ", body='" + getBody() + "'" +
            ", notificationTemplate='" + getNotificationTemplate() + "'" +
            ", params='" + getParams() + "'" +
            "}";
    }

    public static class Builder {

        private String to;

        private String from;

        private String cc;

        private String bcc;

        private String subject;

        private String mobileNumber;

        private String body;

        private NotificationTemplate notificationTemplate;

        private HashMap<String, Object> params;

        public Builder(NotificationTemplate notificationTemplate) {
            this.notificationTemplate = notificationTemplate;
        }

        public Builder from(String from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder cc(String cc) {
            this.cc = cc;
            return this;
        }

        public Builder bcc(String bcc) {
            this.bcc = bcc;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder params(HashMap<String, Object> params) {
            this.params = params;
            return this;
        }

        public Notification build() {
            return new Notification(from, to, cc, bcc, subject, mobileNumber, body, notificationTemplate, params);
        }

    }

}