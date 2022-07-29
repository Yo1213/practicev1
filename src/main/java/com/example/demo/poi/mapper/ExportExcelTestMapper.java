package com.example.demo.poi.mapper;

import com.example.demo.poi.pojo.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
public interface ExportExcelTestMapper {
    List<Dept> getMessage();
}
