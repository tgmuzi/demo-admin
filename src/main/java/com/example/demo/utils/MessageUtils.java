package com.example.demo.utils;

import com.example.demo.modules.quartz.utils.SpringContextUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public class MessageUtils {
    private static MessageSource messageSource;

    static {
        messageSource = (MessageSource) SpringContextUtils.getBean("messageSource");
    }

    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }


    /**
     * telegram api 国际化
     */
    public static String getTelegramMessage(int code, String localeStr) {
        return getTelegramMessage(code, localeStr, new String[0]);
    }

    /**
     * telegram api 国际化
     */
    public static String getTelegramMessage(int code, String localeStr, String... params) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        return messageSource.getMessage(code + "", params, locale);
    }
}
