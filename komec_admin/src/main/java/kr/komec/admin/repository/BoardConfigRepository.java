package kr.komec.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.BoardConfig;;

@Repository
public interface BoardConfigRepository extends JpaRepository<BoardConfig, Long>{
	
}
