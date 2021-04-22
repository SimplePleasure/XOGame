$(document).ready(function(){

    $('#create-game-btn').click( function() {
         var form = $('#create-game form');
         var object = getFormData(form);
         var finalObj = {
            "gamePrefs": object
         };
         let json = JSON.stringify(finalObj);

         $.ajax({
            method: "POST",
            contentType: "application/json",
            url: "/register",
            data: json,
            success: function(response) {
                var table = document.getElementById('game-list');
                var tr = document.createElement('tr');
                tr.innerHTML = '<td>'+ response.id +'</td><td align="center">' + response.sideSize + '</td><td align="center">' + response.pointsToWin + '</td>';
                table.appendChild(tr);
            },
            error: function(response) {
                let map = response.responseJSON;
                alert(map.hints.player);
            }
         });
    });



    function getFormData($form) {

        var dataArray = $form.serializeArray();
        var object = {};
            $(dataArray).each(function(index, obj) {
            object[obj.name] = obj.value;
        });
        return object;
    }



//    setTimeout(response.id, 1000);
//    function checkGameStatus(gameId) {
//        $.ajax({
//            method: "GET",
//
//        });
//    }


});