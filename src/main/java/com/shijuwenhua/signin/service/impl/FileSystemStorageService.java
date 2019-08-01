package com.shijuwenhua.signin.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shijuwenhua.signin.service.StorageService;

@Service
public class FileSystemStorageService implements StorageService {
	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService() {
		this.rootLocation = Paths.get("/usr/java/tomcat/apache-tomcat-8.5.16/webapps/pic/");
//		this.rootLocation = Paths.get("D:\\");
	}

	@Override
	public void init() throws Exception {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new Exception("Could not initialize storage", e);
		}
	}

	@Override
	public void store(MultipartFile file, String storeLocation) throws Exception {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new Exception("Failed to store empty file" + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new Exception(
						"Cannot store file with relative path outside current directory " + filename);
			}
			Path fileLocation = Paths.get(storeLocation);
			Files.copy(file.getInputStream(), fileLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new Exception("Failed to store file" + filename, e);
		}
	}

	@Override
	public Stream<Path> loadAll() throws Exception {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new Exception("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) throws Exception {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new Exception("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new Exception("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}
