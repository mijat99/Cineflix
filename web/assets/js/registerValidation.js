const form = document.getElementById('forma');
const firstname = document.getElementById('firstname');
const lastname = document.getElementById('lastname');
const username = document.getElementById('username');
const email = document.getElementById('email');
const adress = document.getElementById('adress');
const number = document.getElementById('number');
const password1 = document.getElementById('password1');
const password2 = document.getElementById('password2');

const green = '#4CAF50';
const red = '#F44336';

function validateFirstName(){
	if(checkIfEmpty(firstname)) return;
}

function checkIfEmpty(field){
	if(isEmpty(field.value.trim())){
		setInvalid(field)
		return true;
	}else{
		setValid(field)
		return false;
	}
}
function isEmpty(value){
	if(value==='') return true;
	return false;
}
function setInvalid(field,message){
	field.className = 
}