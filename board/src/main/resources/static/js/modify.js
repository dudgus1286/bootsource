const delbtn = document.querySelector(".btn-danger");
const actionForm = document.querySelector("#actionForm");

delbtn.addEventListener("click", () => {
  //   alert("test" + actionForm.querySelector("[name='gno']").value);
  if (!confirm("정말로 삭제하시겠습니까?")) return;

  actionForm.action = "/board/remove";
  actionForm.submit();
});
