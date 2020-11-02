package net.amethyse.cloud;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import net.amethyse.cloud.lib.module.CloudModule;
import net.amethyse.cloud.lib.module.ModuleType;
import net.amethyse.cloud.lib.options.OptionParser;
import net.amethyse.cloud.lib.options.OptionSet;
import net.amethyse.cloud.master.CloudMaster;
import net.amethyse.cloud.wrapper.CloudWrapper;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 25.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class Main {

  private static OptionSet options;
  private static boolean hasParamModule;
  private static CloudModule cloudModule;
  private static final String modulePropertyName= "cloud-module";

  public static void main(String[] args) {
    ResourceLeakDetector.setLevel(Level.DISABLED);
    parseOptions(args);
    ModuleType moduleType = getModuleType();
    System.out.println("Starting Cloud version: "+getVersion());
    System.out.println("Loading CloudModule "+moduleType+"...");
    if(!hasParamModule){
      System.out.println("No Module in parameters detected. Use --module={TYPE}");
    }
    loadModule(moduleType);
  }

  private static void parseOptions(String[] args){
    OptionParser parser = new OptionParser();
    parser.addTemplate("module","m");
    options = parser.parse(args);
  }

  private static ModuleType getModuleType(){
    if(options.has("module")){

      ModuleType parsed = parseFromParameter(options.get("module").getValue());
      if(parsed != null) return parsed;
    }

    String type = System.getProperty(modulePropertyName);

    if(type != null){
      ModuleType parsed = parseFromParameter(type);
      if(parsed != null) return parsed;
    }

    System.out.println("Please select a module to start.");
    for (int i = 1; i<=ModuleType.values().length; i++) {
      System.out.println("    [" + i + "] " + ModuleType.values()[i-1]);
    }
    System.out.println("Enter number or name.");
    ModuleType moduleType;
    Scanner scanner = new Scanner(System.in);

    String read;
    while(true){
      read = scanner.nextLine().trim();
      if(read.isEmpty()){
        requestInput();
        continue;
      }
      moduleType = parseTypeFromInput(read);
      if(moduleType == ModuleType.NONE){
        System.out.println("Could not parse module type. Please try again and see above for a list of available modules.");
        continue;
      }
      return moduleType;
    }
  }

  private static void requestInput(){
    System.out.print("> ");
  }

  private static ModuleType parseFromParameter(String param){

    try{

      ModuleType type = ModuleType.valueOf(param.toUpperCase());
      hasParamModule = true;
      return type;
    }catch (IllegalArgumentException e){
      System.err.println("Invalid module type: "+param);
      System.err.println("Available modules: "+getAvailableModules());
      return null;
    }

  }

  private static ModuleType parseTypeFromInput(String input){
    try {
      return ModuleType.values()[Integer.parseInt(input)-1];
    } catch (Exception ignored) {}
    try {
      return ModuleType.valueOf(input.toUpperCase());
    } catch (Exception e) {
      return ModuleType.NONE;
    }
  }

  private static void loadModule(ModuleType moduleType){

    switch (moduleType){

      case MASTER:
        cloudModule = new CloudMaster();
        break;

      case WRAPPER:
        cloudModule = new CloudWrapper();
        break;

    }

    try{

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try{
          cloudModule.unload();
        }catch (Exception e){
          System.out.println("Error while unloading module "+moduleType.name()+":");
          e.printStackTrace();
        }
      }));
      cloudModule.load(options);
    }catch (Exception e){
      System.out.println("Error while loading module "+moduleType.name()+":");
      e.printStackTrace();
    }

  }

  private static String getAvailableModules(){
    return Arrays.stream(ModuleType.values()).map(ModuleType::name).collect(Collectors.joining(", "));
  }

  private static String getVersion(){
    return Main.class.getPackage().getImplementationVersion();
  }

}
