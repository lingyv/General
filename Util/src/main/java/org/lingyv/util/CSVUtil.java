package org.lingyv.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lingyv on 2016/9/21.
 * CSV文件转换
 */
public class CSVUtil {
    /**
     * 将数据保存成CSV文件
     *
     * @param path        保存地址
     * @param FILE_HEADER CSV文件头
     * @param lists       需要保存的数据
     * @return
     */
    public String CSVFileWrite(String path, String[] FILE_HEADER, List<List<String>> lists) {
        //CSV文件分隔符
        final String NEW_LINE_SEPARATOR = "\n";
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //创建 CSVFormat
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        try {
            //初始化FileWriter
            fileWriter = new FileWriter(path);
            //初始化 CSVPrinter
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            //创建CSV文件头
            csvFilePrinter.printRecord(FILE_HEADER);
            // 遍历List写入CSV
            for (List l : lists) {
                csvFilePrinter.printRecord(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 解析CSV文件
     *
     * @param fileName    --> 需要解析的文件路径
     * @param FILE_HEADER -->  约定需要解析的csv文件的列名
     * @return 返回结果是一个List，List中的元素是CSV文件的每一行的数据，行中的元素用与之对应的列名取值
     * @throws IOException
     */
    public List<CSVRecord> CsvFileReader(String fileName, String[] FILE_HEADER) throws IOException {

        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        List<CSVRecord> csvRecords = null;
        //创建CSVFormat
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);
        //初始化FileReader object
        fileReader = new FileReader(fileName);
        //初始化 CSVParser object
        csvFileParser = new CSVParser(fileReader, csvFileFormat);
        //CSV文件records --> 解析结果
        csvRecords = csvFileParser.getRecords();

        fileReader.close();
        csvFileParser.close();

        return csvRecords;
    }

    /**
     * 根据给定的FILE_HEADER，对比需要解析file的类名，判断用户上传的是否是约定的文件
     * 如果所解析文件的列名不包含FILE_HEADER，则返回false
     *
     * @param filePath    --> 需要解析的文件路径
     * @param FILE_HEADER -->  约定需要解析的csv文件的列名
     * @return
     * @throws IOException
     */
    public Boolean isRightCSV(String filePath, String[] FILE_HEADER) throws IOException {
        File inFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String[] readers = reader.readLine().split(",");
        int a = 0;
        if (readers != null && readers.length > 0) {
            for (String r : readers) {
                for (String h : FILE_HEADER) {
                    if (r.equals(h)) {
                        a++;
                    }
                }
            }
        }

        reader.close();

        return a == FILE_HEADER.length;
    }

    /**
     * 将页面传过来的List<Object>类型转成List<List<String>>类型
     *
     * @param FILE_HEADER CSV文件头
     * @param objects     需要转型的对象
     * @return
     */
    public List<List<String>> toList(String[] FILE_HEADER, List<Object> objects) {
        List<List<String>> lists = new ArrayList<List<String>>();
        for (Object l : objects) {
            List<String> list = new ArrayList<String>();
            Map<String, String> map = (Map<String, String>) l;
            for (String s : FILE_HEADER) {
                list.add(map.get(s));
            }
            lists.add(list);
        }
        return lists;
    }
}
