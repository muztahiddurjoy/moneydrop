package org.moneydrop.app.DataClasses;

public class EmailSenderDataset {
    String to;
    String from;
    String subject;
    String text;
    String html;

    public EmailSenderDataset(String to, String from, String subject, String text, String html) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
        this.html = html;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
