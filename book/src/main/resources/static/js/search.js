const type = document.querySelector("#type");
const keyword = document.querySelector("#keyword");

const searchform = document.querySelector("form#searchForm");

searchform.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log(type.value);
  console.log(keyword.value);

  if (!type.value) {
    alert("검색 타입을 확인해 주세요");
    type.focus();
    return;
  } else if (!keyword.value) {
    alert("검색어를 확인해 주세요");
    keyword.focus();
    return;
  }
  e.target.submit();
});
