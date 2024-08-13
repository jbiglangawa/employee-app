const validateMaxLength = ele => {
    ele.addEventListener("input", evt => {
        if(evt.target.value.length >= evt.target.maxLength) {
            ele.setAttribute("aria-invalid", true)
            document.getElementById(evt.target.name + "-helper").innerHTML = "Field cannot exceed length " + evt.target.maxLength
        }else if(ele.getAttribute("aria-invalid")) {
            ele.removeAttribute("aria-invalid")
            document.getElementById(evt.target.name + "-helper").innerHTML = ""
        }
    })
}

const removeValidationOnSelect = ele => {
    ele.addEventListener("input", evt => {
        if(ele.value && ele.getAttribute("aria-invalid")) {
            ele.removeAttribute("aria-invalid")
            document.getElementById(evt.target.name + "-helper").innerHTML = ""
        }
    })
}

const validateRequiredFields = () => {
    var fieldsToValidate = document.getElementsByClassName("required");
    var allFieldsValid = true;
    for(var i = 0; i < fieldsToValidate.length; i++) {
        if(!fieldsToValidate.item(i).value) {
            fieldsToValidate.item(i).setAttribute("aria-invalid", true)
            document.getElementById(fieldsToValidate.item(i).name + "-helper").innerHTML = "This field is required"
            allFieldsValid = false;
        }else if(fieldsToValidate.item(i).getAttribute("aria-invalid")) {
            fieldsToValidate.item(i).removeAttribute("aria-invalid")
            document.getElementById(fieldsToValidate.item(i).name + "-helper").innerHTML = ""
        }
    }
    return allFieldsValid;
}
