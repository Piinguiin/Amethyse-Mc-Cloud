package net.amethyse.cloud.lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 28.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class GsonFileWriter {

  private Logger logger;
  private final File path;
  private final String fileName;
  private Gson gson;

  /**
   * Instantiates a new Gson file writer.
   *
   * @param path     the path
   * @param fileName the file name
   */
  public GsonFileWriter(final  Logger logger, final File path, final String fileName) {
    this.logger = logger;
    this.path = path;
    this.fileName = fileName;
    gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
  }

  /**
   * Write a class into a json file.
   *
   * @param dataObject the object to serialize
   */
  public <T> void write(final T dataObject) {

    final File file = new File(path, fileName + ".json");

    final boolean dirCreated = file.getParentFile().mkdirs();

    if (dirCreated) {
      logger.info("Directory for '" + fileName + ".json' created.");
    }

    final boolean fileCreated;
    try {
      fileCreated = file.createNewFile();
    } catch (final IOException e) {
      logger.error("Unable to create path/file.");
      logger.error(e.getMessage());
      return;
    }

    if (fileCreated) {
      logger.info("'" + fileName + ".json' created.");
    }

    final String jsonString = gson.toJson(dataObject, dataObject.getClass());
    final FileWriter writer;
    try {
      writer = new FileWriter(file);
      writer.write(jsonString);
      writer.close();
    } catch (final IOException e) {
      logger.error(e.getMessage());
    }

  }

}
