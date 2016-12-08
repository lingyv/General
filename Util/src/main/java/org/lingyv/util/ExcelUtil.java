package org.lingyv.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by lingyv on 2016/11/9.
 */
public class ExcelUtil {

    /**
     * 向模板文件中添加数据
     *
     * @param fileName     -->要保存的文件名
     * @param filePath     -->要保存的地址
     * @param templateName -->模板文件名
     * @param lists        -->要追加的数据
     *                     类型为<List<List<String>>，其中List<String>中的String对应每行中每个单元格，按String在List中的顺序写入单元格
     * @return 返回是否成功
     * @throws IOException
     */
    public static Boolean writeAtpExcel(String fileName, String filePath, String templateName, List<List<String>> lists) throws IOException {
        URL url = ExcelUtil.class.getClassLoader().getResource(templateName); //获取文件模板路径
        //如果文件夹不存在-->创建文件夹
        Boolean success = true;
        Boolean ret = false;
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            success = targetFile.mkdirs();
        }
        if (success) {
            FileOutputStream out = new FileOutputStream(filePath + fileName);  //最终文件保存在filePath中
            FileInputStream inputStream = new FileInputStream(url.getFile());  //获取模板文件
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);  //使用POI提供的方法得到excel的信息
            HSSFWorkbook workbook = new HSSFWorkbook(poifsFileSystem);
            HSSFSheet sheet = workbook.getSheetAt(0);  //获取到第一个位置的工作表
            int i = sheet.getLastRowNum() + 1;    //获取最后一行下一行的行号
            for (List<String> list : lists) {
                HSSFRow row = sheet.createRow(i);
                int count = 0;
                for (String s : list) {
                    HSSFCell cell = row.createCell(count);
                    cell.setCellValue(s);
                    count++;
                }
                i++;
            }
            out.flush();
            workbook.write(out);
            ret = true;
            out.close();
        }
        return ret;
    }
}
