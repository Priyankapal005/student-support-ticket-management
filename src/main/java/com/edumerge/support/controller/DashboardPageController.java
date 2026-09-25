package com.edumerge.support.controller;

	import com.edumerge.support.dto.DashboardResponse;
	import com.edumerge.support.service.DashboardService;

	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.GetMapping;

	@Controller
	public class DashboardPageController {

	    private final DashboardService dashboardService;

	    public DashboardPageController(
	            DashboardService dashboardService) {

	        this.dashboardService = dashboardService;
	    }

	    @GetMapping("/dashboard")
	    public String dashboard(Model model) {

	        DashboardResponse dashboard =
	                dashboardService.getDashboard();

	        model.addAttribute("dashboard", dashboard);

	        return "dashboard";
	    }
	}


