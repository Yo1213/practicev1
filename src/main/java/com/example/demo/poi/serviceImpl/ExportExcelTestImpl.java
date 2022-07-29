package com.example.demo.poi.serviceImpl;

import com.example.demo.poi.mapper.ExportExcelTestMapper;
import com.example.demo.poi.pojo.Dept;
import com.example.demo.poi.service.ExportExcelTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportExcelTestImpl implements ExportExcelTestService {
    @Autowired
    ExportExcelTestMapper exportExcelTestMapper;

    public List<Dept> getMessage(){
       return exportExcelTestMapper.getMessage();
    }
}
