
function polling(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status == 200) {
            var message = JSON.parse(this.responseText).text;

            var text = document.getElementById('Text');
            text.value = text.value + message + "\n";

            polling();
        }
    };

    xhttp.open("GET", "/async", true);
    xhttp.send();
}

function getTimeFromServer() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/async', true);
    xhr.send();
}