package com.example.demo;

import com.example.demo.poi.mapper.ExportExcelTestMapper;
import com.example.pojo.Dept;
import com.example.utils.EmailUtil;
import com.example.utils.ExcelUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        List<Dept> list = new ArrayList<>();
        Dept dept1 = new Dept("985", "市场部");
        Dept dept2 = new Dept("99", "研发部");
        Dept dept3 = new Dept("102", "人事部");
        list.add(dept1);
        list.add(dept2);
        list.add(dept3);
        List<Dept> list2 = list.stream().sorted(Comparator.comparingInt(e -> Integer.valueOf(e.getDid()))).collect(Collectors.toList());
        Collections.reverse(list2);
        System.out.println(list2);
        //Collections.reverse(list2);
        List<Dept> list3 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            list3.add(list2.get(i));
        }
        System.out.println(list3);
    }

    @Resource
    ExportExcelTestMapper exportExcelTestMapper;

    @Test
    public void exportExcel() throws Exception {
        List<com.example.demo.poi.pojo.Dept> message = exportExcelTestMapper.getMessage();
        File file = new File("C:\\Users\\WTL\\Desktop\\test8_2.xlsx");
        if (!file.exists()) {
            file.createNewFile();
        }
        String path = file.getPath();
        ByteArrayInputStream fileOutputStream = ExcelUtil.exportExcel(message, path);

        //表的标题名称
        String sheetName = "教师满意度调查（投票统计表）";
        boolean boo = false;
        try {
            if (fileOutputStream == null) {
                System.out.println("创建表格失败");
            } else {
                String userEmail = "wuyi1312@163.com";
                boo = EmailUtil.sendMail(fileOutputStream, sheetName, userEmail);
                fileOutputStream.close();
            }
            if (boo) {
                System.out.println("导出成功");
            } else {
                System.out.println("导出数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
