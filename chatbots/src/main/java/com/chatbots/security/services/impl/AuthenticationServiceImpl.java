package com.chatbots.security.services.impl;

import com.chatbots.security.SecurityExceptionsHandlers.exceptions.ResourceNotFoundException;
import com.chatbots.security.models.dto.UserLoginRequestDTO;
import com.chatbots.security.models.dto.UserResponseDTO;
import com.chatbots.security.models.entities.RefreshToken;
import com.chatbots.security.models.entities.User;
import com.chatbots.security.models.enums.RegisterProvider;
import com.chatbots.security.models.enums.UserRole;
import com.chatbots.security.repositories.UserRepository;
import com.chatbots.security.services.AuthenticationService;
import com.chatbots.security.services.JwtService;
import com.chatbots.security.services.RefreshTokenService;
import com.chatbots.security.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final RoleService roleService;

    @Override
    public UserResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           userLoginRequestDTO.getEmail(),
                           userLoginRequestDTO.getPassword()
                   )
           );
         }catch (Exception e){
              throw new BadCredentialsException(e.getMessage());
         }
        User user = userRepository.findByEmail(userLoginRequestDTO.getEmail())
                .orElseThrow(()-> new ResourceAccessException("Email Not found "));
        String jwtToken  = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return UserResponseDTO.fromUser(user,jwtToken,refreshToken.getToken());

    }
    @Override
    public UserResponseDTO register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
              throw new BadCredentialsException("Email already exist");
        }
        user.setRole(roleService.getRoleByName(UserRole.USER));
        user.setProvider(RegisterProvider.EMAIL);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user =  userRepository.save(user);
        String jwtToken  = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        return UserResponseDTO.fromUser(user,jwtToken,refreshToken.getToken());
    }
    @Override
    public void  logout(String token){
       refreshTokenService.revokeRefreshToken(token);
    }
}
