package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.komec.admin.domain.entity.BoardFiles;

public interface BoardFilesRepository extends JpaRepository<BoardFiles, Long>{
	List<BoardFiles> findByBoardDataId(long boardDataId);
}
