package com.zee.zee5app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zee.zee5app.Exception.AlreadyExistsException;
import com.zee.zee5app.Exception.IdNotFoundException;
import com.zee.zee5app.Exception.InvalidIdLengthException;
import com.zee.zee5app.Exception.InvalidNameException;
import com.zee.zee5app.dto.Register;
import com.zee.zee5app.service.UserService;

@RestController  //version 4,, combo of @ResponseBody and @Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody Register register) throws AlreadyExistsException {   // ResponseEntity<?> ,,? :- anytype
		
			Register result = userService.addUser(register);
			return ResponseEntity.status(201).body(result); //201:new record created
			
//		} catch (AlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			Map<String, String>hashMap = new HashMap<>();
//			hashMap.put("message", "record already exists");
//			return ResponseEntity.badRequest().body(hashMap);
//			//e.printStackTrace();
//		}
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") String id) throws IdNotFoundException{
		Register register = userService.getUserById(id);
		return ResponseEntity.ok(register);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails() throws InvalidIdLengthException, InvalidNameException{
		Optional<List<Register>> optional = userService.getAllUserDetails();
		
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			map.put("message : ", "user record not found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.ok(optional.get());
	}
	
}
