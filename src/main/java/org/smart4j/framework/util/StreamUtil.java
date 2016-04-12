package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by david.cai on 2016/4/12.
 */
public class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream in){
        StringBuilder body = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        try {
            while ((line = reader.readLine()) != null){
                body.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("read body from inputstream failure", e);
            throw new RuntimeException(e);
        }
        return body.toString();
    }
}
