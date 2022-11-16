package Database.service;

import Database.persistence.dao.UserDAO;
import Database.persistence.dto.UserDTO;

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
