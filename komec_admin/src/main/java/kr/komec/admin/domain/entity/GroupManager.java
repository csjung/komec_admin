package kr.komec.admin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.UseState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 그룹
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupManager {

	@Id
	@GeneratedValue
	long id;
	
	/** 그룹명 */
	String groupName;
	
	/** 비고  */
	String remarks;
	
	/** 사용유무 */
	UseState useState;
	
	
}
