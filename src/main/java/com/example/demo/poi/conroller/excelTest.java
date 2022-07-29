package com.example.demo.poi.conroller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.Calendar;

public class excelTest {
    public static void main(String[] args) throws Exception {
        Workbook wb = new HSSFWorkbook();
        FileOutputStream fileOutput = new FileOutputStream("C:\\Users\\WTL\\Desktop\\自定义excel.xml");
        Sheet sheet = wb.createSheet("第一个sheet");
//        wb.createSheet("第二个sheet");
        Row rowOne = sheet.createRow(0);
        rowOne.createCell(0).setCellValue(13);
        rowOne.createCell(1).setCellValue("第二列");
        rowOne.createCell(2).setCellValue(Calendar.getInstance().getTime());
        //设置样式
        CellStyle cellStyle = wb.createCellStyle();
        //样式 -->  设置日期格式
        short format = wb.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd:HH-mm-ss");
        cellStyle.setDataFormat(format);
        Cell cell3 = rowOne.createCell(3);
        cell3.setCellValue(Calendar.getInstance().getTime());
        //单独给一个单元格设置样式
        cell3.setCellStyle(cellStyle);
        rowOne.createCell(4).setCellValue(HSSFCell.CELL_TYPE_NUMERIC);//0

        //TODO 遍历
        wb.write(fileOutput);
        fileOutput.close();
    }
}
