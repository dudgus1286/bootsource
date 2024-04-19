// 페이지 로드 시 무조건 실행하게 만들기
// 즉시실행 함수
// fetch() - 함수 작성 후 호출

// 함수 작성
// 1. function method1() {}
// 2. const(또는 let) method1 = ()=> {}

// 함수 실행 => 호출
// method1();

// 호이스팅(선언 안 하고 먼저 호출 후 선언)
// 1번은 호이스팅 됨, 2번 안 됨
// var 로 선언한 변수는 호이스팅 가능, const/let 은 안 됨

// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 댓글 목록 가져오기
// 댓글목록 보여줄 영역 가져오기
const replyList = document.querySelector(".replyList");

const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 댓글 총 수 확인
      document.querySelector("#replyCount").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div><div>`;
        result += `<span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(reply.regDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 새 댓글 등록
const replyForm = document.querySelector("#replyForm");
replyForm.addEventListener("submit", (e) => {
  // 새 댓글 등록폼 전송 시 이벤트 중지
  e.preventDefault();

  const replyer = replyForm.querySelector("#replyer");
  const text = replyForm.querySelector("#reply");

  // replyForm 에서 입력할 값 가져오기
  const reply = {
    bno: bno,
    replyer: replyer.value,
    text: text.value,
  };
  console.log(reply);

  // 등록 시도
  fetch(`/replies/new`, {
    method: "post",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(reply),
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        alert(data + " 번 댓글 등록 성공");

        // replyForm 내용 제거
        replyer.value = "";
        text.value = "";

        replyLoaded();
        // location.href = `/board/read?bno=${bno}&page=${requestDto.page}&type=${requestDto.type}&keyword=${requestDto.keyword}`;
      }
    });
});

// 댓글 삭제/수정 버튼 클릭 시 이벤트 전파로 찾아오기
// 이벤트 전파: 자식 요소에 일어난 이벤트는 상위 요소로 전달됨
// rno 가져오기
replyList.addEventListener("click", (e) => {
  // 실제 이벤트가 일어난 대상 == e.target
  const btn = e.target;

  // 사용자가 클릭한 요소가 삭제 버튼인지 요소가 포함한 클래스 요소로 확인
  // const btnClass = btn.classList; // - 클릭한 요소의 클래스 리스트 출력
  // console.log(btnClass);
  // if (!btnClass.contains("btn-outline-danger")) {
  // // - 클래스 리스트에 삭제버튼만의 리스트가 있는지 체크
  //   alert("버튼을 누르시오");
  //   return; // 삭제버튼이 아닌 경우 알림창 띄운 후 리턴
  // }

  // closest("요소") : 제일 가까운 상위요소 찾기
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log("rno : ", rno);

  fetch(`/replies/${rno}`, {
    method: "delete",
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("댓글 삭제 성공");
        replyLoaded();
      }
    });
});
