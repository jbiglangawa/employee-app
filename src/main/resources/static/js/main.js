const renderEmployeeTable = async () => {
    let response = await fetchEmployeeData()

    document.getElementById("employee-table").innerHTML =
        response.data.getEmployees.map(employee => {
            let primaryAddress = employee.addresses.filter(a => a.isPrimary)[0];
            let primaryContact = employee.contacts.filter(a => a.isPrimary)[0];
            return `
                <tr>
                    <th scope="row">${[employee.firstName, employee.middleName, employee.lastName].join(' ')}</th>
                    <td>${primaryAddress ? [primaryAddress.address1, primaryAddress.address2].join(' ') : ''}</td>
                    <td>${primaryContact ? primaryContact.contactInfo : ''}</td>
                    <td>${calculateAge(employee.birthDate)}</td>
                    <td>${getLengthOfStay(moment(employee.hireDate))}</td>
                    <td>
                        <div class="grid">
                            <i class="fas fa-edit clickeable-icon" onclick=""></i>
                            <i class="fas fa-trash clickeable-icon" onclick="deleteEmployee(${employee.employeeId})"></i>
                        </div>
                    </td>
                </tr>
            `}).join('')
}

const setupListeners = () => {
    document.getElementById('close-dialog').addEventListener('click', () => {
        document.getElementById("edit-employee-dialog").open = false
    })
}

const setupValidation = () => {
    let fieldsToValidate = document.getElementsByClassName("validate-maxlength");
    for(var i = 0; i < fieldsToValidate.length; i++) {
        validateMaxLength(fieldsToValidate.item(i))
    }

    let zonedDateTimeToValidate = document.getElementsByClassName("zonedDateTime");
    for(var i = 0; i < zonedDateTimeToValidate.length; i++) {
        removeValidationOnSelect(zonedDateTimeToValidate.item(i))
    }
}

const init = () => {
    renderEmployeeTable();
    setupListeners();
    setupValidation();
}

window.onload = init

var contactInfoTableBody = document.getElementById("contact-info-table-body");
var addressInfoTableBody = document.getElementById("address-info-table-body");

const openEmployeeFormModal = (employeeId) => {
    document.getElementById("edit-employee-dialog").open = true

    // If employeeId is not null, modal is for existing employee
    if(employeeId) {
        document.getElementById("edit-employee-dialog-title").innerHTML = "UPDATE EMPLOYEE INFORMATION"
    }
}

const saveEmployeeData = async () => {
    const valid = validateRequiredFields()
    
    if(!valid) return

    var formData = new FormData(document.getElementById("regis-form"));
    await createEmployee({...Object.fromEntries(formData), contactSize: contactInfoTableBody.children.length, addressSize: addressInfoTableBody.children.length});
    setTimeout(() => {
        renderEmployeeTable();
        document.getElementById("edit-employee-dialog").open = false;
    }, 1000)
}

const closeRow = (rowId) => {
    document.getElementById(rowId).remove()
}

const addContactInfo = () => {
    const currentLength = contactInfoTableBody.children.length + 1;
    $('#contact-info-table-body').append(`
        <tr id="contact-${currentLength}">
            <th scope="row">
                <input type="text" name="contact-info-${currentLength}" maxlength=100
                    class="required validate-maxlength" placeholder="Contact Information"
                    aria-label="Contact Information">
                <small id="contact-info-${currentLength}-helper"></small>
            </th>
            <td><input type="checkbox" name="contact-is-primary-${currentLength}" /></td>
            <td><i class="fas fa-times clickeable-icon" onclick="closeRow('contact-${currentLength}')"></i></td>
        </tr>`)
        
}

const addAddressInfo = () => {
    const currentLength = addressInfoTableBody.children.length + 1;
    $('#address-info-table-body').append(`
        <tr id="address-${currentLength}">
            <th scope="row">
                <input type="text" name="address1-${currentLength}" maxlength=500
                    class="required validate-maxlength" placeholder="Address 1"
                    aria-label="Address 1">
                <small id="address1-${currentLength}-helper"></small>
            </th>
            <td>
                <input type="text" name="address2-${currentLength}" maxlength=500
                    class="required validate-maxlength" placeholder="Address 2"
                    aria-label="Address 2">
                <small id="address2-${currentLength}-helper"></small>
            </td>
            <td><input type="checkbox" name="address-is-primary-${currentLength}"></td>
            <td><i class="fas fa-times clickeable-icon" onclick="closeRow('address-${currentLength}')"></i></td>
        </tr>`)
}

const deleteEmployee = async (employeeId) => {
    await deleteEmployeeById(employeeId);
    await renderEmployeeTable();
}
