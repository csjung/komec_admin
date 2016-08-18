package kr.komec.admin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 연혁 정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** 년도 */
	private int year;
	
	/** 월 */
	private String month;
	
	/** 내용 */
	private String contents;
	
	
}
