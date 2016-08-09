package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.Banner;;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long>{
	
	List<Banner> findByBannerZoneIdOrderBySortAsc(long bannerZoneId);
	
	@Query("select max(u.sort) from Banner u where u.bannerZoneId = ?1")
	Long findByMaxSort(long bannerZoneId);
}
