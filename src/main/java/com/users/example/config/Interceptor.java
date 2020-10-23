package com.users.example.config;

import com.users.example.enums.HttpStatusCodes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception {
        String regex = "[+-]?[0-9]+";

        String[] inputs = requestServlet.getRequestURI().split("/");
        String pathParam = inputs[inputs.length - 1];

        // compiling regex
        Pattern p = Pattern.compile(regex);

        // Creates a matcher that will match input1 against regex
        Matcher m = p.matcher(pathParam);

        // If match found then conitnue to handle request
        if (m.find() && m.group().equals(pathParam)) {
            return true;
        } else {
            responseServlet.sendError(HttpStatusCodes.BadRequest.getStatusCode(), "PathParam is not numeric.");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
        //do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
        throws Exception {
        //do nothing
    }
}