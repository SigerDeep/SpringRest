package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
