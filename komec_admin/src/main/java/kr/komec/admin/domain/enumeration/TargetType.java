package kr.komec.admin.domain.enumeration;

public enum TargetType {
	
	_self("_self"), _parent("_parent"), _top("_top"), _blank("_blank");
	private String  targetType;
	
	TargetType(String targetType) {
        this.targetType = targetType;
    }

    public String targetType() {
        return this.targetType;
    }

    public static TargetType getValue(String value) {
        for (TargetType issueState : TargetType.values()) {
            if (issueState.targetType().equals(value)) {
                return issueState;
            }
        }
        return TargetType._blank;
    }
	
}
