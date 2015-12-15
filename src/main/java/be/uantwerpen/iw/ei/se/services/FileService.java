package be.uantwerpen.iw.ei.se.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.nio.file.Files;
import java.nio.file.Path;
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
        fileName = fileName.substring(0,fileName.lastIndexOf('.')) + "-_-" + getHash(new Date().toString()) + ".zip";

        File newFile = new File(fileDir + fileName);
        try {
            multipartFile.transferTo(newFile);
            return fileName;
        } catch(IOException e) {
            return "";
        }
    }

    public FileSystemResource loadFile(String fileName) {
        String curDir = Paths.get("").toAbsolutePath().toString();
        String fileDir = curDir + "\\Tests\\Files\\";
        File file = new File(fileDir + fileName);
        FileSystemResource returnFile = new FileSystemResource(file);
        return returnFile;
        /*Path path = Paths.get(fileDir + fileName);

        String contentType = "application/zip";
        try {
            byte[] content = Files.readAllBytes(path);
            MultipartFile result = new MockMultipartFile(fileName, fileName, contentType, content);
            return result;
        } catch (final IOException e) {
            return null;
        }*/
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
