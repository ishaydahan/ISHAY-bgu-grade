function postreq() {

	var mainform = document.getElementsByName("mainForm");
	//var child = mainform.childNodes[0];
	if (mainform.length > 0){
		var tables = mainform[0].getElementsByTagName("table");
		if (tables.length > 1) {

			var tablebody = tables[1].getElementsByTagName("tbody")[0];
			chrome.storage.sync.get({
		    pass: 'password',
		    user: "username",
				id: "12345678"
		  }, function(items) {
				// get data
				var data1 = tablebody.getElementsByTagName("tr")[1].getElementsByTagName("td")[0].innerHTML;
				var data2 = tablebody.getElementsByTagName("tr")[14].getElementsByTagName("td")[0].getElementsByTagName("a")[0].getAttribute("href");

				var p_year = document.getElementsByClassName("CourseTitle")[0].innerHTML.substring(0,4);
				var p_semester = data2.substring(data2.indexOf(p_year)).substring(7,8);
				var out_institution = data2.substring(data2.indexOf(p_year)).substring(11,12);
				var list_department = data1.substring(0, 3);
				var list_degree_level = data1.substring(4, 5);
				var list_course = data1.substring(6, 10);

				var settings = {
					"async": true,
					"crossDomain": true,
					"url": "https://stark-hollows-95485.herokuapp.com/grade/",
					"method": "POST",
					"headers": {
						"cache-control": "no-cache",
						"postman-token": "8b2d0056-b57a-5910-8275-08b9e9a5641d"
					},
					"data": "{ \"user\": \""+items.user+"\", \"pass\": \""+items.pass+"\", \"id\": \""+items.id+"\", \"p_year\": \""+p_year+"\", \"p_semester\": \""+p_semester+"\", \"out_institution\": \""+out_institution+"\", \"list_department\": \""+list_department+"\", \"list_degree_level\": \""+list_degree_level+"\", \"list_course\": \""+list_course+"\"}\n"
				}

				$.ajax(settings).done(function (response) {
					if (response=='0'){
						alert("You have problem with your BGU credentials. Set them on Chrome extention options page!");
					}else{
						var blob=new Blob([response]);
						var link=document.createElement('a');
						link.href=window.URL.createObjectURL(blob);
						link.download=p_year +"_"+ p_semester +"_"+ out_institution +"_"+ list_department +"_"+ list_degree_level +"_"+ list_course +".pdf";
						link.click();
					}
				});
		  });
		}
	}
}

function injectNewRow(tablebody) {

	// create a new row at index 5
	var newRow = tablebody.insertRow(4);
	var txtCell = newRow.insertCell(0);
	txtCell.setAttribute("class","BlackText");

	var button = document.createElement("button");
	var text = document.createTextNode("לחץ פעם אחת להורדת גרף ההתפלגות והמתן בסבלנות");
	button.appendChild(text);
	button.setAttribute("id","grade-req");
	button.setAttribute("type","button");
	txtCell.appendChild(button);

  button.addEventListener('click', postreq());

	// space cell
	var spaceCell = newRow.insertCell(1);
	var imgnode = document.createElement("img");
	imgnode.setAttribute("src","/images/nothing.gif");
	imgnode.setAttribute("width","5");
	imgnode.setAttribute("height","1");
	imgnode.setAttribute("border","0");
	spaceCell.appendChild(imgnode);

	// title cell
	var titleCell = newRow.insertCell(2);
	titleCell.setAttribute("class","formDetails");
	titleCell.setAttribute("valign","top");

	var titletxt = document.createTextNode(':התפלגות ציונים');
	titleCell.appendChild(titletxt);
}

function addStuff() {
	var mainform = document.getElementsByName("mainForm");
	//var child = mainform.childNodes[0];
	if (mainform.length > 0){
		var tables = mainform[0].getElementsByTagName("table");
		if (tables.length > 1) {
			var tablebody = tables[1].getElementsByTagName("tbody")[0];
			injectNewRow(tablebody);
		}
	}
}

document.onreadystatechange = async function () {
	if (document.readyState === "complete") {
		addStuff();
	}
}
