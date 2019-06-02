package com.shijuwenhua.signin.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;
public interface StorageService {
  void init()throws Exception;
  void store(MultipartFile file, String location)throws Exception;
  Stream<Path> loadAll()throws Exception;
  Path load(String filename)throws Exception;
  Resource loadAsResource(String filename) throws Exception;
  void deleteAll()throws Exception;
}
