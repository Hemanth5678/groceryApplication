package com.grocery.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grocery.dto.User;
import com.grocery.enums.AccountStatus;
import com.grocery.exception.BadRequestException;
import com.grocery.exception.BaseException;
import com.grocery.payload.response.UserLoginResponse;
import com.grocery.repository.UserRepository;
import com.grocery.security.CustomUserDetails;
import com.grocery.service.AuthService;
import com.grocery.utils.JwtTokenUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private SecurityContext securityContext;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
  public String userRegister(User user) throws BadRequestException{
      Optional<User> userOpt = userRepository.findByEmail(user.getEmail());
      if (userOpt.isPresent()) {
          throw new BadRequestException("This email already exists.");
      }
      userOpt = userRepository.findByPhone(user.getPhone());
      if (userOpt.isPresent()) {
          throw new BadRequestException("This phone number already exists.");
      }
      user.setStatus(AccountStatus.ACTIVE);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return "Registered successfully";
  }
    
    @Override
    public UserLoginResponse loginHandler(String email, String password)
            throws BadRequestException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                if (user.getStatus().equals(AccountStatus.ACTIVE)) {
                    UserLoginResponse userLoginResponse = new UserLoginResponse();
                    userLoginResponse.setAccessToken(jwtTokenUtil.generateAccessToken(user));
                    userLoginResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(user));
                    return userLoginResponse;
                }
                if (user.getStatus() == AccountStatus.INACTIVE) {
                    throw new BadRequestException("The account has not been activated.");
                }
                if (user.getStatus() == AccountStatus.BLOCK) {
                    throw new BadRequestException("This account has been blocked.");
                }
            }
        }
        throw new BadRequestException("Email or password is incorrect.");
    }

    @Override
    public User getUserLoggedIn(){
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }
       return null;
    }

}
