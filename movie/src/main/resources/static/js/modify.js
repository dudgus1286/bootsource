// 이미지 X 버튼 클릭 시
// 이미지 li 만 제거
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  // li 태그 가져오기
  const currentLi = e.target.closest("li");

  if (confirm("정말로 삭제하시겠습니까?")) {
    currentLi.remove();
  }
});

// 삭제 버튼 클릭
const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  // actionForm 보내기
  if (!confirm("삭제하시겠습니까?")) {
    return;
  }
  actionForm.submit();
});
