// form submit 기능 중지
// uploadResult ul li 태그 요소 가져오기
const registerForm = document.querySelector("#register-form");
registerForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  // 첨부파일 정보 수집
  const attachInfos = document.querySelectorAll(".uploadResult ul li");
  console.log(attachInfos);

  // 수집된 정보를 폼 태그 자식으로 붙여넣기
  let result = "";
  attachInfos.forEach((obj, idx) => {
    // hidden 3 개 => movieImageDto 객체 하나로 변경
    result += `<input type='hidden' value ='${obj.dataset.path}' name = 'movieImageDtos[${idx}].path'>`;
    result += `<input type='hidden' value ='${obj.dataset.uuid}' name = 'movieImageDtos[${idx}].uuid'>`;
    result += `<input type='hidden' value ='${obj.dataset.name}' name = 'movieImageDtos[${idx}].imgName'>`;
  });
  form.insertAdjacentHTML("beforeend", result);
  console.log(e.target.innerHTML);

  form.submit();
});

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
