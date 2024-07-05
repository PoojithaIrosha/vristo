package com.poojithairosha.vristopos.service;

import com.poojithairosha.vristopos.dto.ClientResponse;
import com.poojithairosha.vristopos.dto.UserDTO;
import com.poojithairosha.vristopos.model.user.User;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDTO> getAllUsers(int page, int size);

    ClientResponse updateUserStatus(String userId);

    UserDTO getUserById(String userId);

    Page<UserDTO> searchUsers(int page, int size, String text);

    ClientResponse updateUserDetails(UserDTO userDTO);

    ClientResponse registerUser(User user);

}
