03.04(월)
2. 커뮤니티 UI변경
3. 합치기
ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
03.06(수)
1. 로그인, 로그아웃 오류수정
2. 카카오로 로그인시 커뮤니티 게시글, 댓글 작성가능하게
3. 글쓴이만 디테일페이지 들어가면 수정,삭제하기 버튼보이게
ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
sqld
https://yunamom.tistory.com/370
PIVOT 절과 UNPIVOT 절


cs지식하루에 5개씩
*테이블명, 컬럼명 다 대문자인지 확인

*** 카카오로 로그인시 csrf토큰 필요함, 모든 CRUD에 인풋 맨위에 써서 csrf토큰 보내기 
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
