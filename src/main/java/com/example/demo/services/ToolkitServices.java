package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Toolkit;
import com.example.demo.repository.ToolkitRepo;

@Service
public class ToolkitServices {
	
	@Autowired
	ToolkitRepo toolkitrepo;
	
	public ResponseEntity<?> addToolkit(Toolkit t){
		Optional<Toolkit> op = toolkitrepo.findByQrCode(t.getQrCode());
		if(op.isPresent()) {
			return ResponseEntity.status(500).body("Already present");
		}
		Toolkit addedTool = toolkitrepo.save(t);
		return ResponseEntity.status(201).body(addedTool);
	}
	public ResponseEntity<?> getAllToolkitById(Integer id){
		Optional<Toolkit> op = toolkitrepo.findById(id);
		if(op.isEmpty()) {
			return ResponseEntity.ok("no toolkits r present");
		}
		return ResponseEntity.ok(op.get());
	}
	public ResponseEntity<?> getAllToolkit(){
		List<Toolkit> allkits = toolkitrepo.findAll();
		if(allkits.isEmpty()) {
			return ResponseEntity.ok("no toolkits r present");
		}
		return ResponseEntity.ok(allkits);
	}
	
	public ResponseEntity<?> updateToolkit(Toolkit updatedToolkit, Integer id){
		Optional<Toolkit> op = toolkitrepo.findById(id);
		if(!op.isPresent()) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}
		Toolkit existingToolkit = op.get();
		 // ✅ Update fields
	    existingToolkit.setName(updatedToolkit.getName());
	    existingToolkit.setType(updatedToolkit.getType());
	    existingToolkit.setDescription(updatedToolkit.getDescription());
	    existingToolkit.setTotalQuantity(updatedToolkit.getTotalQuantity());
	    existingToolkit.setAvailableQuantity(updatedToolkit.getAvailableQuantity());
	    existingToolkit.setStatus(updatedToolkit.getStatus());
	    
	    
	    return ResponseEntity.ok(toolkitrepo.save(existingToolkit));
	}
	
	public ResponseEntity<?> deleteToolKit(Integer id){
		Optional<Toolkit> op = toolkitrepo.findById(id);
		if(!op.isPresent()) {
			return (ResponseEntity<?>)ResponseEntity.notFound();
		}
		Toolkit t = op.get();
		toolkitrepo.deleteById(id);
		return ResponseEntity.ok("Delete successs");
	}
}
