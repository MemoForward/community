/**
 * 打开阿星的博客
 */
function openMyBlog() {
    window.open("https://memoforward.cn");
}

/**
 * 添加一级评论
 */
function postComment() {
    var questionId = $("#questionId").val();
    var content = $("#commentText").val();
    if(!content){
        alert("评论不能为空！");
        return;
    }
    var reload = true;
    comment(questionId, content, 1, reload);
}

/**
 * 添加二级评论
 */
function postSecondComment(e) {
    var parentId = $(e).attr("data-second-id");
    var content = $("#secondCommentText-" + parentId).val();
    if(!content){
        alert("评论不能为空！");
        return;
    }
    var reload = false;
    comment(parentId, content, 2, reload);
    var commets =parseInt($("#commentIcon-"+parentId).text()) + 1;
    $("#commentIcon-"+parentId).text(commets);
}

function comment(id, content, type, reload) {
    if (content === "") {
        alert("回复不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": id,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code === 200) {
                if(reload === true){
                    window.location.reload();
                }else{
                    // 获取二级评论当前页,如果要给评论排序就需要做，目前只降序的话，就返回二级评论第一页就可以了
                    // var curPage = $("#comment-" + id).attr("data-page");
                    //window.location.reload();
                    showSecondComments(id, 1);
                }
            } else {
                if (response.code === 3006) {
                    var isConfirmed = confirm(response.message);
                    if (isConfirmed) {
                        window.location.href = ("https://github.com/login/oauth/authorize?client_id=2cd4c16d130031968295" +
                            "&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("redirectToComment", "yes");
                        window.localStorage.setItem("questionId", id);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}


/**
 * 展开二级评论
 * @param e
 */
function collapseComments(e, page) {
    var commentId = $(e).attr("data-id");
    // $('#comment-'+commentId).toggleClass("in");
    //console.log(commentId);
    var hasIn = $('#comment-' + commentId).hasClass("in");
    if (hasIn) {
        $('#comment-' + commentId).removeClass("in");
        $('#active-' + commentId).css("color", "");
    } else {
        showSecondComments(commentId, page);
        $('#comment-' + commentId).addClass("in");
        $('#active-' + commentId).css("color", "#ff8506");
    }
}

/**
 * 显示二级评论
 * @param commentId
 * @param page
 */
function showSecondComments(commentId, page) {
    $.getJSON("/comment/" + commentId + "?page=" + page, function (data) {
        var items = [];
        var pageDTO = data.data;
        // console.log(data);
        var secondCommentContainer = $("#comment-" + commentId);
        var curPage = secondCommentContainer.attr("data-page");
        $('div').remove(".del" + commentId);
        $.each(pageDTO.list.reverse(), function (key, commentDTO) {
            // console.log(commentDTO);
            var d = new Date(commentDTO.gmtCreate);
            var content = $("<div/>", {
                "class": "media comment-border del"+commentId,
                html: "<div class='media-left'>" +
                    "<img class='media-object img-rounded img-size' src='" + commentDTO.user.avatarUrl + "'>" +
                    "</div>" +
                    "<div class ='media-body'>" +
                    "<h5 class = 'media-heading comment-user-name'>" +
                    "<span>" + commentDTO.user.name + "</span>" +
                    "<span class='text-info'>" + " | 评论于 " +
                    d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + "</span>" +
                    "</h5>" +
                    "<div class='comment-content'>" + commentDTO.content + "</div>" +
                    "</div>"
            });
            secondCommentContainer.prepend(content);
        });
        secondCommentContainer.attr("data-page", page);
        $("li").remove(".del"+commentId);

        var flagDiv = $("#QflagDiv-"+commentId);
        var hasPre, hasNext;
        if (!pageDTO.hasPre) hasPre = "disabled";
        if (!pageDTO.hasNext) hasNext = "disabled";
        // console.log(pageDTO);
        // debugger;
        if(pageDTO.pages.length === 0){
            console.log("fuck");
            var hiddenShowContent = $("<li/>",{
                "class": "del"+commentId,
                html:"<div style='font: -apple-system-caption2'>这个人没人理，你仁慈一点给他一个评论吧</div>"
            });
            flagDiv.before(hiddenShowContent);
            return;
        }
        var pagePre = $("<li/>", {
            "class": "del"+commentId + " " + hasPre,
            html: "<a href='javascript:showSecondComments(" + commentId + "," + 1 + ")' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a>"
        });
        var pageNext = $("<li/>", {
            "class": "del"+commentId+ " " + hasNext,
            html: "<a href='javascript:showSecondComments(" + commentId + "," + pageDTO.totalPage + ")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a>"
        });
        flagDiv.before(pagePre);
        flagDiv.after(pageNext);
        $.each(pageDTO.pages, function (key, p) {
            var acitve;
            if (p == page) acitve = "active";
            var pageContent = $("<li/>", {
                "class": "del"+commentId+ " " + acitve,
                html: "<a href='javascript:showSecondComments(" + commentId + "," + p + ")'>" + p + "</a>"
            });
            flagDiv.before(pageContent);
        });

        $("#secondCommentText-"+commentId).val("");
    });
}


