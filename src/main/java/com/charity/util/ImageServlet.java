package com.charity.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.charity.dao.CouponRepository;

@Controller
public class ImageServlet {

	@Autowired
	private CouponRepository couponRepository;

	@RequestMapping(value = "/imageServlet")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("########### ImageServlet doGet,id: " + request.getParameter("id"));
		Long id = Long.valueOf(request.getParameter("id"));
		byte[] image = couponRepository.getImageById(id);
		if (image != null) {
			response.setContentType("image/png");
			response.setContentLength(image.length);
			response.getOutputStream().write(image);
		}
	}

}
