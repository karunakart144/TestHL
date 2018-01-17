package com.igate.iconnect.util;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class COMMON_EmailApproval_MailSender {
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	private static Logger log = Logger.getLogger(COMMON_EmailApproval_MailSender.class);
	public void sendMail(final String toAddress,final String ticketID,final String reason,final String source,final String subject){
		
		ResourceBundle bundle = ResourceBundle.getBundle("mail");
		String fromAddress="";
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "");
		final String logoPath=path+bundle
				.getString("mail.logo");
		String vtFileNameToSend="";
		if(source.equalsIgnoreCase("ICONNECT")){
			fromAddress=bundle
					.getString("EmailApprovalHD.fromAddress");
		if(reason.equalsIgnoreCase("TicketID")){
			vtFileNameToSend="invalidTicketHDVM.vm";
		}else if(reason.equalsIgnoreCase("Ticket Status")){
			vtFileNameToSend="ticketStatusHDVM.vm";
		}else if(reason.equalsIgnoreCase("Approver Invalid")){
			vtFileNameToSend="invalidApproverHDVM.vm";
		}
		}else if(source.equalsIgnoreCase("TECH-CR")){
			fromAddress=bundle
					.getString("EmailApprovalTechCR.fromAddress");
			if(reason.equalsIgnoreCase("TicketID")){
				vtFileNameToSend="invalidTicketTechCRVM.vm";
			}else if(reason.equalsIgnoreCase("Ticket Status")){
				vtFileNameToSend="ticketStatusTechCRVM.vm";
			}else if(reason.equalsIgnoreCase("Approver Invalid")){
				vtFileNameToSend="invalidApproverTechCRVM.vm";
			}
		}
		
		final String vmFileName=vtFileNameToSend;
		final String fromAddressToSet=fromAddress;
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) {
				try {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage, true);

					message.setTo(toAddress);
					
					message
							.setFrom(new InternetAddress(
									fromAddressToSet));
					message.setSubject(subject);
					Map model = new HashMap();
					model.put("TICKET_ID", ticketID);
					String text = VelocityEngineUtils
							.mergeTemplateIntoString(
									velocityEngine,
									"com/igate/iconnect/util/"
											+ vmFileName, model);
					message.setText(text, true);
					FileSystemResource res = new FileSystemResource(
							new File(logoPath));
					message.addInline("logo", res);
				} catch (AddressException e) {
					System.err
							.println("AddressException occure while putting the sucess message.."
									+ "Request Id->"
									+ ticketID
									+ "ToAddress->" + toAddress);
					log.error("AddressException occure while putting the sucess message.."
									+ "Request Id->"
									+ ticketID
									+ "ToAddress->" + toAddress);
				} catch (VelocityException e) {
					System.err
							.println("VelocityException occure while putting the sucess message.."
									+ "Request Id->"
									+ ticketID
									+ "ToAddress->" + toAddress);
					log.error("VelocityException occure while putting the sucess message.."
							+ "Request Id->"
							+ ticketID
							+ "ToAddress->" + toAddress);
				} catch (MessagingException e) {
					System.err
							.println("MessagingException occure while putting the sucess message.."
									+ "Request Id->"
									+ ticketID
									+ "ToAddress->" + toAddress);
					log.error("MessagingException occure while putting the sucess message.."
							+ "Request Id->"
							+ ticketID
							+ "ToAddress->" + toAddress);
				}
				
			}
		};
		try {
			this.mailSender.send(preparator);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

