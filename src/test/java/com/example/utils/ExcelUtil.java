package com.example.utils;

import com.example.pojo.Dept;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ExcelUtil {
    public static ByteArrayInputStream exportExcel(List<com.example.demo.poi.pojo.Dept> list, String path) throws Exception {
        System.out.println(list);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook);
        Sheet sheet = workbook.createSheet();
        //指定合并开始行、合并结束行 合并开始列、合并结束列
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 1);
        //添加要合并地址到表格
        sheet.addMergedRegion(rangeAddress);
        // 设置第3列的列宽为20
        int width = (int) ((20 + 0.72) * 256);
        sheet.setColumnWidth(0,width);
        CellStyle styleTitle = getTitleStle(workbook);
        CellStyle styleContent = getContentStle(workbook);
        Row row0 = sheet.createRow(0);
        row0.setHeight((short) 256);
        Cell cell0 = row0.createCell(Cell.CELL_TYPE_NUMERIC);
        cell0.setCellValue("tile:员工表");
        cell0.setCellStyle(styleTitle);
        for (int i = 0; i < list.size(); i++) {
            int j = 0;
            //创建行
            Row row = sheet.createRow(i+1);
            //创建列
            Cell cell_0 = row.createCell(j);
            cell_0.setCellStyle(styleContent);
            cell_0.setCellValue(list.get(i).getDid());
            //创建列
            Cell cell_1 = row.createCell(++j);
            cell_1.setCellStyle(styleContent);
            cell_1.setCellValue(list.get(i).getDname());
        }
        String pic1 = "C:\\Users\\WTL\\Pictures\\Saved Pictures\\勇士懒羊羊.jpg";
        //插入图片
        insertPicCost(workbook,sheet,pic1,width);
        //如果你需要将excel放置到某个地方，比如说写出到某个文件夹下，那可以在直接在此处进行写出即可，根据自己业务做修改。这里只是将excel文件以流的形式return，用于其他方法做操作。
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        return new ByteArrayInputStream(bos.toByteArray());
//        FileOutputStream out = new FileOutputStream(path);
//        workbook.write(out);
//        out.flush();
//        out.close();
    }

    public static void insertPicCost(Workbook workBook,Sheet topPic,String pic,int width){
        BufferedImage bufferImg = null;//图片
        try {
            // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            //将图片读到BufferedImage
            bufferImg = ImageIO.read(new File(pic));
            // 将图片写入流中
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            // 利用HSSFPatriarch将图片写入EXCEL
            XSSFDrawing patriarch = (XSSFDrawing) topPic.createDrawingPatriarch();
            //图片导出到单元格B2中
            //dx1: 图片左上角到sheet边框左侧距离
            //dy1: 图片左上角到sheet边框上侧距离
            //dx2: 图片右下角到sheet边框左侧距离
            //dy2: 图片右下角到sheet边框上侧距离
            //col1：起始单元格列序号，从0开始计算
            //row1：起始单元格行序号，从0开始计算
            //col2：终止单元格列序号，从0开始计算
            //row2：终止单元格行序号，从0开始计算
            //col1,row1,col2，row2把线画出来(画长一点，线经过点即可eg（ 2, 256, width * 4, 256*4, (short) 2, 1, (short) 4, 3）最后一个参数是3 )，取交集
            //C2-D4
            XSSFClientAnchor anchor = new XSSFClientAnchor(width * 2, 256, width * 4, 256*4,
                    (short) 2, 1, (short) 4, 4);
            // 插入图片
            patriarch.createPicture(anchor, workBook.addPicture(byteArrayOut
                    .toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            System.out.println("插入成功" );
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("插入失败 : " + io.getMessage());
        } finally {

        }
    }

    private static CellStyle getContentStle(SXSSFWorkbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
//        // 字体加粗
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        CellStyle style = workbook.createCellStyle();
//        // 设置底边框;
//        style.setBorderBottom(CellStyle.BORDER_THIN);
//        // 设置底边框颜色;
//        style.setBottomBorderColor(IndexedColors.BLACK.index);
//        // 设置左边框;
//        style.setBorderLeft(CellStyle.BORDER_THIN);
//        // 设置左边框颜色;
//        style.setLeftBorderColor(IndexedColors.BLACK.index);
//        // 设置右边框;
//        style.setBorderRight(CellStyle.BORDER_THIN);
//        // 设置右边框颜色;
//        style.setRightBorderColor(IndexedColors.BLACK.index);
//        // 设置顶边框;
//        style.setBorderTop(CellStyle.BORDER_THIN);
//        // 设置顶边框颜色;
//        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }

    private static CellStyle getTitleStle(SXSSFWorkbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        CellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(CellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(CellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        // 设置右边框;
        style.setBorderRight(CellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(CellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return style;
    }
}
