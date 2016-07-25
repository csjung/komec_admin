package kr.komec.admin.domain.enumeration;

public enum MenuType {
	
	CONTENT("콘텐츠"), BOARD("게시판"), PROGRAM("프로그램");
	private String  menuType;
	
	MenuType(String menuType) {
        this.menuType = menuType;
    }

    public String menuType() {
        return this.menuType;
    }

    public static MenuType getValue(String value) {
        for (MenuType issueState : MenuType.values()) {
            if (issueState.menuType().equals(value)) {
                return issueState;
            }
        }
        return MenuType.CONTENT;
    }
	
}
