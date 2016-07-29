package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.Menu;;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findBySiteId(long siteId);
	
	List<Menu> findBySiteIdAndUpperIdOrderBySortAsc(long siteId, long upperId);
	
	@Query("select max(u.sort) from Menu u where u.upperId = ?1")
	Long findByMaxSort(long upperId);
	
}
