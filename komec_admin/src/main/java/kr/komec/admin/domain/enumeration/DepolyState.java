package kr.komec.admin.domain.enumeration;

/**
 * 배포 상태 정보
 * @author csjung
 *
 */
public enum DepolyState {
	
	COMPLETE("완료"), WAITING("대기");
	private String  depolyState;
	
	DepolyState(String depolyState) {
        this.depolyState = depolyState;
    }

    public String depolyState() {
        return this.depolyState;
    }

    public static DepolyState getValue(String value) {
        for (DepolyState issueState : DepolyState.values()) {
            if (issueState.depolyState().equals(value)) {
                return issueState;
            }
        }
        return DepolyState.COMPLETE;
    }
	
}
