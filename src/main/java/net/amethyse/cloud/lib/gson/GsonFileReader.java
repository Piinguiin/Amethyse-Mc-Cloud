package net.amethyse.cloud.lib.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
public class GsonFileReader {

  private final Logger logger;
  private final File directory;
  private final String fileName;
  private Gson gson;

  /**
   * Instantiates a new Gson file reader.
   *
   * @param directory the directory for the file
   * @param fileName  the name of the file
   */

  public GsonFileReader(final Logger logger, final File directory, final String fileName) {
    this.logger = logger;
    this.directory = directory;
    this.fileName = fileName;
    gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
  }

  /**
   * Read object from a file.
   *
   * @param classToDeserialize the class to deserialize
   * @param defaultValue       gets returned if object from file is null
   * @return the object from the file
   */

  public <T> T read(final Class<T> classToDeserialize, final T defaultValue) {

    final File file = new File(directory, fileName + ".json");

    if (!file.exists()) {
      sendFileNotFoundMessage();
      logger.error("Saving default file.");
      final GsonFileWriter writer = new GsonFileWriter(logger, directory, fileName);
      writer.write(defaultValue);
      return defaultValue;
    }

    final BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(file));
    } catch (final FileNotFoundException e) {
      sendFileNotFoundMessage();
      logger.error(e.getMessage());
      return defaultValue;
    }

    return gson.fromJson(reader, classToDeserialize);
  }

  private void sendFileNotFoundMessage() {
    logger.error(
        "Cannot find file '" + fileName + "' in directory '" + directory.getAbsolutePath()
            + "'.");
  }

}
