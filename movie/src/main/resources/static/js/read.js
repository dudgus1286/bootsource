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
