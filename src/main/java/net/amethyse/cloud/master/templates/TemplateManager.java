package net.amethyse.cloud.master.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class TemplateManager {

  public void zipFiles(Collection<File> files, File base, OutputStream output) throws IOException {
    ZipOutputStream zos = new ZipOutputStream(output);
    for(File file : files) addFile(file,base,zos);
    zos.close();
    output.flush();
  }

  private static void addFile(File file, File base, ZipOutputStream zos){

    if(file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0){
      for(File file1 : file.listFiles()) addFile(file1,base,zos);
      return;
    }

    try{

      if(!file.exists()) return;

      String relative = base.toURI().relativize(file.toURI()).getPath();
      if(file.isDirectory() && !relative.endsWith("/")) relative += "/";
      ZipEntry zipEntry = new ZipEntry(relative);
      zos.putNextEntry(zipEntry);
      if(!file.isDirectory()){
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0){
          zos.write(bytes,0,length);
        }
        fis.close();
      }
      zipEntry.setTime(file.lastModified());
      zos.closeEntry();

    }catch (Exception e){
      e.printStackTrace();
    }

  }

}
