package com.example.demo.poi.conroller;

import com.example.demo.poi.pojo.Dept;
import com.example.demo.poi.service.ExportExcelTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class exportExcel {
    @Autowired
    ExportExcelTestService excelTestService;

    @RequestMapping("/exportExcelTest")
    public void exportExcelTest(){
        System.out.println("1");
        List<Dept> message = excelTestService.getMessage();
        System.out.println(message);
    }
}

