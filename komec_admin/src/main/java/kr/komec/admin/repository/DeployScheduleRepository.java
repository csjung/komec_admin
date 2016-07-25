package kr.komec.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.DeploySchedule;;

@Repository
public interface DeployScheduleRepository extends JpaRepository<DeploySchedule, Long>{
	
}
