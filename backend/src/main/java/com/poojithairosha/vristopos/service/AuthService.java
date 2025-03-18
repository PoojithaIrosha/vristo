package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.AuthRequest;
import com.poojithairosha.vristopos.dto.AuthResponse;
import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.ResetPasswordRequest;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest);

    ClientResponse forgotPassword(String email);

    ClientResponse resetPassword(ResetPasswordRequest request);

}
