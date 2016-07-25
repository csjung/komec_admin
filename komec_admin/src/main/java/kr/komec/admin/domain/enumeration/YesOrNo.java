package kr.komec.admin.domain.enumeration;

public enum YesOrNo {
	
	YES("예"), NO("아니요");
	private String  yesOrNo;
	
	YesOrNo(String yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public String yesOrNo() {
        return this.yesOrNo;
    }

    public static YesOrNo getValue(String value) {
        for (YesOrNo issueState : YesOrNo.values()) {
            if (issueState.yesOrNo().equals(value)) {
                return issueState;
            }
        }
        return YesOrNo.YES;
    }
	
}
