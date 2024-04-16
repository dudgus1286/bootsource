const searchForm = document.querySelector("#searchForm");
searchForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const type = document.querySelector("#type");
  const keyword = document.querySelector("#keyword");

  if (!type.value) {
    alert("검색 조건을 확인해 주세요");
    type.focus();
    return;
  }
  if (!keyword.value) {
    alert("검색어를 확인해 주세요");
    keyword.focus();
    return;
  }

  e.target.submit();
});
