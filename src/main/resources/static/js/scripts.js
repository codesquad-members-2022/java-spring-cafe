$('.submit-write').children('input').click(addAnswer);

function addAnswer(e) {
  e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

  var queryString = $(".submit-write").serialize(); //form data들을 자동으로 묶어준다.
  console.log("query : " + queryString);

  var url = $(".submit-write").attr("action");
  console.log("action : " + url);

  $.ajax({
    type: 'post',
    url: "/api" + url,
    data: queryString,
    dataType: 'json',
    error: function () {
      console.log('failure');
    },
    success: function (data, status) {
      console.log(data);
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(data.userId, data.localDateTime, data.contents, data.id, data.userId, data.parentArticleId);
      $(".qna-comment-slipp-articles").prepend(template);
      $("textarea[name=contents]").val("");
      //$(".qna-comment-slipp").load(window.location.href + " .qna-comment-slipp");
    }
  });
}

//$(document).on('click', '.link-delete-article', deleteAnswer)
//$('.link-delete-article').children('input').click(deleteAnswer);
$(".qna-comment-slipp-articles").on("click", ".link-delete-article button[type='submit']", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  const url = deleteBtn.closest(".link-delete-article").attr("action"); // 값이 왜 바뀌지? var -> const로 변경
  //var url = $(this).attr("action");
  console.log("url : " + url);

  $.ajax({
    type: 'delete',
    url: url,
    dataType: 'json',
    error: function (xhr, status) {
      console.log("error");
    },
    success: function (data, status) {
      console.log(data);
      if (data.valid) {
        deleteBtn.closest("article").remove();
      } else {
        alert(data.errorMessage);
      }
    }
  });
}

String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
