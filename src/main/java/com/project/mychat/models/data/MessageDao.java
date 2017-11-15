package com.project.mychat.models.data;

import com.project.mychat.models.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;

@Repository
@Transactional
public interface MessageDao extends CrudRepository<Message, Integer>
{
    Iterable<Message> findAllByDateOrderByTimeDesc(Date date);
}
