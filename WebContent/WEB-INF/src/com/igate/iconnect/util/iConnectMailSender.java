package com.igate.iconnect.util;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.igate.iconnect.BO.MailSender;
import com.igate.iconnect.constants.MailSenderConstants;





public class iConnectMailSender {
		private static Logger log = Logger.getLogger(iConnectMailSender.class);

		private JavaMailSender mailSender;
		private VelocityEngine velocityEngine;
		private JdbcTemplate jdbcTemplate;
		private JavaMailSender mailSenderTechCR;
		private String fromAddress;

		public void setVelocityEngine(VelocityEngine velocityEngine) {
			this.velocityEngine = velocityEngine;
		}
		
		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}

		public void setMailSender(JavaMailSender mailSender) {
			this.mailSender = mailSender;
		}
		
		public void setMailSenderTechCR(JavaMailSender mailSenderTechCR) {
			this.mailSenderTechCR = mailSenderTechCR;
		}

		@Autowired
		public void init(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}

		public void SameStatusMailSender(String ticketID, HttpServletRequest request) {
			log.info("Same Status Mail Sender started");
			String EXPIRY_MAIL_REQUEST_DETAILS =MailSenderConstants.IC_SAME_STATUS_MAIL_TRIGGERING_QUERY;
			String mailType = "";

			List<MailSender> remRequestDetailsList = this.jdbcTemplate
					.query(EXPIRY_MAIL_REQUEST_DETAILS, new Object[] {ticketID},
							new RowMapper<MailSender>() {
								public MailSender mapRow(
										ResultSet rs1, int count)
										throws SQLException {
									MailSender obj1 = new MailSender();
									obj1.setTICKET_ID(rs1.getString("TICKET_ID"));
									obj1.setFUNCTION(rs1.getString("TICKET_FUNCTION"));
									obj1.setCATEGORY(rs1.getString("CATEGORY"));
									obj1.setSUB_CATEGORY(rs1.getString("SUB_CATEGORY"));
									obj1.setPRIORITY(rs1.getString("PRIORITY"));
									obj1.setCREATED_BY(rs1.getString("CREATED_BY"));
									obj1.setREQUESTED_BY(rs1.getString("REQUESTED_BY"));
									obj1.setPROJECT(rs1.getString("PROJECT"));
									obj1.setASSIGNED_GROUP(rs1.getString("ASSIGNED_GROUP"));
									obj1.setASSIGNED_TO(rs1.getString("ASSIGNED_TO"));
									obj1.setTO_ADDRESS(rs1.getString("TO_ADDRESS"));
									obj1.setCC_ADDRESS(rs1.getString("CC_ADDRESS"));
									obj1.setSUBJECT(rs1.getString("SUBJECT"));
									obj1.setDESCRIPTION(rs1.getString("DESCRIPTION"));
									obj1.setNAME(rs1.getString("CREATED_BY"));
									obj1.setSTATUS(rs1.getString("STATUS"));
									obj1.setCREATED_DATE(rs1.getString("CREATED_DATE"));
									obj1.setTICKET_COUNT(rs1.getString("TICKET_COUNT"));
									return obj1;
								}
							});

			if (remRequestDetailsList.size() > 0) {
				for (Iterator<MailSender> reqIterator = remRequestDetailsList
						.iterator(); reqIterator.hasNext();) {
					final MailSender reminderDetails = reqIterator
							.next();
					{
						
						final String vmFileName = "sameStatusMailTrigger.vm";
						String toAddrress = reminderDetails.getTO_ADDRESS();
						String ccAddrress=reminderDetails.getCC_ADDRESS();
						String[] toAddresses = toAddrress.split(",");
						final Set<String> toAddr = new TreeSet<String>();
						toAddr.addAll(Arrays.asList(toAddresses));
						final String[] finalToAddre = toAddr.toArray(new String[0]);
						String ticketCount=reminderDetails.getTICKET_COUNT();
						if(ticketCount.equalsIgnoreCase("0"))
						{
						mailType = "SameStatusMail";
						}
						else
						{
						mailType="SameStatusMail"+ticketCount;	
						}
						
						ResourceBundle bundle = ResourceBundle.getBundle("mail");
						final String fromAddress = bundle
								.getString("mail.fromAddress");
						
						ServletContext context = request.getSession().getServletContext();
						
						final String logoPath=context.getRealPath(bundle
								.getString("mail.logo"));
						String subjectFromMail = bundle
						.getString("mail.subject");
						subjectFromMail=subjectFromMail.replace("{","");
						subjectFromMail=subjectFromMail.replace("}","");
						subjectFromMail=subjectFromMail.replaceAll("TICKET_ID",reminderDetails.getTICKET_ID());
						subjectFromMail=subjectFromMail.replaceAll("FUNCTION",reminderDetails.getFUNCTION());
						subjectFromMail=subjectFromMail.replaceAll("STATUS",reminderDetails.getSTATUS());
						final String subject=subjectFromMail;
						String[] ccAddre = new String[100];
						String[] ccAddresses=ccAddrress.split(",");
						List<String> toAdd=Arrays.asList(toAddresses);
						List<String> ccAdd=Arrays.asList(ccAddresses);
						int a=0;
						for(int j=0;j<toAdd.size();j++){
							for(int k=0;k<ccAdd.size();k++){
								if(!toAdd.get(j).equalsIgnoreCase(ccAdd.get(k))){
									ccAddre[a]=ccAdd.get(k);
									a++;
							}
						}
						}
						final Set<String> ccAddr=new TreeSet<String>();
						List<String> list = new ArrayList<String>(Arrays.asList(ccAddre));
						list.removeAll(Arrays.asList("", null));
						ccAddr.addAll(list);
						final String[] finalCCAddre= ccAddr.toArray(new String[0]);
						MimeMessagePreparator preparator = new MimeMessagePreparator() {
							public void prepare(MimeMessage mimeMessage) {
								try {
									MimeMessageHelper message = new MimeMessageHelper(
											mimeMessage, true);

									message.setTo(finalToAddre);
									message.setCc(finalCCAddre);
									message
											.setFrom(new InternetAddress(
													fromAddress));
									message.setSubject(subject);
									Map model = new HashMap();

									model.put("helpDesk", reminderDetails);
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
													+ reminderDetails.getTICKET_ID()
													+ "ToAddress->" + finalToAddre);
									log.error("AddressException occure while putting the sucess message.."
													+ "Request Id->"
													+ reminderDetails.getTICKET_ID()
													+ "ToAddress->" + finalToAddre);
								} catch (VelocityException e) {
									System.err
											.println("VelocityException occure while putting the sucess message.."
													+ "Request Id->"
													+ reminderDetails.getTICKET_ID()
													+ "ToAddress->" + finalToAddre);
									log.error("VelocityException occure while putting the sucess message.."
											+ "Request Id->"
											+ reminderDetails.getTICKET_ID()
											+ "ToAddress->" + finalToAddre);
								} catch (MessagingException e) {
									System.err
											.println("MessagingException occure while putting the sucess message.."
													+ "Request Id->"
													+ reminderDetails.getTICKET_ID()
													+ "ToAddress->" + finalToAddre);
									log.error("MessagingException occure while putting the sucess message.."
											+ "Request Id->"
											+ reminderDetails.getTICKET_ID()
											+ "ToAddress->" + finalToAddre);
								}

							}
						};
						
						this.mailSender.send(preparator);
						this.jdbcTemplate
						.update(MailSenderConstants
								.query_to_update_remainder_tracker,
								ticketID, mailType);
						

					}

				}
			}
		
	}

}
