package ec.gob.comercial.shared.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementación temporal de UserDetailsService
 * 
 * TODO: Implementar con base de datos real cuando se cree el módulo de usuarios
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usuario temporal para testing
        // TODO: Buscar en base de datos
        if ("admin@municipio.gob.ec".equals(username)) {
            return User.builder()
                .username("admin@municipio.gob.ec")
                .password("$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6") // password: admin123
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
        }
        
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
