package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;

import jakarta.servlet.http.HttpSession;


@Controller
public class AuthController {
	
	@Autowired // 13.1
	HttpSession session;
	
	@Autowired // seq:12.1
	Account account;
	
	// ログイン画面表示
	@GetMapping("/")	// seq:11.1
	public String index() {
		return "login"; // seq:11.2
	}
	
	// ログイン実行
	@PostMapping("/login")	   	  // seq:12.2
	public String login(@RequestParam("name") String name) {
		account.setName(name); 	  // seq:12.3
		return "redirect:/items"; // seq:12.4
	}
	
	// ログアウト処理
	@GetMapping("/logout")	  // seq:13.2
	public String logout() {
		session.invalidate(); // seq:13.3
		return "login"; 	  // seq:13.4
	}
	
}
