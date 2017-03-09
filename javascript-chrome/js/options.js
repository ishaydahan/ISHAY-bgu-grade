// Saves options to chrome.storage.sync.
function save_options() {
  var pass = document.getElementById('pass').value;
  var user = document.getElementById('user').value;
	var id = document.getElementById('id').value;
  chrome.storage.sync.set({
    pass: pass,
    user: user,
		id: id
  }, function() {
    // Update status to let user know options were saved.
    var status = document.getElementById('status');
    status.textContent = 'Options saved.';
    setTimeout(function() {
      status.textContent = '';
    }, 750);
  });
}

// Restores select box and checkbox state using the preferences
// stored in chrome.storage.
function restore_options() {
  // Use default value pass = 'red' and user = true.
  chrome.storage.sync.get({
    pass: 'password',
    user: "username",
		id: "12345678"
  }, function(items) {
    document.getElementById('pass').value = items.pass;
    document.getElementById('user').value = items.user;
		document.getElementById('id').value = items.id;
  });
}
document.addEventListener('DOMContentLoaded', restore_options);
document.getElementById('save').addEventListener('click',
    save_options);
