function getDates(dates) {
    alert("ITWORKS");
    var currDate = new Date();
    var counter = 0;
    var mon1, mon2, mon3, mon4;
    while (counter < 4) {
        for (var i = 0; i < 7; i++) {
            if (currDate.getDay() != 1) {
                currDate.setDate(currDate.getDate() + 1);
            }
        }
        counter++;
        switch (counter) {
            case 1:
                mon1 = new Date(currDate).toDateString();
                break;
            case 2:
                mon2 = new Date(currDate).toDateString();
                break;
            case 3:
                mon3 = new Date(currDate).toDateString();
                break;
            case 4:
                mon4 = new Date(currDate).toDateString();
                break;
        }
        currDate.setDate(currDate.getDate() + 1);
    }

    document.getElementById(dates).innerHTML = '<option hidden="" disabled="disabled" selected="selected">Starting date</option>' +
            '<option value="option1">' + mon1 + '</option>' +
            '<option value="option2">' + mon2 + '</option>' +
            '<option value="option3">' + mon3 + '</option>' +
            '<option value="option4">' + mon4 + '</option>';

}

function listSuburbs(suburbDropdown) {
	alert(12);
                var x = new XMLHttpRequest();
                x.onreadystatechange = function () {
                    if (x.readyState == 4 && x.status == 200) {
                        document.getElementById(suburbDropdown).innerHTML = x.responseText;
                    }
                };

                x.open("GET", "/wp-includes/Order/suburb.php?m=listSuburds", true)
                x.send();
}
			