package th.co.locus.jlo.mail.receiver.service;


import jakarta.mail.internet.MimeMessage;

public interface ReceiveMailService {
	void handleReceivedMail(MimeMessage message);
}
