package com.example.restaurant.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

class CommandUtility {

    static Locale getSessionLocale(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("lang");
        return locale != null ? Locale.forLanguageTag(locale) : Locale.getDefault();
    }
}
