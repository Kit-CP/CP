package service;

import persistence.dao.UserDAO;
import persistence.dto.UserDTO;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<UserDTO> showAll() {
        return userDAO.showAll();
    }
}
