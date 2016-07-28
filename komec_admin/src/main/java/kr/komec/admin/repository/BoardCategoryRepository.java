package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.BoardCategory;;

@Repository
public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
	
	int deleteByBoardConfigId(long boardConfigId);
	
	List<BoardCategory> findByBoardConfigId(long boardConfigId);
}
