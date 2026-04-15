package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.ToolkitIssue;
import com.example.demo.services.ToolkitIssueServices;
@RestController
@RequestMapping("/api/toolkitissue")
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
}