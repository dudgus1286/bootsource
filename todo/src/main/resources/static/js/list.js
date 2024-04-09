// 화면의 중복 요소에 이벤트 작성

// 이벤트는 전파됨 => 부모 요소가 감지함
document.querySelector(".list-group").addEventListener("click", (e) => {
  console.log("이벤트가 발생한 대상 " + e.target);
  console.log("이벤트가 발생한 대상 value " + e.target.value);
  console.log("이벤트를 감지한 대상 " + e.currentTarget);

  const form = document.querySelector("#completedForm");
  form.querySelector("[name='id']").value = e.target.value;
  form.submit();
});
