package coms309.login.bhall1.service;

import coms309.login.bhall1.model.User;
import coms309.login.bhall1.model.UserDto;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
}