package org.lingyv.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingyv on 2016/11/8.
 */
public class FileUtil {

    /**
     * 读取文件
     * 将文件内容保存在List<String>对象中
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static List<String> fileReader(String filePath) throws IOException {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String lineContent = null;
                //读取文件的每一行，并添加到List容器中
                while ((lineContent = reader.readLine()) != null) {
                    //加上换行符(读的过程中换行符会被去除掉)
                    //fileContent.add(lineContent+"\n");
                    fileContent.add(lineContent);
                }
            }
        } else {
            throw new IOException("文件 " + filePath + " 不存在");
        }

        return fileContent;
    }

    /**
     * 将数据写入文件
     * 如果文件不存在，则创建文件
     * 如果文件存在且有内容，则会删掉原有内容再写入
     *
     * @param dirPath
     * @param fileName
     * @param fileContent
     * @return
     * @throws IOException
     */
    public static boolean fileWriter(String dirPath, String fileName, List<String> fileContent) throws IOException {
        return writer(dirPath, fileName, fileContent, false);
    }

    /**
     * 将数据追加写入文件
     * 如果文件不存在，则创建文件
     *
     * @param dirPath
     * @param fileName
     * @param fileContent
     * @throws IOException
     */
    public static boolean fileAppend(String dirPath, String fileName, List<String> fileContent) throws IOException {
        return writer(dirPath, fileName, fileContent, true);
    }

    /**
     * 将数据写入文件
     * 如果文件不存在，则创建文件
     * @param dirPath
     * @param fileName
     * @param fileContent
     * @param append  --> 是否保留原文件内容
     * @return
     * @throws IOException
     */
    private static boolean writer(String dirPath, String fileName, List<String> fileContent, boolean append) throws IOException {
        boolean result = false;
        if (createDir(dirPath)) {
            String filePath = dirPath + "/" + fileName;
            try (FileWriter fileWriter = new FileWriter(filePath,append);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter writer = new PrintWriter(bufferedWriter)) {
                if (fileContent != null && !fileContent.isEmpty()) {
                    for (String content : fileContent) {
                        writer.println(content);
                    }
                    result = true;
                } else {
                    throw new IOException("没有要写入的内容");
                }
            }
        } else {
            throw new IOException("文件夹" + dirPath + "不存在");
        }

        return result;
    }

    /**
     * 将文件路径转换成真实的保存路径
     *
     * @param filePath --> 你要保存的相对路径
     * @param request
     * @return
     */
    public static String getRealPath(String filePath, HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("./ATP/upLoad/" + filePath);
    }

    public static String creatFile(String path, String fileName) throws IOException {
        //如果文件夹不存在-->创建文件夹
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            Boolean success = targetFile.mkdirs();
        }
        //如果文件不存在-->创建文件
        File file = new File(path, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return path + fileName;
    }

    /**
     * 保存文件
     *
     * @param path -->  保存的真实路径
     * @param file -->  要保存的文件
     * @return
     */
    public String saveFile(String path, MultipartFile file) {
        return saveFile(path, file, false);
    }

    /**
     * 保存文件
     *
     * @param path      保存的真实路径
     * @param file      要保存的文件
     * @param duplicate 是否需要自动生成Id避免文件名重复
     * @return
     */
    public String saveFile(String path, MultipartFile file, Boolean duplicate) {
        String fileName = null;
        if (file != null) {
            //获取文件名称
            fileName = duplicate ? (ShortId.getId() + file.getOriginalFilename()) : file.getOriginalFilename();
            //保存文件
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                Boolean success = targetFile.mkdirs();
            }

            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileName;
    }

    /**
     * 保存多个文件
     *
     * @param path  文件要保存的路径
     * @param files 要保存的文件
     * @return
     */
    public List<String> saveFiles(String path, MultipartFile[] files) {
        return saveFiles(path, files, false);
    }

    /**
     * 保存多个文件
     *
     * @param path      文件要保存的路径
     * @param files     要保存的文件
     * @param duplicate 是否需要生成Id以防文件名重复
     * @return
     */
    public List<String> saveFiles(String path, MultipartFile[] files, Boolean duplicate) {
        List<String> fileNames = new ArrayList<String>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                fileNames.add(saveFile(path, file, duplicate));
            }
        }

        return fileNames;
    }


    public static boolean createDir(String dirPath) {
        boolean success = true;
        File file = new File(dirPath);
        if (!file.exists()) {
            success = file.mkdirs();
        }
        return success;
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static Boolean deleteFile(String path) {
        Boolean ret = false;
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            ret = file.delete();
        }
        return ret;
    }
}
