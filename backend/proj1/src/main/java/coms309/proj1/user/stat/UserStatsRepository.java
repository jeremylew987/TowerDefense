package coms309.proj1.user.stat;

import coms309.proj1.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatsRepository extends JpaRepository<UserStats, Long>
{
	Optional<UserStats> findFirstByLevel(int level);

	Optional<UserStats> findById(Long id);

}
