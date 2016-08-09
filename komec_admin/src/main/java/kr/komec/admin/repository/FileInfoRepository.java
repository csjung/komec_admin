package kr.komec.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.FileInfo;;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long>{
	
}
