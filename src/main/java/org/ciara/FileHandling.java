package org.ciara;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandling {
    //File Create In download Folder
    public String FolderCreate(String folderName) {
        String downloadPath = System.getProperty("user.home") + "/Downloads/" + folderName;
        Path foldPath = Paths.get(downloadPath);

        try{
            Files.createDirectories(foldPath);
            System.out.println("Folder created: " + foldPath);
        }catch (IOException e){
            System.out.println("Failed to create folder: " + foldPath);
            e.printStackTrace();
        }
        return downloadPath;
    }

    //folder delete in download folder
    public void DeleteDerectory(String folderName) {
        String downloadPath = System.getProperty("user.home") + "/Downloads/" + folderName;
        Path foldPath = Paths.get(downloadPath);

        try{
            Files.deleteIfExists(foldPath);
            System.out.println("Folder deleted: " + foldPath);
        }catch (IOException e){
            System.out.println("Failed to delete folder: " + foldPath);
            e.printStackTrace();
        }

    }

}
