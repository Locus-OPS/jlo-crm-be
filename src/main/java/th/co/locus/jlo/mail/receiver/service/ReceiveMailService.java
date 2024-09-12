package th.co.locus.jlo.mail.receiver.service;

import javax.mail.internet.MimeMessage;

public interface ReceiveMailService {
	void handleReceivedMail(MimeMessage message);
}
