package com.example.hou.config;

import com.example.hou.service.impl.AuthenticationEntryPointImpl;
import com.example.hou.util.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.hou.service.impl.AccessDeniedHandlerImpl;




/**
spring security   核心config类     控制台不再输出密码
 */


@Configuration
//@EnableWebSecurity //因为我引入了spring-boot-starter-security，所以不用@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //将authenticationManager注入容器中，再自定义登录接口中获取进行认证
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //@Autowired
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;



    //注入加密方式--后面就会使用这种方式进行对密码的对比（明文与密码的对比是否匹配）
    // 而不使用默认的密码验证
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //配置放行的规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // 关闭csrf验证(防止跨站请求伪造攻击)由于我们的资源都会收到SpringSecurity的保护，所以想要跨域访问还要让SpringSecurity运行跨域访问
                // 不通过session 获取SecurityContext(基于Token不需要session)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //开启权限拦截
                .authorizeRequests()
                // 允许登录接口匿名访问
                .antMatchers("/sysUser/login", "/sysUser/test",
                          "/book/get",
                        "/images/**",
                        "/logo/**",
                        "/email/**",
                    //    "/case/**",
                     //  "/disease/**",
                     //   "/affairnode/**",
//                        "/affair/**"
                        "/question/**",
                        "/exam/**",
                        "/websocket/**"
//                        "/**"
//                        "/examrecord/**"
                ).anonymous()
//                开发中所有可访问

                //换成.permitAll()?    //究极debug   在匿名访问的接口加token会报暂无权限错误

                .antMatchers("/**.html","/js/**","/css/**","/img/**").permitAll()  //放行静态资源
                // 其他请求都需要认证  修改
                .anyRequest().authenticated();

        //将jwtAuthenticationTokenFilter过滤器注入到UsernamePasswordAuthenticationFilter过滤器之前
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 认证授权异常自定义处理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)//自定义认证失败异常处理类
                .accessDeniedHandler(accessDeniedHandler);//自定义授权失败异常处理类


        // 禁用缓存
        http.headers().cacheControl();

        // 跨域请求配置
        http.cors();
    }



}
