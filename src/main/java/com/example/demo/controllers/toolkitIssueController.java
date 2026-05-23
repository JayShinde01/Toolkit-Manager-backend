package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.ToolkitIssue;
import com.example.demo.services.ToolkitIssueServices;
@RestController
@RequestMapping("/api/toolkitissue")
@CrossOrigin("*")
public class toolkitIssueController {

    @Autowired
    ToolkitIssueServices toolkitissueservice;

    // ✅ Request Toolkit
    @PostMapping("/request/{userId}/{toolkitId}")
    public ResponseEntity<?> requestToolkit(@PathVariable Integer userId,
                                            @PathVariable Integer toolkitId){
        return toolkitissueservice.requestToolkit(userId, toolkitId);
    }

    // ✅ Approve Request
    @PutMapping("/approve/{issueId}")
    public ResponseEntity<?> approveRequest(@PathVariable Integer issueId){
        return toolkitissueservice.approveRequest(issueId);
    }

    // ✅ Update Issue
    @PutMapping("/update/{issueId}")
    public ResponseEntity<?> updateIssue(@PathVariable Integer issueId,
                                         @RequestBody ToolkitIssue updatedIssue ){
        return toolkitissueservice.updateIssue(issueId, updatedIssue);
    }

    // ✅ Delete Issue
    @DeleteMapping("/delete/{issueId}")
    public ResponseEntity<?> deleteIssue(@PathVariable Integer issueId){
        return toolkitissueservice.deleteIssue(issueId);
    }
    
    @GetMapping("/")
    public ResponseEntity<?>getAllRequests(){
    	return toolkitissueservice.getAllRequests();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?>getAllRequestsById(@PathVariable Integer userId){
    	return toolkitissueservice.getAllRequestsById(userId);
    }

    // ↩ Request Return
    @PutMapping("/return-request/{issueId}")
    public ResponseEntity<?> requestReturn(@PathVariable Integer issueId) {
        return toolkitissueservice.requestReturn(issueId);
    }

    // ↩ Approve Return
    @PutMapping("/return-approve/{issueId}")
    public ResponseEntity<?> approveReturn(@PathVariable Integer issueId,
                                           @RequestParam(required = false) String notes) {
        return toolkitissueservice.approveReturn(issueId, notes);
    }

    // 📊 Analytics
    @GetMapping("/analytics")
    public ResponseEntity<?> getAnalytics() {
        return toolkitissueservice.getAnalytics();
    }

    // 📄 Download CSV Report
    @GetMapping("/report/csv")
    public ResponseEntity<?> downloadReportCsv() {
        String csvContent = toolkitissueservice.generateReportCsv();
        byte[] csvBytes = csvContent.getBytes();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=toolkit_report.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }
}