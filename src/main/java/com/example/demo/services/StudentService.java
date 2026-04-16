package com.example.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.Student;
import com.example.demo.repository.StudentRepo;
import com.example.demo.repository.userRepo;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private userRepo userrepo;

    public ResponseEntity<?> updateStudent(Integer id, Student updatedData) {
    	
        try {
            Student existingStudent = studentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            if (updatedData.getUserName() != null)
                existingStudent.setUserName(updatedData.getUserName());

            if (updatedData.getPassword() != null)
                existingStudent.setPassword(updatedData.getPassword());

            if (updatedData.getEmail() != null)
                existingStudent.setEmail(updatedData.getEmail());

            if (updatedData.getPhone() != null)
                existingStudent.setPhone(updatedData.getPhone());

            if (updatedData.getBranch() != null)
                existingStudent.setBranch(updatedData.getBranch());

            if (updatedData.getDivision() != null)
                existingStudent.setDivision(updatedData.getDivision());

            if (updatedData.getPrn() != null)
                existingStudent.setPrn(updatedData.getPrn());

            if (updatedData.getYear() != 0)
                existingStudent.setYear(updatedData.getYear());

            Student saved = studentRepo.save(existingStudent);

            return new ResponseEntity<>(saved, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> deleteStudent(Integer id) {

        try {
            Student student = studentRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            studentRepo.delete(student);

            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> getStudentById(Integer id){
    	Optional<Student> op = studentRepo.findById(id);
    	if(op.isEmpty())return ResponseEntity.status(500).body("not found");
    	return ResponseEntity.ok(op.get());
    }
}

