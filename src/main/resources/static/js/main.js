$(document).ready(function() {
    let timer;

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
                tr.classList.add('gameSample');
                tr.innerHTML = '<td>'+ response.id +'</td><td align="center">' + response.sideSize + '</td><td align="center">' + response.pointsToWin + '</td>';
                table.appendChild(tr);
                playingCheck();
            },
            error: function(response) {
                let map = response.responseJSON;
                alert(map.hints.player);
            }
         });
    });


    // Делегирование событий для join
    $('#game-list').click(function(event) {
        let target = event.target;
        if (target.tagName == 'TD') {
            let gameId = {
                "gameId": target.parentElement.firstElementChild.innerHTML
            }
            let json = JSON.stringify(gameId);
            $.ajax({
                method: "POST",
                contentType: "application/json",
                url: "/join",
                data: json,
                success: function(response) {
                    playingCheck();
                }
            });
        }
    });

    // Делегирование событий для turn
    $('#myGame').click(function(event) {
        var target = event.target;
        if (target.tagName != 'TD') {
            return;
        }
        let turn = {
            "xAxis": target.id,
            "yAxis": target.parentElement.id
        }
        let json = JSON.stringify(turn);
        $.ajax({
            method: "POST",
            contentType: "application/json",
            url: "/turn",
            data: json
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


    function playingCheck() {
        $.ajax({
            method: "GET",
            url: "/playing",
            success: function(response) {
                if(response.result == true) {
                    document.getElementById('create-game').hidden = true;
                    timer = setInterval(checkGameStatus, 1000);
                }
            }

        });
    }

    function checkGameStatus() {
        var gameShown = document.getElementById('myGame');
        $.ajax({
            method: "GET",
            url: "/check",
            success: function(response) {
                if(response.status == 'WAIT') {
                    var table = document.createElement("table");
                    table.innerHTML = '<tr><td rowspan="2">my game</td> <td>game id</td> <td>time left</td> </tr><tr><td>'
                    + response.gameId + '</td> <td align="center">' + response.waiting + '</td></tr>';
                    gameShown.textContent = '';
                    gameShown.appendChild(table);
                } else if (response.status == 'PLAY') {
                    var battlefield = response.battlefield;
                    var table = document.createElement("table");
                    table.id = 'battleField';
                    for (x in battlefield) {
                        var row = document.createElement("tr");
                        row.id = x;
                        for (y in battlefield[x]) {
                            var col = document.createElement("td");
                            col.id = y;
                            if(battlefield[x][y] == null) {
                                col.innerHTML = '.';
                            } else {
                                col.innerHTML = battlefield[x][y];
                            }
                            col.width = '15px';
                            col.align = 'center';
                            col.classList.add('pane')
                            row.appendChild(col);
                        }
                        table.appendChild(row);
                    }
                    gameShown.textContent = '';
                    gameShown.appendChild(table);
                } else if (response.status == 'FINISH') {
                    setTimeout(() => clearInterval(timer));
                    gameShown.textContent = response.winnerSymbol + ' winner!';
                    setTimeout(() => gameShown.textContent ='', 4000);
                    setTimeout(() => document.getElementById('create-game').hidden = false, 4000);

                }
            },
            error: function() {
                setTimeout(() => clearInterval(timer));
                gameShown.textContent = '';
                document.getElementById('create-game').hidden = false;
            }
        });
    }

    playingCheck();
});