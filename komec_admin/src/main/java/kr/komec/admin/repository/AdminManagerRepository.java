package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.AdminManager;;

@Repository
public interface AdminManagerRepository extends JpaRepository<AdminManager, Long>{
	public List<AdminManager> findByUserId(String userid);
	
}
