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
      var template = answerTemplate.format(data.userId, data.localDateTime, data.contents, data.id, data.userId);
      $(".qna-comment-slipp-articles").prepend(template);
      $("textarea[name=contents]").val("");
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
