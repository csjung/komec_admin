package kr.komec.admin.domain.enumeration;

public enum BoardType {
	
	COMMON("일반"), IMAGE("이미지 게시판"), CALENDAR("달력 게시판");
	private String  boardType;
	
	BoardType(String boardType) {
        this.boardType = boardType;
    }

    public String boardType() {
        return this.boardType;
    }

    public static BoardType getValue(String value) {
        for (BoardType issueState : BoardType.values()) {
            if (issueState.boardType().equals(value)) {
                return issueState;
            }
        }
        return BoardType.COMMON;
    }
	
}
