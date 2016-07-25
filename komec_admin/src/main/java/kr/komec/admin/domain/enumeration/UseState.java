package kr.komec.admin.domain.enumeration;

public enum UseState {
	
	USE("사용"), NOTUSE("사용안함");
	private String  useState;
	
	UseState(String useState) {
        this.useState = useState;
    }

    public String useState() {
        return this.useState;
    }

    public static UseState getValue(String value) {
        for (UseState issueState : UseState.values()) {
            if (issueState.useState().equals(value)) {
                return issueState;
            }
        }
        return UseState.USE;
    }
	
}
