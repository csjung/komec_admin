package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.komec.admin.domain.entity.BoardData;

public interface BoardDataRepository extends JpaRepository<BoardData, Long>{
	
	List<BoardData> findByBoardConfigIdOrderByIdDesc(long boardConfigId);
	List<BoardData> findByBoardConfigIdOrderByIdDesc(long boardConfigId , Pageable pageable);
	List<BoardData> findByIdNotNullOrderByIdDesc(Pageable pageable);
}
