package com.project.mychat.models.data;

import com.project.mychat.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersDao extends CrudRepository<User, String>
{
    User findUserByUsername(String username);
}
