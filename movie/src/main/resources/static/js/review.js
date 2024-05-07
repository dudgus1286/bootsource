// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// /reviews/3/all 요청 처리
const reviewsLoaded = () => {
  fetch(`/reviews/${mno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 리뷰 하나도 안 달린 영화에 새 리뷰가 달린 경우 리뷰 목록 영역 표시하기
      if (data.length > 0) {
        reviewList.classList.remove("hidden");
      }

      // 리뷰 총 개수 변경
      let totalReview = data.length;
      let result = ``;
      data.forEach((obj) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno="${obj.reviewNo}">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold">${obj.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${obj.nickname}</span>`;
        result += `<span class="grade">평점 : ${obj.grade}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(obj.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        // 작성자 == 로그인
        if (`${obj.email}` == user) {
          result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
          result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        }
        result += `</div></div>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();

// 리뷰 등록 or 수정
// 리뷰 폼 submit 중지
const reviewForm = document.querySelector(".review-form");
reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();

  // text, grade, mid, mno
  const text = reviewForm.querySelector("#text");
  const mid = reviewForm.querySelector("#mid");
  const nickname = reviewForm.querySelector("#nickname");
  const email = reviewForm.querySelector("#email");
  // 수정이라면 reviewNo 가 존재함
  const reviewNo = reviewForm.querySelector("#reviewNo");

  const body = {
    mno: mno,
    text: text.value,
    email: email.value,
    grade: grade || 0,
    mid: mid.value,
    reviewNo: reviewNo.value,
  };

  if (!reviewNo.value) {
    // 새 review 등록
    fetch(`/reviews/${mno}`, {
      method: "post",
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        text.value = "";
        reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click;

        console.log(data);
        if (data) {
          alert(data + "번 리뷰 등록 성공");
        }

        reviewsLoaded(); // 댓글 리스트 다시 가져오기
      });
  } else {
    // 수정
    fetch(`/reviews/${mno}/${reviewNo.value}`, {
      method: "put",
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(body),
    })
      .then((response) => response.text())
      .then((data) => {
        text.value = "";
        reviewNo.value = "";
        reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click;

        console.log(data);
        if (data) alert(data + "번 리뷰 수정 성공");

        reviewsLoaded(); // 댓글 리스트 다시 가져오기
      });
  }
});

// 삭제 클릭 시 reviewNo 가져오기
// fetch() 작성
reviewList.addEventListener("click", (e) => {
  e.preventDefault();

  // 부모 요소가 이벤트 감지하는 형태 => 실제 이벤트 대상요소 찾기
  const target = e.target;

  // 리뷰 댓글 번호 가져오기
  const reviewNo = target.closest(".review-row").dataset.rno;
  // 컨트롤러에서 작성자와 로그인 유저가 같은지 다시 한 번 비교하기 위해 이메일 갖고오기
  const email = reviewForm.querySelector("#email");

  if (target.classList.contains("btn-outline-danger")) {
    if (!confirm("리뷰를 정말로 삭제하시겠습니까?")) return;

    const form = new FormData();
    form.append("email", email.value);

    fetch(`/reviews/${mno}/${reviewNo}`, {
      method: "delete",
      headers: {
        "X-CSRF-TOKEN": csrfValue,
      },
      body: form,
    })
      .then((response) => response.text())
      .then((data) => {
        alert(data + " 번 리뷰가 삭제되었습니다.");
        reviewsLoaded();
      });
  } else if (target.classList.contains("btn-outline-success")) {
    // 도착한 데이터 리뷰 폼에 보여주기
    fetch(`/reviews/${mno}/${reviewNo}`, {
      method: "get",
    })
      .then((response) => response.json())
      .then((data) => {
        reviewForm.querySelector("#reviewNo").value = data.reviewNo;
        reviewForm.querySelector("#mid").value = data.mid;
        reviewForm.querySelector("#nickname").value = data.nickname;
        reviewForm.querySelector("#text").value = data.text;
        reviewForm.querySelector("#email").value = data.email;
        // 이벤트 click 을 직접 호출
        reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
        reviewForm.querySelector("button").innerHTML = "리뷰 수정";
      });
  }
});
