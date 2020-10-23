package com.users.example.e2e;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (resultStringBuilder.length() > 0) {
                    resultStringBuilder.append("\n");
                }
                resultStringBuilder.append(line);
            }
        }

        return resultStringBuilder.toString();
    }

    public static String readFileByClasspath(String file) throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        return readFromInputStream(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
    }
}
