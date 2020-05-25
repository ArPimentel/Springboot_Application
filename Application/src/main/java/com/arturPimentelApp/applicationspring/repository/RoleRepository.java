package com.arturPimentelApp.applicationspring.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.arturPimentelApp.applicationspring.Roles;

@Repository
public interface RoleRepository extends CrudRepository <Roles, Long>{


}
