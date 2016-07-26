package kr.komec.admin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.MenuType;
import kr.komec.admin.domain.enumeration.UseState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사이트 메뉴
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {

	@Id
	@GeneratedValue
	private long id;
	
	/** 메뉴명 */
	private String name;
	
	/**메뉴 영문명  */
	private String nameEn;
	
	/** 출력순서 */
	private long sort;
	
	/** 상위 ID */
	private long upperId;
	
	/** 콘텐츠 ID */
	@Column(nullable = true)
	private Long contentsId;
	
	/** 프로그램 URL */
	private String programUrl;
	
	/** 게시판 ID */
	@Column(nullable = true)
	private Long boardId;
	
	/** 메뉴타입 */
	private MenuType menuType;
	
	/** 사용유무 */
	private UseState useState;
	
	/** 사이트 ID */
	private long siteId;
	
	@OneToMany(mappedBy = "upperId", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@OrderBy("sort")
	@Column(nullable = true)
	private List<Menu> menus;
	
}
