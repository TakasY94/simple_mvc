package org.example.app.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {
    private Logger logger = Logger.getLogger(FileService.class);

    public Set<String> getListFilesForDownload(String dir) {
        logger.info("Getting list of files for download");
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

}
