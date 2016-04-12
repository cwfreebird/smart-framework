package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by david.cai on 2016/4/12.
 */
public class CodecUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source){
        String target;

        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URLEncode failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    public static String decodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("URLDecoder failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
