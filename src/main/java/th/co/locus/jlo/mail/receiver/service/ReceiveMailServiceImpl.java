package th.co.locus.jlo.mail.receiver.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail2.jakarta.util.MimeMessageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.activation.DataSource;
import jakarta.mail.FetchProfile;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import th.co.locus.jlo.common.bean.ServiceResult;
import th.co.locus.jlo.common.service.BaseService;
import th.co.locus.jlo.common.util.CommonUtil;
import th.co.locus.jlo.mail.inbound.InboundReceiveMailService;
import th.co.locus.jlo.mail.inbound.att.InboundAttachmentReceiveMailService;
import th.co.locus.jlo.mail.inbound.att.bean.InboundAttachmentReceiveMailBean;
import th.co.locus.jlo.mail.inbound.bean.InboundReceiveMailBean;
import th.co.locus.jlo.system.file.FileService;
import th.co.locus.jlo.system.file.modelbean.FileModelBean;

@Slf4j
@Service
public class ReceiveMailServiceImpl extends BaseService implements ReceiveMailService {

	@Autowired
	private InboundReceiveMailService inboundReceiveMailService;

	@Autowired
	private FileService fileService;

	@Autowired
	private InboundAttachmentReceiveMailService attReceiveMailService;

	@Value("${attachment.path.email.inbound.att}")
	private String emailFilePath;

	private static final String DOWNLOADED_MAIL_FOLDER = "DOWNLOADED";

	@Override
	public void handleReceivedMail(MimeMessage receivedMessage) {
		log.debug("handleReceivedMail : {}", receivedMessage);

		try {
			log.debug("try handleReceivedMail : {}", receivedMessage);
			Folder folder = receivedMessage.getFolder();
			folder.open(Folder.READ_WRITE);

			Message[] messages = folder.getMessages();
			fetchMessagesInFolder(folder, messages);

			Arrays.asList(messages).stream().filter(message -> {
				MimeMessage currentMessage = (MimeMessage) message;
				try {
					return currentMessage.getMessageID().equalsIgnoreCase(receivedMessage.getMessageID());
				} catch (MessagingException e) {
					log.error("Error occurred during process message", e);
					return false;
				}
			}).forEach(this::extractMail);

			copyMailToDownloadedFolder(receivedMessage, folder);

			folder.close(true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	private void fetchMessagesInFolder(Folder folder, Message[] messages) throws MessagingException {
		log.debug("fetchMessagesInFolder : folder {} ,messages {}", folder, messages);
		FetchProfile contentsProfile = new FetchProfile();
		contentsProfile.add(FetchProfile.Item.ENVELOPE);
		contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
		contentsProfile.add(FetchProfile.Item.FLAGS);
		contentsProfile.add(FetchProfile.Item.SIZE);
		folder.fetch(messages, contentsProfile);
	}

	private void copyMailToDownloadedFolder(MimeMessage mimeMessage, Folder folder) throws MessagingException {

		Store store = folder.getStore();
		Folder downloadedMailFolder = store.getFolder(DOWNLOADED_MAIL_FOLDER);
		if (downloadedMailFolder.exists()) {
			downloadedMailFolder.open(Folder.READ_WRITE);
			downloadedMailFolder.appendMessages(new MimeMessage[] { mimeMessage });
			downloadedMailFolder.close(true);
		}
	}

	private void extractMail(Message message) {
		log.debug("extractMail {}", message);
		try {
			final MimeMessage messageToExtract = (MimeMessage) message;
			final org.apache.commons.mail2.jakarta.util.MimeMessageParser mimeMessageParser = new MimeMessageParser(
					messageToExtract).parse();

			showMailContent(mimeMessageParser);

			// downloadAttachmentFiles(mimeMessageParser);

			// To delete downloaded email
			messageToExtract.setFlag(Flags.Flag.DELETED, true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void showMailContent(MimeMessageParser mimeMessageParser) throws Exception {
		log.debug("From: {} to: {} | Subject: {}", mimeMessageParser.getFrom(), mimeMessageParser.getTo(),
				mimeMessageParser.getSubject());
		log.debug("Mail content: {}", mimeMessageParser.getPlainContent());

		InboundReceiveMailBean emailInbound = new InboundReceiveMailBean();
		emailInbound.setFormEmail(mimeMessageParser.getFrom());
		emailInbound.setToEmail(mimeMessageParser.getTo().toString());
		emailInbound.setToCcEmail(mimeMessageParser.getCc().size() > 0 ? mimeMessageParser.getCc().toString() : null);
		emailInbound
				.setToBccEmail(mimeMessageParser.getBcc().size() > 0 ? mimeMessageParser.getBcc().toString() : null);
		emailInbound.setReplyToEmail(mimeMessageParser.getReplyTo());
		emailInbound.setSubjectEmail(mimeMessageParser.getSubject());
		emailInbound.setPlainContent(mimeMessageParser.getPlainContent());
		emailInbound.setHtmlContent(mimeMessageParser.getHtmlContent());
		emailInbound.setStatusCd("01");
		emailInbound.setCreatedBy((long) 41);
		emailInbound.setUpdatedBy((long) 41);
		emailInbound.setBuId(1);

		ServiceResult<InboundReceiveMailBean> serviceResult = inboundReceiveMailService
				.insertEmailInbound(emailInbound);
		if (serviceResult.isSuccess()) {
			InboundReceiveMailBean ibReceiveMailBean = serviceResult.getResult();
			downloadAttachmentFiles(mimeMessageParser, ibReceiveMailBean);

		}

	}

	private void downloadAttachmentFiles(MimeMessageParser mimeMessageParser,
			InboundReceiveMailBean ibReceiveMailBean) {
		log.debug("Email has {} attachment files", mimeMessageParser.getAttachmentList().size());

		mimeMessageParser.getAttachmentList().forEach(dataSource -> {
			if (StringUtils.isNotBlank(dataSource.getName())) {

				saveFileAttachment(dataSource, ibReceiveMailBean);

			}

		});
	}

	private void saveFileAttachment(DataSource dataSource, InboundReceiveMailBean ibReceiveMailBean) {
		try {

			String mailId = "inbound";
			byte[] inByte = IOUtils.toByteArray(dataSource.getInputStream());
			MultipartFile result = new MockMultipartFile(dataSource.getName(), dataSource.getName(),
					dataSource.getContentType(), inByte);
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String newFileName = mailId + "_" + timeStamp + CommonUtil.getFileExtension(result);

			FileModelBean fileBean = new FileModelBean();
			fileBean.setFilePath(emailFilePath);
			fileBean.setFileExtension(CommonUtil.getFileExtension(result));
			fileBean.setFileName(newFileName);
			fileBean.setFileSize(result.getSize());
			fileBean.setCreatedBy((long) 41);
			fileBean.setUpdatedBy((long) 41);
			
			log.info("***fileService :{}",fileService);
			log.info("***fileBean :{}",fileBean);
			
			fileBean = fileService.createAttachment(fileBean).getResult();
			log.debug("Create Attachment : {} ", fileBean);
			fileService.saveFile(result, emailFilePath, fileBean.getFileName());
			log.debug("saveFile Success ");

//			 insert relation tb_email_inbound_attachment
			InboundAttachmentReceiveMailBean beanAtt = new InboundAttachmentReceiveMailBean();
			beanAtt.setEmailInboundId(ibReceiveMailBean.getId());
			beanAtt.setAttachmentId(fileBean.getAttId());
			beanAtt.setBuId(1);
			beanAtt.setCreatedBy((long) 41);
			beanAtt.setUpdatedBy((long) 41);
			attReceiveMailService.createEmailInboundAtt(beanAtt);
			
		
			
		} catch (Exception e) {
			log.error("Save File Attachment : {} ", e.getMessage());
			e.printStackTrace();
		}

	}
	
 

	@SuppressWarnings("unused")
	private void createDirectoryIfNotExists(String directoryPath) {
		if (!Files.exists(Path.of(directoryPath))) {
			try {
				Files.createDirectories(Path.of(directoryPath));
			} catch (IOException e) {
				log.error("An error occurred during create folder: {}", directoryPath, e);
			}
		}
	}

	
}
