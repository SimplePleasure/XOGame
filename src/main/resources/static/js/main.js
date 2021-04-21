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



});





//$(document).ready(function(){
//
//    $('#create-game-btn').click( function() {
//
//         var dataArray = $('#create-game form').serializeArray();
//         var json = {};
//         $(dataArray).each(function(index, obj) {
//         json[obj.name] = obj.value;
//         });
//
//         var finalObj = {
//            "gamePrefs": json
//         };
//         console.log(finalObj);
//
//         let
//
//         $.ajax({
//            method: "POST",
//            contentType: "application/json",
//            url: "/register",
//            data: finalObj,
//            success: function() {
//                alert('success');
//            }
//         });
//
//
////      var side = $('#side-size-num').val();
////      var pointsCount = $('#points-count-num').val();
////      var prefs = {
////        "side-size": side,
////        "points-count": pointsCount
////      };
////      var obj = {
////        gamePrefs: prefs
////      };
////      console.log(obj);
////
////      $.ajax({
////         method: "POST",
////         contentType: "application/json",
////         url: "/register",
////         data: obj,
////         success: function() {
////             alert('success');
////         }
////      });
//
//
//
//
//
//    });
//
//
//
//    function getFormData($form) {
//        var unindexed_array = $form.serializeArray();
//        var indexed_array = {};
//
//        $.map(unindexed_array, function(n, i){
//            indexed_array[n['name']] = n['value'];
//        });
//
//        return indexed_array;
//    }
//
//
//
//});