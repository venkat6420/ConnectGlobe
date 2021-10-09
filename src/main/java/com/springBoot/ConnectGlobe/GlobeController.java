package com.springBoot.ConnectGlobe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class GlobeController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	GlobeService userDetailsService;
	
	@Autowired
	jwtUtil jwtTokenUtil;
	
	@Autowired
	GlobeService service;
	
	
	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping("/")
	public String home() {
		return "Login";
	}
	@GetMapping("/register")
	public String registerrr(Model m) {
		m.addAttribute("register",new UserModel());
		return "Registration";
	}
	@PostMapping("/save")
	public String saveToRegister(@ModelAttribute("register") UserModel m) {
		List<UserModel> l=new ArrayList<>();
//		String fullname=request.getParameter("username");
//		String email=request.getParameter("email");
//		String password=request.getParameter("")
//		String username=model.getEmail();
//		String password=model.getPassword();
//		String roles=model.getRoles();
		String username=m.getEmail();
		String password=m.getPassword();
		String roles=m.getRoles();
		CredentialModel data=new CredentialModel(username,password,roles);
		UserModel u=service.saveToUser(m);
		CredentialModel c=service.saveToCredential(data);
		return "Login";
	}
	@PostMapping(value = "/login", produces = "application/json")
	public ModelAndView createAuthenticationToken(@RequestParam("email") String email,@RequestParam("psw") String password,HttpSession session) throws Exception{
		System.out.println("authenticated");
		AuthenticationRequest a=new AuthenticationRequest(email,password);
		System.out.println(a.getUsername());
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(a.getUsername(),a.getPassword())
				);
		}catch(BadCredentialsException e) {
//			return "Invalid username or password";
			throw new Exception("Incorrect Username or passowrd");
		}
		System.out.println("done...");
		final UserDetails userDetails=userDetailsService.loadUserByUsername(a.getUsername());
		final CredentialModel model=userDetailsService.findByUsername(a.getUsername());
		System.out.println(model.getRoles());
		if(model.getRoles().equals("ROLE_USER")) {
			System.out.println("Hello patient");
			final UserModel p=userDetailsService.findByEmail(a.getUsername());
			ModelAndView mod = new ModelAndView("/Home");
			mod.addObject("userDetails",p);
			int id=p.getUserId();
			String uId=String.valueOf(id);
			session.setAttribute("userId", uId);
			List<imageEntityClass> l=service.getAllPosts();
			System.out.println(l);
			mod.addObject("AllPosts",l);
			final String jwt=jwtTokenUtil.generateToken(userDetails);
			return mod;
		}else {
			System.out.println("Hello admin");
			final UserModel p=userDetailsService.findByEmail(a.getUsername());
			final String jwt=jwtTokenUtil.generateToken(userDetails);
			ModelAndView mo=new ModelAndView("#");
			mo.addObject(p);
			return mo;
		}
	}
	@GetMapping("/MyPosts/{id}")
	public ModelAndView getMyPosts(@PathVariable("id") String id) throws UnsupportedEncodingException {
		System.out.println("Hello"+id);
		int Uid=Integer.parseInt(id);
		List<imageEntityClass> l =service.getMyPosts(Uid);
		ModelAndView mi=new ModelAndView("/Posts");
		mi.addObject("MyPosts", l);
		mi.addObject("userId", Uid);
		return mi;
	}
	
	@PostMapping("/MyPosts/upload")
	public ModelAndView uploadPost(@RequestParam("image") MultipartFile p,@RequestParam("tag") String tag,@RequestParam("userId") int id) throws IOException {
		System.out.println("Upload method");
		byte[] i=p.getBytes();
		uploadEntity u=new uploadEntity(id,tag,i);
		uploadEntity t=service.saveToMyPosts(u);
		int Uid=t.getUserId();
		List<imageEntityClass> l=service.getMyPosts(Uid);
		ModelAndView mk=new ModelAndView("/Posts");
		mk.addObject("MyPosts", l);
		mk.addObject("userId",Uid);
		System.out.println("file uploaded succes");
		return mk;
	}
	
}
