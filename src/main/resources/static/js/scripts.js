function passwordChanged() {
	var strength = document.getElementById('strength');
	var strongRegex = new RegExp("^(?=.{14,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
	var mediumRegex = new RegExp("^(?=.{10,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
	var enoughRegex = new RegExp("(?=.{8,}).*", "g");
	var pwd = document.getElementById("password");
	if (pwd.value.length == 0) {
		strength.innerHTML = 'Escriba la contraseña';
	} else if (false == enoughRegex.test(pwd.value)) {
		strength.innerHTML = 'Se necesita más caracteres';
	} else if (strongRegex.test(pwd.value)) {
		strength.innerHTML = '<span style="color:green">¡Fuerte!</span>';
	} else if (mediumRegex.test(pwd.value)) {
		strength.innerHTML = '<span style="color:orange">¡Intermedia!</span>';
	} else {
		strength.innerHTML = '<span style="color:red">¡Débil!</span>';
	}
}

function mostrarPassword() {
    var cambio = document.getElementById("password");
    if (cambio.type == "password") {
        cambio.type = "text";
        $('.icon').removeClass('bi bi-eye-fill').addClass('bi bi-eye-slash-fill');
        $('.btnPass').prop('title','Ocultar contraseña');
        
    } else {
        cambio.type = "password";
        $('.icon').removeClass('bi bi-eye-slash-fill').addClass('bi bi-eye-fill');
        $('.btnPass').prop('title','Mostrar contraseña');
    }
}