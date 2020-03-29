/**
 * 评论封装方法
 */
function commentTarget(targetId,type,content) {
    if(!content){
        alert("不能回复空")
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
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




/**
 * 一级评论
 */
function postComment() {
    var questionId = $("#question_id").val();
    var commentArea = $("#comment_area").val();
    commentTarget(questionId,1,commentArea)
}

/**
 * 二级评论
 */
function getComment(e) {
    debugger;
    var commentId = e.getAttribute("data-id");
    var comment = $("#input-" + commentId).val();
    commentTarget(commentId,2,comment)
}


/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comment = $("#comment-" + id);
    debugger;
    var attribute = e.getAttribute("data-collapse");
    if (attribute){
        comment.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length != 1){
            comment.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function(index,comment) {
                    debugger;
                    var medialeftElement =  $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded img-size",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-body comment_body"
                    }).append($("<h6/>",{
                        "class":"media-heading",
                        "text":comment.user.name
                    })).append($("<div/>",{
                        "text":comment.content
                    })).append($("<div/>",{
                        "class":"comment_menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(medialeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comment_list",
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                comment.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    debugger;
    //判断输入框是否存在定义属性
    if (previous.indexOf(value) == -1){
        if(previous.length != 0){
            $("#tag").val(previous + ',' + value);
        }else {
            $("#tag").val(value);
        }
    }
}
function showSelectTag() {
    $("#select-tag").show();
}
