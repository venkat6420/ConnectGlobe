package com.springBoot.ConnectGlobe;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GlobeController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	GlobeService userDetailsService;

	
	@Autowired
	GlobeService service;
	
	@Autowired
	UserRepo repo;
	
	@Autowired
	AuthenticationManager authenticationManager;

	ModelAndView mavr = new ModelAndView("/Registration");
	ModelAndView mavl = new ModelAndView("/Login");
	ModelAndView modh = new ModelAndView("/Home");
	ModelAndView moda = new ModelAndView("/admin");
	ModelAndView modp=new ModelAndView("/Posts");
	ModelAndView modr = new ModelAndView("/Reports");
	ModelAndView modmr = new ModelAndView("/MyReports");
	ModelAndView modc=new ModelAndView("/comment");
	ModelAndView mods=new ModelAndView("/suggestions");
	ModelAndView modpr= new ModelAndView("/profile");
	private int qids;
	@GetMapping("/")
	public String home(HttpSession session) {

		session.setAttribute("userId","");
		return "Login";
	}
	@GetMapping("/register")
	public String registerrr(Model m) {
		m.addAttribute("register",new UserModel());
		return "Registration";
	}
	@PostMapping("/save")
	public ModelAndView saveToRegister(@ModelAttribute("register") UserModel m) {

		String username=m.getEmail();
		String password=m.getPassword();
		String roles=m.getRoles();
		if(service.checkemailexists(m.getEmail()))
		{
			//ModelAndView mav = new ModelAndView("/Registration");
			mavr.addObject("exits", "Email I'd Already Exists");
			return mavr;
		}
		CredentialModel data=new CredentialModel(username,password,roles);
		service.saveToUser(m);
		service.saveToCredential(data);
		//ModelAndView mav = new ModelAndView("/Registration");
		mavr.addObject("success", "SuccesFully Registered and Click Login Button");
		return mavr;
	}
	@PostMapping(value = "/login", produces = "application/json")
	public ModelAndView createAuthenticationToken(@RequestParam("email") String email,@RequestParam("psw") String password,HttpSession session,HttpServletRequest request) throws Exception{
		AuthenticationRequest a=new AuthenticationRequest(email,password);
		try {
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(a.getUsername(),a.getPassword())
				);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch(BadCredentialsException e) {
			//ModelAndView mav = new ModelAndView("/Login");
			mavl.addObject("error", "Invalid username and password!");
			return mavl;
		}
		final CredentialModel model=userDetailsService.findByUsername(a.getUsername());
		if(model.getRoles().equals("ROLE_USER")) {
			final UserModel p=userDetailsService.findByEmail(a.getUsername());
			//ModelAndView mod = new ModelAndView("/Home");
			modh.addObject("userDetails",p);
			int id=p.getUserId();
			qids=id;
			UserModel uo=repo.getById(qids);
			String uId=String.valueOf(id);
			session.setAttribute("userId", uId);
			List<imageEntityClass> l=service.getAllPosts();
			modh.addObject("AllPosts",l);
			modh.addObject("Roles", uo);		
			return modh;
		}else {
			final UserModel p=userDetailsService.findByEmail(a.getUsername());
			List<UserModel> li=service.getAllUsers();
			//ModelAndView mod = new ModelAndView("/admin");
			moda.addObject("userDetails", li);
			int id=p.getUserId();
			qids=id;
			UserModel uo=repo.getById(qids);
			String uId=String.valueOf(id);
			session.setAttribute("userId", uId);
			List<imageEntityClass> l=service.getAllPosts();
			moda.addObject("AllPosts",l);
			moda.addObject("Roles", uo);
			return moda;
		}
	}
	@GetMapping("/MyPosts")
	public ModelAndView getMyPosts() throws UnsupportedEncodingException {
		List<imageEntityClass> l =service.getMyPosts(qids);
		//ModelAndView mi=new ModelAndView("/Posts");
		UserModel u=repo.getById(qids);
		modp.addObject("MyPosts", l);
		modp.addObject("userModel", u);
		return modp;
	}
	@PostMapping("/upload")
	public ModelAndView uploadPost(@RequestParam("image") MultipartFile p,@RequestParam("tag") String tag,@RequestParam("userId") int id) throws IOException {
		byte[] i=p.getBytes();
		uploadEntity u=new uploadEntity(id,tag,i);
		uploadEntity t=service.saveToMyPosts(u);
		int Uid=t.getUserId();
		List<imageEntityClass> l=service.getMyPosts(Uid);
		//ModelAndView mk=new ModelAndView("/Posts");
		UserModel ui=repo.getById(Uid);
		modp.addObject("MyPosts", l);
		modp.addObject("userModel",ui);
		return modp;
	}
	@GetMapping("/home")
	public ModelAndView homecoming() throws UnsupportedEncodingException
	{
		List<imageEntityClass> t = service.getAllPosts();
		//ModelAndView mav = new ModelAndView("/Home");
		UserModel u=repo.getById(qids);
		modh.addObject("AllPosts",t);
		modh.addObject("Roles", u);
		return modh;
	}
	@GetMapping("/AllReports")
	public ModelAndView AllReports()
	{
		List<MyReportEntityClass> re = service.getAllReports();
		//ModelAndView mav = new ModelAndView("/Reports");
		UserModel u=repo.getById(qids);
		modr.addObject("AllReports",re);
		modr.addObject("Roles", u);
		return modr;
	}
	@GetMapping("/MyReports")
	public ModelAndView MyReports()
	{
		List<MyReportEntityClass> re = service.getMyReports(qids);
		//ModelAndView mav = new ModelAndView("/MyReports");
		UserModel u=repo.getById(qids);
		modmr.addObject("ReportList",re);
		modmr.addObject("userModel", u);
		return modmr;
	}
	@PostMapping("/uploadReport")
	public ModelAndView uploadReport(@RequestParam("issue") String issue,@RequestParam("userId") int id)
	{
		MyReportEntity my = new MyReportEntity(id, issue);
		MyReportEntity m1 = service.saveToMyReport(my);
		int Uid=m1.getUserId();
		List<MyReportEntityClass> re = service.getMyReports(Uid);
		//ModelAndView mav = new ModelAndView("/MyReports");
		UserModel ui=repo.getById(Uid);
		modmr.addObject("ReportList",re);
		modmr.addObject("userModel",ui);
		return modmr;
	}
	@PostMapping("/comment")
	public ModelAndView postComment(@RequestParam("postId") int pId,@RequestParam("comment") String comment) throws UnsupportedEncodingException {
		commentEntity u=new commentEntity(pId,qids,comment);
		service.saveToComment(u);
		List<imageEntityClass> t = service.getAllPosts();
		//ModelAndView mav = new ModelAndView("/Home");
		UserModel ui=repo.getById(qids);
		modh.addObject("AllPosts",t);
		modh.addObject("Roles", ui);
		return modh;
	}
	@GetMapping("/AllComments/{id}")
	public ModelAndView viewComments(@PathVariable("id") int id) throws UnsupportedEncodingException {
		List<commentEntityClass> t=service.viewComments(id);
		List<imageEntityClass> g=service.getMyPostsComment(id);
		//ModelAndView mav=new ModelAndView("/comment");
		modc.addObject("comments",t);
		modc.addObject("post",g);
		return modc;
	}
	@GetMapping("/back")
	public ModelAndView getBack() throws UnsupportedEncodingException
	{
		List<imageEntityClass> t = service.getAllPosts();
		//ModelAndView mav = new ModelAndView("/Home");
		UserModel u=repo.getById(qids);
		modh.addObject("AllPosts",t);
		modh.addObject("Roles", u);
		return modh;
	}
	@PostMapping("/suggest")
	public ModelAndView PostSuggestion(@RequestParam("rId") int rId,@RequestParam("userId") int userId,@RequestParam("suggest") String suggest) {
		suggestEntity s=new suggestEntity(rId,userId,suggest);
        service.saveToSuggestions(s);
		List<MyReportEntityClass> re = service.getAllReports();
		//ModelAndView mav = new ModelAndView("/Reports");
		UserModel u=repo.getById(qids);
		modr.addObject("AllReports",re);
		modr.addObject("Roles", u);
		return modr;
	}
	@GetMapping("/getAllSuggest/{id}")
	public ModelAndView viewSuggestions(@PathVariable("id") int id) {
	
		List<suggestionClass> l=service.viewSuggestions(id);
		MyReportEntity h=service.getMyReportSuggest(id);
		//ModelAndView mav=new ModelAndView("/suggestions");
		mods.addObject("suggestions", l);
		mods.addObject("Report", h);
		return mods;
	}
	@GetMapping("/return")
	public ModelAndView getBackToReports() {
		List<MyReportEntityClass> re = service.getAllReports();
		//ModelAndView mav = new ModelAndView("/Reports");
		UserModel u=repo.getById(qids);
		modr.addObject("AllReports",re);
		modr.addObject("Roles",u);
		return modr;
	}
	@GetMapping("/delete/{id}")
	public ModelAndView deleteReport(@PathVariable("id") int id) {
		ModelAndView mav = null;
		int i=service.deleteInSuggestions(id);
		int j=service.deleteInReports(id);
		if(i>0 && j>0) {
			List<MyReportEntityClass> re = service.getMyReports(qids);
			mav = new ModelAndView("/MyReports");
			mav.addObject("ReportList",re);
		}
		return mav;
	}
	@GetMapping("/deletePost/{id}")
	public ModelAndView deletePost(@PathVariable("id") int id) throws UnsupportedEncodingException {
		ModelAndView mav=null;
		int j=service.deleteInPost(id);
		if(j>0) {
			List<imageEntityClass> l =service.getMyPosts(qids);
			mav=new ModelAndView("/Posts");
			UserModel ui=repo.getById(qids);
			mav.addObject("MyPosts", l);
			mav.addObject("userModel", ui);
		}
		return mav;
	}
	@GetMapping("/Profile")
	public ModelAndView MyProfile()
	{
		List<UserModel> q = service.getProfileDetails(qids);
		//ModelAndView mav= new ModelAndView("/profile");
		UserModel m=repo.getById(qids);
		modpr.addObject("userDetails",q);
		modpr.addObject("Roles",m);
		return modpr;
	}
	@PostMapping("/Edit")
	public ModelAndView profileeditdetails(@RequestParam("id") int id,@RequestParam("name") String name,@RequestParam("pass") String password,@RequestParam("phone") String phone)
	{
		UserModel u=service.getDetails(id);
		u.setFullname(name);
		u.setPassword(password);
		u.setMobileNumber(phone);
		CredentialModel c=service.getCredential(id);
		c.setUsername(u.getEmail());
		c.setPassword(password);
		service.saveToCredential(c);
	service.saveToUser(u);
		List<UserModel> q = service.getProfileDetails(id);
		//ModelAndView mav= new ModelAndView("/profile");
		UserModel ui=repo.getById(id);
		modpr.addObject("userDetails",q);
		modpr.addObject("Roles", ui);
		return modpr;
	}
	@GetMapping("/getUserProfile/{id}")
	public ModelAndView getUserProfile(@PathVariable("id") int id) {
		List<UserModel> q = service.getProfileDetails(id);
		ModelAndView mav= new ModelAndView("/ProfileAdmin");
		mav.addObject("userDetails",q);
		return mav;
	}
	@GetMapping("/deleteUserProfile/{id}")
	public ModelAndView deleteUser(@PathVariable("id") int id) {
		service.deleteUser(id);
		List<UserModel> li=service.getAllUsers();
		//ModelAndView mod = new ModelAndView("/admin");
		moda.addObject("userDetails", li);
		return moda;
	}
	@GetMapping("/adminHome")
	public ModelAndView getBackAdmin() {
		List<UserModel> li=service.getAllUsers();
		//ModelAndView mod = new ModelAndView("/admin");
		moda.addObject("userDetails", li);
		return moda;
	}
	

}
