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

      let totalReview = data.length;
      let result = ``;
      data.forEach((obj) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno="1">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold">${obj.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${obj.nickname}</span>`;
        result += `<span class="grade">평점 : ${obj.grade}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(obj.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();

// 작은 포스트 클릭하면 클 포스터 보여주기
const imgModal = document.getElementById("imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // 모달을 뜨게 만든 li 가져오기
    const posterLi = e.relatedTarget;

    // data- 값 가져오기 : dataset, getAttribute
    const file = posterLi.getAttribute("data-file");
    console.log("file ", file);

    // 타이틀 영역 영화명 삽입
    imgModal.querySelector(".modal-title").textContent = `${title}`;

    // 이미지 경로 변경
    const modalBody = imgModal.querySelector(".modal-body");
    modalBody.innerHTML = `<img src = "/upload/display?fileName=${file}" style = "whidth:100%">`;
  });
}
