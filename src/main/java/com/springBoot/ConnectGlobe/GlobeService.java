package com.springBoot.ConnectGlobe;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class GlobeService implements UserDetailsService{
	
	@Autowired
	GlobeRepo repo;
	
	@Autowired
	UserRepo repop;
	
	@Autowired
	uploadEntityRepo repou;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CredentialModel model = repo.findByUsername(username);
		if(model==null) {
			throw new UsernameNotFoundException("Not Found...");
		}
		return new GlobeGetDetails(model);
	}
	public UserModel saveToUser(UserModel model) {
		model.setPassword(passwordEncoder.encode(model.getPassword()));
		UserModel u=repop.save(model);
		return u;
	}
	public CredentialModel saveToCredential(CredentialModel model) {
		model.setPassword(passwordEncoder.encode(model.getPassword()));
		CredentialModel c=repo.save(model);
		return c;
	}
	public CredentialModel findByUsername(String username) {
		CredentialModel model=repo.findByUsername(username);
		return model;
	}
	public UserModel findByEmail(String username) {
		UserModel p=repop.findByEmail(username);
		return p;
	}
	public uploadEntity saveToMyPosts(uploadEntity u) {
		uploadEntity t=repou.save(u);
		return t;
	}
	public List<imageEntityClass> getAllPosts() throws UnsupportedEncodingException{
		String image="";
		List<imageEntityClass> t=new ArrayList<>();
		List<uploadEntity> l=repou.findAll();
		for(int i=0;i<l.size();i++) {
			if(l.get(i).getImage() != null) {
				byte[] encode = java.util.Base64.getEncoder().encode(l.get(i).getImage());
				image =new String(encode,"UTF-8");
				
			}
			String tag=l.get(i).getTag();
			int pId=l.get(i).getpId();
			int userId=l.get(i).getUserId();
			UserModel q=repop.getById(userId);
			String name=q.getFullname();
			imageEntityClass k=new imageEntityClass(pId,userId,tag,image,name);
			System.out.println(k.toString());
			t.add(k);
		}
		System.out.println(t);
		return t;
	}
	public List<imageEntityClass> getMyPosts(int id) throws UnsupportedEncodingException{
		System.out.println("inside");
		String image="";
		List<imageEntityClass> t=new ArrayList<>();
		List<uploadEntity> l=repou.findByUserId(id);
		System.out.println("outside"+l.size());
		for(int i=0;i<l.size();i++) {
			if(l.get(i).getImage() != null) {
				byte[] encode = java.util.Base64.getEncoder().encode(l.get(i).getImage());
				image =new String(encode,"UTF-8");				
			}
			String tag=l.get(i).getTag();
			int pId=l.get(i).getpId();
			int userId=l.get(i).getUserId();
			UserModel q=repop.getById(userId);
			String name=q.getFullname();
			imageEntityClass k=new imageEntityClass(pId,userId,tag,image,name);
			System.out.println(k.toString());
			t.add(k);
		}
		System.out.println(t);
		return t;
	}
}
