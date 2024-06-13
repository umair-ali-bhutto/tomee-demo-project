<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>SENDER</title>
		<link rel="stylesheet" href="assets/css/style.css">
		<link rel="shortcut icon" href="assets/images/favicon.ico" type="image/x-icon">
	</head>

	<body>
		<main id="main">
			<header>
				<img src="assets/images/logo.png" alt="Logo" width="5%">
			</header>

			<h2>Send Message</h2>

			<form id="myForm" method="post" onsubmit="submitForm()" autocomplete="off">
				<label for="message">Enter Message:</label>
				<input type="text" id="message" name="message" title="Please Enter Message"
					placeholder="Please Enter Message" required>
				<button type="submit">Send</button>
			</form>
		</main>
		<div id="spinnerContainer" class="spinnerContainer">
			<div id="spinner" class="spinner"></div>
			<div id="spinnerMessage" class="spinnerMessage">Sending Message....</div>
		</div>

		<div id="myModal" class="modal">
			<div class="modal-content">
				<p>${message}</p>
				<button onclick="closeModal()">Close</button>
			</div>
		</div>


		<script type="text/javascript">
			function submitForm() {
				var myForm = document.getElementById('myForm');
				myForm.action = 'send';
				myForm.method = 'post';

				document.getElementById('main').style.display = 'none';
				document.getElementById('spinnerContainer').style.display = 'block';
				myForm.submit();
			}
		</script>
	</body>



	<script type="text/javascript">
		function closeModal() {
			document.getElementById('myModal').style.display = 'none';
			document.getElementById('spinnerContainer').style.display = 'none';
			document.getElementById('main').style.display = 'block';
			document.getElementById('myForm').reset();
		}
	</script>



	</html>