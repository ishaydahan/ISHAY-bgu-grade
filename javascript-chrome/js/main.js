
chrome.runtime.onInstalled.addListener(function (object) {
    chrome.tabs.create({url: "options.html"}, function (tab) {
    });
});



chrome.browserAction.onClicked.addListener(function(activeTab)
{

	chrome.storage.sync.get({
		pass: 'password',
		user: "username",
		id: "12345678"
	}, function(items) {
		if(chrome.runtime.lastError || (items.user=='username' || items.id=='12345678')){
				alert('גש למסך האפשרויות של התוסף ועדכן פרטים אישיים'); // win
        return;
    }else{
			var newURL = "https://bgu4u.bgu.ac.il/html/annual/main.html";
			chrome.tabs.create({ url: newURL });
		}
	});
});
