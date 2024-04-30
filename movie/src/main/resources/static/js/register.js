// x 를 누르면 파일 삭제 요청
// a 태그 기능 중지 => href 값 가져와서 화면 출력
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();
  console.log("x 클릭", e.target); // <i class="fa-solid fa-xmark"></i>
  console.log("x 클릭", e.currentTarget); // div id="output" 의 내용

  const currentLi = e.target.closest("li");

  // data-file 에 있는 값 가져오기
  const filePath = e.target.closest("a").dataset.file;
  console.log("filePath ", filePath); // 파일 경로

  const formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
    body: formData,
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        // 해당 div 제거
        currentLi.remove();
      }
    });
});
