//package pl.sidor.UserAuth.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//   @Qualifier(value = "data")
//   private DataSource dataSource;
//
//   @Autowired
//   private PasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//       auth.inMemoryAuthentication()
//               .withUser("admin").password("{noop}admin").roles("ADMIN","USER")
//               .and()
//               .withUser("Karol").password("{noop}karol123").roles("USER");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .antMatchers("/resources/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/h2/**").permitAll()
////                .antMatchers(HttpMethod.POST, "/login").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    private void jdbcAuth(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().usersByUsernameQuery("select username,password, enabled from users where username=?")
//                .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
//                .dataSource(dataSource).passwordEncoder(passwordEncoder);
//    }
//}
