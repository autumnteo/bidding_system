package com.services.user.api.services;

import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.services.user.api.dao.ConfirmationTokenDAO;
import com.services.user.api.dao.UserDAO;
import com.services.user.api.model.ConfirmationToken;
import com.services.user.api.model.InvoiceTriggerEmailRequest;
import com.services.user.api.model.PartnerDashboardView;
import com.services.user.api.model.TriggerWinningBidEmail;
import com.services.user.api.model.User;
import com.services.user.api.model.updateUserDetailsForm;
import com.services.user.api.util.EmailService;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ConfirmationTokenDAO confirmationTokenDAO;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService() {
		super();
	}
	
	//helper methods
	public Optional<User> getUserById(long userId){
		return userDAO.findById(userId);
	}
	
	//ADMIN ONLY - change user type
	public String changePartnerUserType(String userId) {
		Optional<User> userSearch = userDAO.findById(Long.parseLong(userId));
		String userType = "ROLE_PARTNER";
		if (!userSearch.isPresent()) {
			return "User not found";
		} else {
			User existingUser = userSearch.get();
			String[] existingRoles = existingUser.getUserType().split(",");
			String newRoleString = "";
			if ( ArrayUtils.contains(existingRoles, userType) && existingRoles.length != 1 ) {
				for (int i = 0 ; i < existingRoles.length ; i++ ) {
					if (!existingRoles[i].equals(userType)) {
						if(newRoleString.length() == 0) {
							newRoleString = newRoleString.concat(existingRoles[i]);
						} else {
							newRoleString = newRoleString.concat("," + existingRoles[i]);
						}
					}
				}
				existingUser.setRequestingPartner(false);
			} else if (ArrayUtils.contains(existingRoles, userType) && existingRoles.length == 1) {
				return "Unable to remove role from user";
			} else {
				newRoleString = existingUser.getUserType() + "," + userType;
				existingUser.setRequestingPartner(false);
			}
			existingUser.setUserType(newRoleString);
			userDAO.save(existingUser);
			return "User role successfully updated";
		}
	}
	
	//ADMIN ONLY - Change any user's password
	public String changeUserPwd(String username, String password) {
		Optional<User> existingUser = userDAO.findByUsernameIgnoreCase(username);
		if (existingUser.isPresent()) {
			return "User not found";
		} else {
			try {
				User changeUser = existingUser.get();
				changeUser.setPassword(bCryptPasswordEncoder.encode(password));
				userDAO.save(changeUser);
				return "Password successfully changed";
			} catch (Exception e) {
				return "Unable to reset password, please try again later";
			}
		}
	}
	
	// used by user to request change to partner
	public String partnerRequestByuser(String userId) {
		Optional<User> existingUser = userDAO.findById(Long.parseLong(userId));
		if(!existingUser.isPresent()) {
			return "User not found";
		} else {
			User requestingUser = existingUser.get();
			requestingUser.setRequestingPartner(true);
			userDAO.save(requestingUser);
		}
		return "Request to become a partner saved";
	}
	
	//resends email to the user, need to define if its the activation email or the password email
	public String resendEmailToUser(String username, String tokenType) {
		Optional<User> existingUser = userDAO.findByUsernameIgnoreCase(username);
		if (existingUser.isPresent()) {
			return "User not found";
		} else {
			long targetUserId = existingUser.get().getUserId();
			Optional<ConfirmationToken> tokenTarget = confirmationTokenDAO.findConfirmationTokenByUserIdTokenType(tokenType,String.valueOf(targetUserId));
			if (!tokenTarget.isPresent()) {
				return "Confirmation token cannot be found";
			} else {
				if (tokenType.equals("R")) {
					emailService.sendConfirmationTokenEmail(tokenTarget.get().getUser(),tokenTarget.get().getConfirmationToken());
				} else if (tokenType.equals("P")) {
					emailService.sendForgotPasswordEmail(tokenTarget.get().getUser().getEmail(),tokenTarget.get().getConfirmationToken());
				}
				return "Email successfully sent";
			}
		}
	}
	
	//username must be unique
	public String createNewUser(User registrationUser) {
		Optional<User> existingUser = userDAO.findByUsernameIgnoreCase(registrationUser.getUsername());
		Optional<User> existingUserToo = userDAO.findByEmailIgnoreCase(registrationUser.getEmail());
		if(existingUser.isPresent()) {
			return "Username already in use";
		} else if (existingUserToo.isPresent()) {
			return "Email already in use";
		}
		else {
			try {
				userDAO.save(registrationUser);

	            ConfirmationToken confirmationToken = new ConfirmationToken(registrationUser,"R");
	            confirmationTokenDAO.save(confirmationToken);
	            
	            try {
	            	
	            	emailService.sendConfirmationTokenEmail(registrationUser,confirmationToken.getConfirmationToken());
	            	
	            } catch (MailAuthenticationException e) {
	            	// error in sending email, deleting existing entities already created in the database
	            	confirmationTokenDAO.delete(confirmationToken);
	            	userDAO.delete(registrationUser);
	            	return "Error in generating confirmation email, please attempt registration again later";
	            }
	            
	            return "Confirmation email sent, please check your inbox!";
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return "Error in registration, please try again later!";
			}
			
		}
	}
	
	//username cannot be changed, password cannot be changed through this form
	public String updateUserDetails(updateUserDetailsForm updateUser) {
		Optional<User> dbUser = userDAO.findByUsernameIgnoreCase(updateUser.getUsername());
		if (dbUser.isPresent()) {
			return "User not found";
		} else {
			User existingUser = dbUser.get();
			if (!updateUser.getFullName().equals(existingUser.getFullName())){
				existingUser.setFullName(updateUser.getFullName());
			}
			if (!updateUser.getAddress().equals(existingUser.getAddress())){
				existingUser.setAddress(updateUser.getAddress());
			}
			if (!updateUser.getCompanyName().equals(existingUser.getCompanyName())){
				existingUser.setCompanyName(updateUser.getCompanyName());
			}
			if (!updateUser.getEmail().equals(existingUser.getEmail())){
				existingUser.setEmail(updateUser.getEmail());
			}
			if (!updateUser.getPhoneNumber().equals(existingUser.getPhoneNumber())){
				existingUser.setPhoneNumber(updateUser.getPhoneNumber());
			}
			if (!updateUser.getPosition().equals(existingUser.getPosition())){
				existingUser.setPosition(updateUser.getPosition());
			}
			if (!updateUser.getUen().equals(existingUser.getUen())){
				existingUser.setUen(updateUser.getUen());
			}
			userDAO.save(existingUser);
			return "User details updated";
		}
	}
	
	public String dbUser(String username, String password) {
		Optional<User> dbUser = userDAO.findByUsernameIgnoreCase(username);
		if (!dbUser.isPresent()) {
			return "User not found";
		} else {
			try {
				User existingUser = dbUser.get();
				existingUser.setPassword(bCryptPasswordEncoder.encode(password));
				userDAO.save(existingUser);
				return "Password successfully updated";
			} catch (Exception e) {
				return "Error in changing password, please try again later";
			}
		}
	}
	
	public String forgetPasswordRequest(String username) {
		Optional<User> dbUser = userDAO.findByUsernameIgnoreCase(username);
		if (!dbUser.isPresent()) {
			return "User not found";
		} else {
			User existingUser = dbUser.get();
			
			// check if a token for forgotten password already exists
			Optional<ConfirmationToken> existingToken = confirmationTokenDAO.findConfirmationTokenByUserIdTokenType(username, "P");
			
			ConfirmationToken confirmationToken = null;
			
			if (!existingToken.isPresent()) {
				// creating and saving a new confirmation token
	            confirmationToken = new ConfirmationToken(existingUser,"P");
	            confirmationTokenDAO.save(confirmationToken);
			} else {
				confirmationToken = existingToken.get();
			}
            
            try {
            	//trigger email send to user
            	emailService.sendForgotPasswordEmail(existingUser.getEmail(), confirmationToken.getConfirmationToken());
            	
            	//remove old token if present
            	if (existingToken.isPresent()) {
            		confirmationTokenDAO.delete(existingToken.get());
            	}
            	return "Password reset email sent, please check your inbox";
            } catch (Exception e) {
            	//rollback confirmation token
            	confirmationTokenDAO.delete(confirmationToken);
            	return "Unable to reset password, please try again later.";
            }
		}
	}
	
	public String forgetPasswordReset(String username, String confirmationToken, String password) {
		Optional<User> searchUser = userDAO.findByUsernameIgnoreCase(username);
		if (!searchUser.isPresent()) {
			return "User not found";
		} else {
			try {
				Optional<ConfirmationToken> presentToken = confirmationTokenDAO.findbyConfirmationToken(confirmationToken);
				if (presentToken.isPresent()) {
					User existingUser = searchUser.get();
					existingUser.setPassword(bCryptPasswordEncoder.encode(password));
					userDAO.save(existingUser);
					confirmationTokenDAO.delete(presentToken.get());
					return "Password successfully changed";
				}
				return "Unable to verify token";
			} catch (Exception e) {
				return "Unable to reset password, please try again later";
			}
		}
	}
	
	public String activateAccount(String token) {
		Optional<ConfirmationToken> tokenCheck = confirmationTokenDAO.findbyConfirmationToken(token);
		if (!(tokenCheck.isPresent())) {
			return String.format("Confirmation token (%s) not found, please check your token.", token);
		}
		else {
			ConfirmationToken tokenRecord = tokenCheck.get();
			if (tokenRecord.getUser().isEnabled()) {
				confirmationTokenDAO.delete(tokenRecord);
				return String.format("Confirmation token (%s) already activated.", token);
			} else {
				User currentUser = tokenRecord.getUser();
				currentUser.setEnabled(true);
				try {
					userDAO.save(currentUser);
					confirmationTokenDAO.delete(tokenRecord);
				} catch (Exception e) {
					return "Error activating token, please try again later";
				}
				return "Account activated!";
			}
		}
	}

	public String sendWinningBidEmail(TriggerWinningBidEmail triggerReq) {
		Optional<User> winingBidder = userDAO.findById(triggerReq.getWinningBid().getUserId());
		if (winingBidder.isPresent()) {
			User sendEmailUser = winingBidder.get();
			emailService.sendWinningBidEmail(triggerReq,sendEmailUser.getEmail());
		} else {
			return "User cannot be found";
		}
		return "Email has been sent to sucessful bidder";
	}

	public String sendTopThreeEmail(String userId, String reqId) {
		Optional<User> requestOwner = userDAO.findById(Long.parseLong(userId));
		if (requestOwner.isPresent()) {
			User sendEmailUser = requestOwner.get();
			emailService.sendTopThreeEmail(sendEmailUser.getEmail(),reqId);
		} else {
			return "User cannot be found";
		}
		return "Email Sent for Request: " + reqId;
		
	}

	public PartnerDashboardView viewPartnerDashboard() {
		PartnerDashboardView responseView = new PartnerDashboardView();
		responseView.setCurrentPartners(userDAO.getCurrentPartners());
		responseView.setRequestingPartners(userDAO.getRequestingPartners());
		return responseView;
	}

	public String triggerInvoiceEmail(InvoiceTriggerEmailRequest invoiceTrigger) {
		Optional<User> partnerSearch = userDAO.findById(Long.parseLong(invoiceTrigger.getUserId()));
		if (partnerSearch.isEmpty()) {
			return "User not found";
		}
		return emailService.triggerInvoiceEmail(invoiceTrigger, partnerSearch.get().getEmail());
	}

	public boolean verifyPartnerRequest(String userId) {
		Optional<User> partnerSearch = userDAO.findById(Long.parseLong(userId));
		if (partnerSearch.isEmpty()) {
			return false;
		}
		User partnerCheckUser = partnerSearch.get();
		if (partnerCheckUser.isRequestingPartner()) {
			return true;
		}
		return false;
	}

}
