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
public class FileService
{
    @Autowired
    private ServletContext servletContext;

    public String saveFile(MultipartFile multipartFile) throws Exception
    {
        String curDir = Paths.get("").toAbsolutePath().toString();
        String fileDir = curDir + "\\Tests\\Files\\";

        File folderLocation = new File(fileDir);

        if(!folderLocation.exists())
        {
            if(!folderLocation.mkdirs())
            {
                throw new Exception("Downloadfolder: " + fileDir + " could not be created!");
            }
        }

        String fileName = multipartFile.getOriginalFilename();
        //Strip extension, add random, add .zip
        fileName = fileName.substring(0,fileName.lastIndexOf('.')) + "-_-" + getHash(new Date().toString()) + ".zip";

        File newFile = new File(fileDir + fileName);
        try
        {
            multipartFile.transferTo(newFile);
            return fileName;
        }
        catch(IOException e)
        {
            throw new Exception("Could not save file: " + fileName + " on server!");
        }
    }

    public FileSystemResource loadFile(String fileName)
    {
        String curDir = Paths.get("").toAbsolutePath().toString();
        String fileDir = curDir + "\\Tests\\Files\\";

        File file = new File(fileDir + fileName);
        FileSystemResource returnFile = new FileSystemResource(file);

        return returnFile;
    }

    private String getHash(String in)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes());

            byte byteData[] = md.digest();

            return byteData.toString();
        }
        catch(NoSuchAlgorithmException e)
        {
            return "";
        }
    }
}
