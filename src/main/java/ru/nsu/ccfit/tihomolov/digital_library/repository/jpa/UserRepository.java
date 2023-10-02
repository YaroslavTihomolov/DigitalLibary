package ru.nsu.ccfit.tihomolov.digital_library.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
}
