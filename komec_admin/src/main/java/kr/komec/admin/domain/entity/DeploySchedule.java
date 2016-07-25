package kr.komec.admin.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.DepolyState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 콘텐츠 배포 정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeploySchedule {

	@Id
	@GeneratedValue
	private long id;
	
	/** 배포 예약일 */
	private Date deployDate;
	
	/** 배포 예약 시 */
	private int deployHour;
	
	/** 배포 완료일 */
	private Date completeDate;
	
	/** 배포상태 */
	private DepolyState depolyState;
	
	/** 콘텐츠 이력 ID */
	private long contentHistId;
	
}
