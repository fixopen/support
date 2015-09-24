package com.baremind;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

/**
 * Created by gaolianli on 2015/9/6.
 */
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        //服务类所在的包路径
        packages("com.baremind");
        //打印访问日志，便于跟踪调试，正式发布可清除
        register(LoggingFilter.class);

        // MVC.
        register(JspMvcFeature.class);
    }
}
