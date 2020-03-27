function postComment() {
    var questionId = $("#question_id").val();
    var commentArea = $("#comment_area").val();
    if(!commentArea){
        alert("不能回复空")
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentArea,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200){
                // $("#comment_section").hide();
                window.location.reload();
            }else {
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=9102467aec7cad18d182&redirect_uri=http://localhost:8080/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }else {

                    }
                }
            }
            console.log(response)
        },
        dataType: "json"
    });
}

