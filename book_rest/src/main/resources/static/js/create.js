document.querySelector("#createForm").addEventListener("submit", (e) => {
  // form submit 기능 중지
  e.preventDefault();

  // from 안의 내용 가져오기 => javascript 객체 생성
  const data = {
    categoryName: e.target.querySelector("#categoryName").value,
    title: e.target.querySelector("#title").value,
    publisherName: e.target.querySelector("#publisherName").value,
    writer: e.target.querySelector("#writer").value,
    price: e.target.querySelector("#price").value,
    salePrice: e.target.querySelector("#salePrice").value,
  };

  console.log(data);

  // fetch() : 메소드 지정 안 하면 get 으로 전송됨
  fetch(`http://localhost:8080/book/new`, {
    method: "post",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(data), // JSON.stringify() : 자바스크립트 객체를 json 타입으로 변환
  })
    .then((response) => response.text()) // 데이터 입력 성공할 경우 "success" 문자로 반환하기 때문
    .then((data) => {
      if (data == "success") {
        alert("입력성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
