package com.loto.lte.web.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExistsFileUtil {
    public static boolean exists(String resourcePath) {
        Path firstPath = Paths.get(resourcePath);
        return Files.exists(firstPath);
    }
}
