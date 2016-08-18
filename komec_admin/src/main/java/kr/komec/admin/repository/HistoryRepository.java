package kr.komec.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.komec.admin.domain.entity.History;;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>{

	List<History> findAllByOrderByYearDescMonthDesc();
}
