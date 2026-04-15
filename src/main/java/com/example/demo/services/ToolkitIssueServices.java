package com.example.demo.services;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

@Service
public class ToolkitIssueServices {
	
	@Autowired
	ToolkitIssueRepo toolkitissuerepo;
	
	@Autowired
	userRepo userrepo;
	
	@Autowired
	ToolkitRepo toolkitrepo;
	
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

	    return ResponseEntity.ok("Request approved & toolkit issued");
	}

	public ResponseEntity<?> rejectRequest(Integer issueId){

	    ToolkitIssue issue = toolkitissuerepo.findById(issueId)
	            .orElseThrow(() -> new RuntimeException("Request not found"));

	    issue.setStatus(IssueStatus.REJECTED);

	    toolkitissuerepo.save(issue);

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
}
