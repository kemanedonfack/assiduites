package com.uic.assiduite.repository;

import com.uic.assiduite.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author engome
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

}
