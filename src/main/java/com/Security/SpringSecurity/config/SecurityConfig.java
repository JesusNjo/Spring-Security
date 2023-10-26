package com.Security.SpringSecurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Firts configuration
 /*
    @Bean
   public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                //.csrf().disable() CSRF (CROSS SIDE RESQUEST FORGERY) Deshabilitar la protección CSRF (formularios)
                .authorizeHttpRequests()//Para configurar las URL protegidas y las que nó
                    .requestMatchers("/security/v2").permitAll() //Para agregar los endpoints SIN seguridad
                    .anyRequest().authenticated()//Para cualquier otro debe estar autenticado
                .and() //Para agregar mas seguridad
                .formLogin().permitAll() //Para el formulario de autenticación para todos
                .and()
                .build();
    }*/

    //Second configuration
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/security/v2").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin().permitAll()
                .and()
                .build();
    }*/

    //Build complete configuration with comments about this topic

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //.csrf().disable() CSRF (CROSS SIDE RESQUEST FORGERY) Deshabilitar la protección CSRF (formularios)
                .authorizeHttpRequests(auth->{ //Para configurar las URL protegidas y las que nó
                    auth.requestMatchers("/security/v2").permitAll(); //Para agregar los endpoints SIN seguridad
                    auth.anyRequest().authenticated(); //Para indicar que cualquier otro debe estar autenticado
                })
                .formLogin() //Para el formulario de autenticación para todos
                    .successHandler(successHandler()) // URL para la redirección indicada en la funcion de abajo
                    .permitAll() //Para que todas las paginas bloqueadas tengas el formulario
                .and()
                .sessionManagement() // Para configurar el comportamiento de las sessiones
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) //ALWAYS - IF_REQUIRED - NEVER - STATELESS
                        //Always: Va a crear una sesión siempre que no haya una creada, si existe una la va a reutilizar
                        //IF_REQUIRED: Crea una nueva sesion solo si es necesario, si no es existe crea una
                        //NEVER: No crea ninguna sesion, utiliza una si ya esta creada sino no hace nada
                        //STATELESS: No crea ninguna sesion, todas las solicitudes las trabaja independendiente y no guarda nada
                    .invalidSessionUrl("https://google.es") //Si la sesion es invalida (no autentica) se redirige a la direccion indicada
                    .maximumSessions(1)// Cantidad maxima de sesiones abiertas por usuario
                    .expiredUrl("/login") //Sirve para que al expirar la sesion de usuario lo redirija a la ubicacion indicada
                    .sessionRegistry(sessionRegistry()) //Este se encarga de administrar el registro de los usuarios
                .and()
                .sessionFixation() //Sirve para evitar que un usuario vulnere la seguridad y usar su sesion de forma indefinida
                    .migrateSession() // Sirve para cuando la aplicación sea atacada cambiar el ID de sesion
                    //.newSession() // Crea una nueva sesion cuando sea atacada la app
                    //.none() // Inavilita la fixation
                .and()
                .httpBasic()// Es una autenticación basica, se envia el usuario y contraseña en el header. Pruebas en postman
                .and()
                .build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
           response.sendRedirect("/security/session");
        });
    }





}
