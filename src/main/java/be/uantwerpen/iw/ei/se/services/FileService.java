package be.uantwerpen.iw.ei.se.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Quinten on 10/12/2015.
 */
@Service
public class FileService {

    @Autowired
    private ServletContext servletContext;

    public String saveFile(MultipartFile multipartFile) {

        String curDir = Paths.get("").toAbsolutePath().toString();
        String fileDir = curDir + "\\Tests\\Files\\";

        if(! new File(fileDir).exists()) {
            return "";
        }

        String fileName = multipartFile.getOriginalFilename();
        // strip .zip, add random, add .zip
        fileName = fileName.substring(0,fileName.lastIndexOf('.')) + getHash(new Date().toString()) + ".zip";

        File newFile = new File(fileDir + fileName);
        try {
            multipartFile.transferTo(newFile);
            return fileName;
        } catch(IOException e) {
            return "";
        }
    }

/*    private File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            return null;
        }
    }*/

    private String getHash(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes());

            byte byteData[] = md.digest();

            return byteData.toString();
        } catch(NoSuchAlgorithmException e) {
            return "";
        }
    }
}
