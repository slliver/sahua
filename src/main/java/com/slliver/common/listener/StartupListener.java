package com.slliver.common.listener;


import com.slliver.common.Global;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/2/1 10:40
 * @version: 1.0
 */
public class StartupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {

    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        init(event);
    }

    private void init(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        Global.ctx = context.getContextPath();
        context.setAttribute("ctx", Global.ctx);
    }
}
