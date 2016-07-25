package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.ContentHist;

@Repository
public interface ContentHistRepository extends JpaRepository<ContentHist, Long>{

	List<ContentHist> findByContentsIdOrderByIdDesc(long contentsId);	
}
