package com.example.controller;

import com.example.service.CpuStatsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CpuStateController {
    private final CpuStatsService cpuStatsService;

    public CpuStateController(CpuStatsService cpuStatsService) {
        this.cpuStatsService = cpuStatsService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public String getStats() throws Exception {
        return cpuStatsService.getCpuStatsJson();
    }
}
