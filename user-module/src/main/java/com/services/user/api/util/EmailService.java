package com.services.user.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.services.user.api.model.InvoiceTriggerEmailRequest;
import com.services.user.api.model.TriggerWinningBidEmail;
import com.services.user.api.model.User;
import com.services.user.api.services.UserService;

import freemarker.template.Configuration;

@Service("emailService")
public class EmailService {

    private JavaMailSender javaMailSender;
    
    @Autowired
    UserService userService;
    
    @Autowired
    @Qualifier("freeMarkerConfigCustom")
    Configuration fmConfiguration;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    private void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }
    
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date currentDttm = new Date(System.currentTimeMillis());
		String formattedDttm = sdf.format(currentDttm);
		return formattedDttm;
	}
	
	private String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date currentDttm = new Date(System.currentTimeMillis());
		String formattedDttm = sdf.format(currentDttm);
		return formattedDttm;
	}
	
    private String getContentFromTemplate(Map<String, Object> model, String templateName) {
        StringBuffer content = new StringBuffer();
 
        try {
            content.append(FreeMarkerTemplateUtils
                .processTemplateIntoString(fmConfiguration.getTemplate(templateName), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
	private void sendEmail(EmailTemplate mail, String templateName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(getContentFromTemplate(mail.getModel(),templateName));
            mimeMessageHelper.setText(mail.getMailContent(), true);
 
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public void sendConfirmationTokenEmail(User currentUser, String confirmationToken) throws MailAuthenticationException {
    	
    	EmailTemplate newMail = new EmailTemplate();
    	newMail.setMailFrom("antbuildz_bid@gmail.com");
    	newMail.setMailTo(currentUser.getEmail());
    	newMail.setMailSubject("[Antbuildz] Please Complete Your Registration!");
    	
    	Map <String, Object> model = new HashMap<>();
    	model.put("confirmationToken", confirmationToken);
    	model.put("actionUrl","http://localhost:8710/user/api/confirm-account/" + confirmationToken );
    	model.put("name", currentUser.getFullName());
    	model.put("username", currentUser.getUsername());
    	model.put("supportEmail", "info@antbuildz.com");
    	
    	newMail.setModel(model);
    	
    	sendEmail(newMail, "confirmationtoken.txt");
    }
    
    public void sendForgotPasswordEmail(String email, String confirmationToken) throws MailAuthenticationException {
    	SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("[Antbuildz] Password Reset Request");
        
        mailMessage.setFrom("antbuildz_bid@gmail.com");
        
        mailMessage.setText("Hi, \n You have received a request to reset your password at" + getCurrentTime() + " \n If this was requested by you, please follow the link below to reset your password. Otherwise, please ignore this email.. \n To reset your password, please click here : "
        + "http://localhost:8710/user/api/pwd-new/" + confirmationToken + 
        " /n Thank you, /n Team Antbuildz");
        
        sendEmail(mailMessage);
    }

	public void sendWinningBidEmail(TriggerWinningBidEmail triggerReq,String email) {
		
		EmailTemplate newMail = new EmailTemplate();
    	newMail.setMailFrom("antbuildz_bid@gmail.com");
    	newMail.setMailTo(email);
    	newMail.setMailSubject("[Antbuildz] Your bid is successful!");
    	
		Map <String, Object> model = new HashMap<>();
    	model.put("startDttm", triggerReq.getWiningRequest().getStartDatetime());
    	model.put("endDttm", triggerReq.getWiningRequest().getEndDatetime());
    	model.put("reqId", triggerReq.getWiningRequest().getRequester());
    	model.put("jobDttm", getCurrentTime());
    	model.put("bidAmount",triggerReq.getWinningBid().getBidPrice());
    	model.put("supportEmail", "info@antbuildz.com");
    	model.put("startLoc", triggerReq.getWiningRequest().getStartLocationPostalCode());
    	model.put("endLoc", triggerReq.getWiningRequest().getEndLocationPostalCode());
        
    	newMail.setModel(model);

    	sendEmail(newMail, "successfulbid.txt");
	}

	public void sendTopThreeEmail(String email, String reqId) {
		EmailTemplate newMail = new EmailTemplate();
    	newMail.setMailFrom("antbuildz_bid@gmail.com");
    	newMail.setMailTo(email);
    	newMail.setMailSubject("[Antbuildz] Bid ready for selection");
    	
    	Map <String, Object> model = new HashMap<>();
    	model.put("supportEmail", "info@antbuildz.com");
    	model.put("message", "Hello, \n Bidding for your request (" + reqId + ") has closed and the top 3 bids are now available for your decision.");
		
    	newMail.setModel(model);
    	
    	sendEmail(newMail, "checkbidemail.txt");
	}

	public String triggerInvoiceEmail(InvoiceTriggerEmailRequest invoiceTrigger, String email) {
		EmailTemplate newMail = new EmailTemplate();
    	newMail.setMailFrom("antbuildz_bid@gmail.com");
    	newMail.setMailTo(email);
    	newMail.setMailSubject("[Antbuildz] Job Completed!");
    	
    	Optional<User> searchRequester = userService.getUserById(invoiceTrigger.getCompletedJob().getRequestorUserId());
    	if(searchRequester.isEmpty()) {
    		return "Requester Email not found";
    	}
    	
    	User currentReqUser = searchRequester.get();
    	
    	Optional<User> partnerSearch = userService.getUserById(invoiceTrigger.getCompletedBid().getUserId());
    	if (partnerSearch.isEmpty()) {
    		return "Partner Email not found";
    	}
    	
    	User currentPartner = partnerSearch.get();
    	
    	Map <String, Object> model = new HashMap<>();
    	model.put("name", currentReqUser.getFullName());
    	model.put("jobId",invoiceTrigger.getCompletedJob().getJobNumber());
    	model.put("jobDttm", invoiceTrigger.getCompletedJob().getJobCreationTime());
    	model.put("total", invoiceTrigger.getCompletedBid().getBidPrice());
    	model.put("partnerName", currentPartner.getCompanyName());
    	model.put("partnerUEN", currentPartner.getUen());
    	model.put("startDttm", invoiceTrigger.getCompletedRequest().getStartDatetime());
    	model.put("endDttm", invoiceTrigger.getCompletedRequest().getEndDatetime());
    	model.put("date", getCurrentDate());
    	model.put("renterName", currentReqUser.getFullName());
    	model.put("reqId", invoiceTrigger.getCompletedRequest().getRequestNumber());
    	model.put("supportEmail", "info@antbuildz.com");
    	
    	
    	newMail.setModel(model);
    	
    	sendEmail(newMail, "invoice.txt");
		
		return "Job Closed, Email Sent";
	}
}
