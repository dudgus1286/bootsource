// 제목 클릭 시 a 태그 기능 중지
// href 값
document.querySelector("tbody").addEventListener("click", (e) => {
  e.preventDefault();

  const target = e.target;

  console.log(target.dataset.id);

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
