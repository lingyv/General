package org.lingyv.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingyv on 2016/11/8.
 */
public class FileUtil {

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
