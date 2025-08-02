# EventPlanner - 일정 관리 애플리케이션

EventPlanner는 일정을 효율적으로 등록, 수정, 조회, 삭제할 수 있도록 돕는 일정 관리 웹 애플리케이션입니다.  
간단한 인증 구조와 CRUD 기능을 갖춘 백엔드 서비스로 구성되어 있습니다.  
사용자는 각 일정(Event)에 댓글(Comment)를 남길 수 있으며, 댓글은 일정에 종속됩니다.  
댓글 작성 시 이름과 비밀번호를 입력해야 하며, 이를 통해 수정 및 삭제가 가능합니다.

## 주요 기능
- 일정 생성(Create)
- 일정 전체 및 선택 조회(Read)
- 일정 수정(Update) — 비밀번호 인증 기반
- 일정 삭제(Delete) — 비밀번호 인증 기반
- 
- 댓글 생성(Create)
- 댓글 조회(Read)
- 댓글 수정(Update) — 비밀번호 인증 기반
- 댓글 삭제(Delete) — 비밀번호 인증 기반


## API 명세서 & ERD
- [API 명세서 보기](https://www.notion.so/240ad6b19b1a80e188fef08bd0bcdfef?v=240ad6b19b1a80aa80ef000c5adac8c7&source=copy_link)
- ![ERD](./src/images/ERD.png)