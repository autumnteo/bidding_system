
package com.services.user.api.controller;

import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.user.api.dao.ConfirmationTokenDAO;
import com.services.user.api.dao.UserDAO;
import com.services.user.api.model.InvoiceTriggerEmailRequest;
import com.services.user.api.model.PartnerDashboardView;
import com.services.user.api.model.PasswordChangeReq;
import com.services.user.api.model.TriggerWinningBidEmail;
import com.services.user.api.model.User;
import com.services.user.api.model.createUserForm;
import com.services.user.api.services.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ConfirmationTokenDAO confirmationTokenDAO;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@ApiOperation(value="API Healthcheck")
	@GetMapping
	public boolean apiHealth() {
		return true;
	}
	
	@ApiOperation(value="[ADMIN] Get all registered users in the system")
	@GetMapping("/admin/get-all")
	public List<User> getAllUsers(){
		return (List<User>) userDAO.findAll(); 
	}
	
	@ApiOperation(value="[ADMIN] Change any user's password")
	@GetMapping("/admin/user/change-pwd")
	public String changeUserPwd(PasswordChangeReq pwdReq) {
		return userService.changeUserPwd(pwdReq.getUsername(), pwdReq.getPassword());
	}
	
	@ApiOperation(value="[ADMIN] View partner requests")
	@GetMapping("/admin/view/partnerDashboard")
	public PartnerDashboardView viewPartnerDashboard() {
		return userService.viewPartnerDashboard();
	}
	
	@ApiOperation(value="[ADMIN] Toggle User Partner Type")
	@GetMapping("/admin/togglePartner/{userId}")
	public String changeUserType(@PathVariable String userId) {
		return userService.changePartnerUserType(userId);
	}
	
	@ApiOperation(value="[User] Request to be partner")
	@GetMapping("/requestPartner/{userId}")
	public String partnerRequestByuser(@PathVariable String userId) {
		return userService.partnerRequestByuser(userId);
	}
	
	@ApiOperation(value="[User] Verify partner request")
	@GetMapping("/verifyPartnerRequest/{userId}")
	public boolean verifyPartnerRequest(@PathVariable String userId) {
		return userService.verifyPartnerRequest(userId);
	}
	
	
	@ApiOperation(value="[GATEWAY] Returns a user model for authentication")
	@GetMapping("/login/{username}")
	public User userLogin(@PathVariable String username) {
		Optional<User> existingUser = userDAO.findByUsernameIgnoreCase(Jsoup.clean(username, Whitelist.basic()));
		if (existingUser.isPresent()) {
			return existingUser.get();
		} else {
			return new User();
		}
	}
	
	@ApiOperation(value = "[PROCESSING] Triggers the email to the winning bidder")
	@PostMapping("/triggerEmail/winningBid")
	public void sendWinningBidEmail(@RequestBody TriggerWinningBidEmail triggerReq) {
		userService.sendWinningBidEmail(triggerReq);
	}
	
	@ApiOperation(value = "[PROCESSING] Triggers the email to the requester bidder to select the bid")
	@PostMapping("/triggerEmail/topThreeBid/{userId}/{reqId}")
	public String sendTopThreeEmail(@PathVariable String userId, @PathVariable String reqId) {
		return userService.sendTopThreeEmail(userId,reqId);
	}
	
	@ApiOperation(value = "[PROCESSING] Triggers the email to the user that the job is completed")
	@PostMapping("/triggerEmail/invoiceEmail")
	public String triggerInvoiceEmail(@RequestBody InvoiceTriggerEmailRequest invoiceTrigger) {
		return userService.triggerInvoiceEmail(invoiceTrigger);
	}
	
	@ApiOperation(value="User endpoint for authenticating confirmation token")
	@GetMapping("/confirm-account/{token}")
	public String validateToken(@PathVariable String token) {
		return userService.activateAccount(token);
	}
	
	@ApiOperation(value="Creates a new user")
	@PostMapping("/create")
	public String createNewUser(@RequestBody createUserForm userReq) {
		//map to new object with XSS validation
		User newUser = new User();
		newUser.setUsername(Jsoup.clean(userReq.getUsername(), Whitelist.basic()));
		newUser.setEmail(Jsoup.clean(userReq.getEmail(), Whitelist.basic()));
		newUser.setFullName(Jsoup.clean(userReq.getFullName(), Whitelist.basic()));
		newUser.setAddress(Jsoup.clean(userReq.getAddress(), Whitelist.basic()));
		newUser.setCompanyName(Jsoup.clean(userReq.getCompanyName(), Whitelist.basic()));
		newUser.setPassword(bCryptPasswordEncoder.encode(Jsoup.clean(userReq.getPassword(), Whitelist.basic())));
		newUser.setPhoneNumber(Jsoup.clean(userReq.getPhoneNumber(), Whitelist.basic()));
		newUser.setUen(Jsoup.clean(userReq.getUen(), Whitelist.basic()));
		newUser.setPosition(Jsoup.clean(userReq.getPosition(), Whitelist.basic()));
		newUser.setEnabled(false);
		newUser.setUserType("ROLE_USER");
	
		return userService.createNewUser(newUser);
		}
	
	@ApiOperation(value="Request for a password reset")
	@GetMapping("/password-reset/request/{username}")
	public String passwordResetRequest(@PathVariable String username) {
		return userService.forgetPasswordRequest(username);
	}
	
	@ApiOperation(value="Change a forgotten password with required confirmation token")
	@PostMapping("/api/pwd-new/{confirmationToken}")
	public String forgetPasswordReset(@PathVariable String confirmationToken, @RequestBody PasswordChangeReq passwordReq) {
		return userService.forgetPasswordReset(Jsoup.clean(passwordReq.getUsername(), Whitelist.basic()),
				confirmationToken ,Jsoup.clean(passwordReq.getPassword(), Whitelist.basic()));
	}
	
	

}
