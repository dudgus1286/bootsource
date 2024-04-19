// 제목 클릭 시 a 태그 기능 중지
// href 값
document.querySelector("tbody").addEventListener("click", (e) => {
  e.preventDefault();

  const target = e.target;

  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((resonse) => resonse.json())
    .then((data) => {
      console.log(data);

      // 디자인 영역 가져오기
      document.querySelector("#category").value = data.categoryName;
      document.querySelector("#title").value = data.title;
      document.querySelector("#publisher").value = data.publisherName;
      document.querySelector("#writer").value = data.writer;
      document.querySelector("#price").value = data.price;
      document.querySelector("#salePrice").value = data.salePrice;
      document.querySelector("#createDate").value = data.createdDate;
      document.querySelector("#book_id").value = data.id;
    });
});

document.querySelector("#deleteBtn").addEventListener("click", (e) => {
  e.preventDefault();

  // 삭제 버튼 클릭시 id 가져오기
  const bookId = document.querySelector("#book_id");

  fetch(`/delete/${bookId.value}`, {
    method: "delete",
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("삭제 성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});

document.querySelector("#modifyBtn").addEventListener("click", (e) => {
  // a 태그 기능 중지
  e.preventDefault();

  // from 안의 내용 가져오기 => javascript 객체 생성
  const myForm = document.querySelector("#myForm");

  // myForm 안에 있는 요소 찾기
  const bookId = myForm.querySelector("#book_id").value;

  const data = {
    id: bookId,
    price: myForm.querySelector("#price").value,
    salePrice: myForm.querySelector("#salePrice").value,
  };

  console.log(data);

  // fetch() : 메소드 지정 안 하면 get 으로 전송됨
  fetch(`/modify/${bookId}`, {
    method: "put",
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(data), // JSON.stringify() : 자바스크립트 객체를 json 타입으로 변환
  })
    .then((response) => response.text()) // 데이터 입력 성공할 경우 "success" 문자로 반환하기 때문에 텍스트로 받음
    .then((data) => {
      if (data == "success") {
        alert("수정 성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
