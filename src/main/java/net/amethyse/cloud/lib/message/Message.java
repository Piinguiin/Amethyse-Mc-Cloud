package net.amethyse.cloud.lib.message;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/*******************************************************
 * Copyright (C) 2015-2019 Piinguiin neuraxhd@gmail.com
 *
 * This file is part of Cloud and was created at the 29.10.2020
 *
 * Cloud can not be copied and/or distributed without the express
 * permission of the owner.
 *
 *******************************************************/
public class Message extends LinkedHashMap<String, Object> {

  private static final Type HASH_MAP_TYPE = new TypeToken<Map<String, Object>>() {
  }.getType();

  private Message() {
  }

  private Message(Map<String, Object> map) {
    super(map);
  }

  public static Message create() {
    return new Message();
  }

  public static Message create(Map map) {
    return new Message(map);
  }

  public static Message createFromJsonString(String json) {
    Message builder = new Message((Map<String, Object>) new Gson().fromJson(json, HASH_MAP_TYPE));
    return builder;
  }

  public Message set(String key, Object value) {
    put(key, value);
    return this;
  }

  public Message setIfNotNull(String key, Object value) {
    if (value == null) return this;
    return set(key, value);
  }

  public Message setIfAbsent(String key, Object value) {
    if (containsKey(key)) return this;
    return set(key, value);
  }

  public Message setIfCondition(String key, Object value, boolean condition) {
    if (!condition) return this;
    return set(key, value);
  }

  public Message setType(String type) {
    set("type", type);
    return this;
  }

  public Message setTarget(String target) {
    set("target", target);
    return this;
  }

  public Message setData(Object data) {
    set("data", data);
    return this;
  }

  public <T> T get(String key, Class<T> type) {
    return (T) get(key);
  }

  public String getType() {
    return (String) get("type");
  }

  public String getTarget() {
    return (String) get("target");
  }

  public Object getData() {
    return get("data");
  }

  public JsonObject toJsonObject() {
    return new Gson().toJsonTree(this).getAsJsonObject();
  }

  public String toJson() {
    String json = new Gson().toJson(this);
    return json;
  }

  @Override
  public String toString() {
    return toJson();
  }
}
