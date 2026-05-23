package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Toolkit;
import com.example.demo.Entities.ToolkitIssue;
import com.example.demo.Entities.User;
import com.example.demo.enums.IssueStatus;
import com.example.demo.repository.ToolkitIssueRepo;
import com.example.demo.repository.ToolkitRepo;
import com.example.demo.repository.userRepo;
import com.example.demo.repository.StudentRepo;
import com.example.demo.enums.UserRole;

@Service
public class ToolkitIssueServices {

	@Autowired
	StudentRepo studentrepo;
	
	@Autowired
	ToolkitIssueRepo toolkitissuerepo;
	
	@Autowired
	userRepo userrepo;
	
	@Autowired
	ToolkitRepo toolkitrepo;

	@Autowired
	NotificationService notificationService;

	private void notifyAdminsAndLabAssistants(String message) {
		try {
			List<User> admins = userrepo.findByRole(UserRole.ADMIN);
			for (User u : admins) {
				notificationService.createNotification(u, message);
			}
			List<User> labAssistants = userrepo.findByRole(UserRole.LAB_ASSISTANT);
			for (User u : labAssistants) {
				notificationService.createNotification(u, message);
			}
		} catch (Exception e) {
			System.out.println("Error creating notification: " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> requestToolkit(Integer userId, Integer toolkitId){

	    User user = userrepo.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Toolkit toolkit = toolkitrepo.findById(toolkitId)
	            .orElseThrow(() -> new RuntimeException("Toolkit not found"));

	    ToolkitIssue issue = new ToolkitIssue();
	    issue.setUser(user);
	    issue.setToolkit(toolkit);
	    issue.setIssueDate(LocalDate.now());
	    issue.setStatus(IssueStatus.PENDING); // 🔥 Important

	    toolkitissuerepo.save(issue);

	    // 🔔 Notify
	    notificationService.createNotification(user, "You requested the toolkit: " + toolkit.getName() + ". Waiting for approval.");
	    notifyAdminsAndLabAssistants("Student " + user.getUserName() + " requested the toolkit: " + toolkit.getName() + ".");

	    return ResponseEntity.ok("Request sent for approval");
	}
	
	public ResponseEntity<?> approveRequest(Integer issueId){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Request not found"));

	    if(issue.getStatus()== IssueStatus.ISSUED){
	        return ResponseEntity.badRequest().body("Already issued");
	    }

	    Toolkit toolkit = issue.getToolkit();

	    if(toolkit.getAvailableQuantity() <= 0){
	        return ResponseEntity.badRequest().body("Toolkit not available");
	    }

	    // ✅ Approve
	    issue.setStatus(IssueStatus.ISSUED);

	    // ✅ Reduce quantity now
	    toolkit.setAvailableQuantity(toolkit.getAvailableQuantity() - 1);

	    toolkitrepo.save(toolkit);
	    toolkitissuerepo.save(issue);

	    // 🔔 Notify
	    notificationService.createNotification(issue.getUser(), "Your request for " + toolkit.getName() + " has been approved! Please collect it from the lab.");
	    notifyAdminsAndLabAssistants("Toolkit " + toolkit.getName() + " successfully issued to " + issue.getUser().getUserName() + ".");

	    return ResponseEntity.ok("Request approved & toolkit issued");
	}

	public ResponseEntity<?> rejectRequest(Integer issueId){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Request not found"));

	    issue.setStatus(IssueStatus.REJECTED);

	    toolkitissuerepo.save(issue);

	    // 🔔 Notify
	    notificationService.createNotification(issue.getUser(), "Your request for " + issue.getToolkit().getName() + " was rejected.");
	    notifyAdminsAndLabAssistants("Request by " + issue.getUser().getUserName() + " for " + issue.getToolkit().getName() + " has been rejected.");

	    return ResponseEntity.ok("Request rejected");
	}
	public ResponseEntity<?> returnToolkit(Integer issueId){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Issue not found"));

	    if(issue.getStatus() == IssueStatus.RETURNED){
	        return ResponseEntity.badRequest().body("Already returned");
	    }

	    issue.setStatus(IssueStatus.RETURNED);
	    issue.setReturnDate(LocalDate.now());

	    Toolkit toolkit = issue.getToolkit();
	    toolkit.setAvailableQuantity(toolkit.getAvailableQuantity() + 1);

	    toolkitrepo.save(toolkit);
	    toolkitissuerepo.save(issue);

	    return ResponseEntity.ok("Returned successfully");
	}
	public ResponseEntity<?> updateIssue(Integer issueId, ToolkitIssue updatedIssue){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Issue not found"));

	    // ✅ Allow update only if not issued yet
	    if(issue.getStatus() == IssueStatus.ISSUED){
	        return ResponseEntity.badRequest().body("Cannot edit after approval");
	    }

	    // ✅ Update fields (example)
	    if(updatedIssue.getStatus() != null){
	        issue.setStatus(updatedIssue.getStatus());
	    }

	    if(updatedIssue.getIssueDate() != null){
	        issue.setIssueDate(updatedIssue.getIssueDate());
	    }

	    toolkitissuerepo.save(issue);

	    return ResponseEntity.ok(issue);
	}
	public ResponseEntity<?> deleteIssue(Integer issueId){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Issue not found"));

	    // ✅ Prevent delete if already issued
	    if(issue.getStatus() == IssueStatus.ISSUED){
	        return ResponseEntity.badRequest().body("Cannot delete issued toolkit");
	    }

	    toolkitissuerepo.delete(issue);

	    return ResponseEntity.ok("Request deleted successfully");
	}
	
	public ResponseEntity<?> getAllRequests(){
		List<ToolkitIssue> ls = toolkitissuerepo.findAll();
		if(ls.isEmpty()) {
			return ResponseEntity.ok("No requests found..!");
		}
		return ResponseEntity.ok(ls);
	}
	
	public ResponseEntity<?> getAllRequestsById(Integer userId){

	    Optional<User> op = userrepo.findById(userId);

	    if(op.isEmpty()){
	        return ResponseEntity.status(404).body("User not found");
	    }

	    List<ToolkitIssue> ls = toolkitissuerepo.findByUserId(userId);

	    if(ls.isEmpty()) {
	        return ResponseEntity.ok("No requests found..!");
	    }

	    return ResponseEntity.ok(ls);
	}

	public ResponseEntity<?> requestReturn(Integer issueId) {
		ToolkitIssue issue = toolkitissuerepo.findById(issueId)
				.orElseThrow(() -> new RuntimeException("Issue not found"));

		if (issue.getStatus() != IssueStatus.ISSUED) {
			return ResponseEntity.badRequest().body("Only issued toolkits can be returned");
		}

		issue.setStatus(IssueStatus.RETURN_PENDING);
		toolkitissuerepo.save(issue);

		// 🔔 Notify
		notificationService.createNotification(issue.getUser(), "Return request generated for " + issue.getToolkit().getName() + ". Please physically return the kit to the lab.");
		notifyAdminsAndLabAssistants("Student " + issue.getUser().getUserName() + " has requested to return " + issue.getToolkit().getName() + ".");

		return ResponseEntity.ok("Return request generated successfully");
	}

	public ResponseEntity<?> approveReturn(Integer issueId, String notes) {
		ToolkitIssue issue = toolkitissuerepo.findById(issueId)
				.orElseThrow(() -> new RuntimeException("Issue not found"));

		if (issue.getStatus() != IssueStatus.RETURN_PENDING && issue.getStatus() != IssueStatus.ISSUED) {
			return ResponseEntity.badRequest().body("Toolkit return is not requested or issued");
		}

		issue.setStatus(IssueStatus.RETURNED);
		issue.setReturnDate(LocalDate.now());
		issue.setNotes(notes != null ? notes : "Perfect");

		Toolkit toolkit = issue.getToolkit();
		toolkit.setAvailableQuantity(toolkit.getAvailableQuantity() + 1);

		toolkitrepo.save(toolkit);
		toolkitissuerepo.save(issue);

		// 🔔 Notify
		notificationService.createNotification(issue.getUser(), "Your return for " + toolkit.getName() + " has been accepted. Condition: " + issue.getNotes() + ".");
		notifyAdminsAndLabAssistants("Toolkit " + toolkit.getName() + " return accepted from " + issue.getUser().getUserName() + ". Condition: " + issue.getNotes() + ".");

		return ResponseEntity.ok("Toolkit return approved successfully");
	}

	public ResponseEntity<?> getAnalytics() {
		Map<String, Object> data = new HashMap<>();

		long totalStudentsCount = studentrepo.count();
		List<ToolkitIssue> allIssues = toolkitissuerepo.findAll();

		long issuedKitsCount = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.ISSUED || i.getStatus() == IssueStatus.RETURN_PENDING)
				.count();

		long returnedKitsCount = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.RETURNED)
				.count();

		long pendingKitsCount = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.PENDING)
				.count();

		List<Toolkit> allKits = toolkitrepo.findAll();
		long remainingKitsCount = allKits.stream()
				.mapToLong(Toolkit::getAvailableQuantity)
				.sum();

		Map<String, Long> demandMap = allIssues.stream()
				.filter(i -> i.getToolkit() != null)
				.collect(Collectors.groupingBy(i -> i.getToolkit().getName(), Collectors.counting()));

		List<Map<String, Object>> mostDemandedList = new ArrayList<>();
		demandMap.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.forEach(e -> {
					Map<String, Object> item = new HashMap<>();
					item.put("name", e.getKey());
					item.put("count", e.getValue());
					mostDemandedList.add(item);
				});

		List<Map<String, Object>> damagedKits = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.RETURNED && i.getNotes() != null && !i.getNotes().trim().isEmpty() && !i.getNotes().equalsIgnoreCase("Perfect") && !i.getNotes().equalsIgnoreCase("None"))
				.map(i -> {
					Map<String, Object> item = new HashMap<>();
					item.put("id", i.getId());
					item.put("studentName", i.getUser() != null ? i.getUser().getUserName() : "Unknown");
					item.put("toolkitName", i.getToolkit() != null ? i.getToolkit().getName() : "Unknown");
					item.put("notes", i.getNotes());
					item.put("returnDate", i.getReturnDate() != null ? i.getReturnDate().toString() : "");
					return item;
				})
				.collect(Collectors.toList());

		List<Map<String, Object>> activeDetails = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.ISSUED || i.getStatus() == IssueStatus.RETURN_PENDING)
				.map(i -> {
					Map<String, Object> item = new HashMap<>();
					item.put("id", i.getId());
					item.put("studentName", i.getUser() != null ? i.getUser().getUserName() : "Unknown");
					item.put("email", i.getUser() != null ? i.getUser().getEmail() : "");
					item.put("phone", i.getUser() != null ? i.getUser().getPhone() : "");
					item.put("toolkitName", i.getToolkit() != null ? i.getToolkit().getName() : "Unknown");
					item.put("toolkitType", i.getToolkit() != null ? i.getToolkit().getType() : "");
					item.put("issueDate", i.getIssueDate() != null ? i.getIssueDate().toString() : "");
					item.put("status", i.getStatus().toString());
					return item;
				})
				.collect(Collectors.toList());

		List<Map<String, Object>> returnedDetails = allIssues.stream()
				.filter(i -> i.getStatus() == IssueStatus.RETURNED)
				.map(i -> {
					Map<String, Object> item = new HashMap<>();
					item.put("id", i.getId());
					item.put("studentName", i.getUser() != null ? i.getUser().getUserName() : "Unknown");
					item.put("toolkitName", i.getToolkit() != null ? i.getToolkit().getName() : "Unknown");
					item.put("issueDate", i.getIssueDate() != null ? i.getIssueDate().toString() : "");
					item.put("returnDate", i.getReturnDate() != null ? i.getReturnDate().toString() : "");
					item.put("notes", i.getNotes());
					return item;
				})
				.collect(Collectors.toList());

		data.put("totalStudents", totalStudentsCount);
		data.put("issuedKits", issuedKitsCount);
		data.put("returnedKits", returnedKitsCount);
		data.put("pendingKits", pendingKitsCount);
		data.put("remainingKits", remainingKitsCount);
		data.put("mostDemanded", mostDemandedList);
		data.put("damagedKits", damagedKits);
		data.put("activeDetails", activeDetails);
		data.put("returnedDetails", returnedDetails);

		return ResponseEntity.ok(data);
	}

	public String generateReportCsv() {
		StringBuilder sb = new StringBuilder();
		sb.append("Issue ID,Student Name,Email,Phone,Toolkit Name,Toolkit Type,Issue Date,Return Date,Status,Damage Notes\n");
		List<ToolkitIssue> allIssues = toolkitissuerepo.findAll();
		for (ToolkitIssue i : allIssues) {
			String studentName = i.getUser() != null ? i.getUser().getUserName() : "Unknown";
			String email = i.getUser() != null ? i.getUser().getEmail() : "";
			String phone = i.getUser() != null ? i.getUser().getPhone() : "";
			String kitName = i.getToolkit() != null ? i.getToolkit().getName() : "Unknown";
			String kitType = i.getToolkit() != null ? i.getToolkit().getType() : "";
			
			sb.append(i.getId()).append(",")
			  .append(escapeCsv(studentName)).append(",")
			  .append(escapeCsv(email)).append(",")
			  .append(escapeCsv(phone)).append(",")
			  .append(escapeCsv(kitName)).append(",")
			  .append(escapeCsv(kitType)).append(",")
			  .append(i.getIssueDate() != null ? i.getIssueDate().toString() : "").append(",")
			  .append(i.getReturnDate() != null ? i.getReturnDate().toString() : "").append(",")
			  .append(i.getStatus().toString()).append(",")
			  .append(escapeCsv(i.getNotes() != null ? i.getNotes() : "")).append("\n");
		}
		return sb.toString();
	}

	private String escapeCsv(String value) {
		if (value == null) return "";
		return "\"" + value.replace("\"", "\"\"") + "\"";
	}
}
