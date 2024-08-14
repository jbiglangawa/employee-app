let states = {
    currentPage: 0,
    lastPage: 0
}

let navStart = document.getElementById('nav-start')
let navBackward = document.getElementById('nav-backward')
let navForward = document.getElementById('nav-forward')
let navEnd = document.getElementById('nav-end')

let contactInfoTableBody = document.getElementById("contact-info-table-body");
let addressInfoTableBody = document.getElementById("address-info-table-body");

const renderEmployeeTable = async () => {
    let response = await fetchEmployeeData(states.currentPage)
    states.lastPage = (response.data.getEmployees.totalCount / 10).toFixed(0)

    document.getElementById("employee-table").innerHTML =
        response.data.getEmployees.employees.map(employee => {
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

const renderTableButtonNavigationButtons = () => {
    if(states.lastPage > 0) {
        if(states.currentPage > 0) {
            navStart.disabled = false
            navStart.classList.remove('outline')
            navBackward.disabled = false
            navBackward.classList.remove('outline')
        }else {
            navStart.disabled = true
            navStart.classList.add('outline')
            navBackward.disabled = true
            navBackward.classList.add('outline')
        }
        if(states.currentPage < states.lastPage) {
            navForward.disabled = false
            navForward.classList.remove('outline')
            navEnd.disabled = false
            navEnd.classList.remove('outline')
        }else {
            navForward.disabled = true
            navForward.classList.add('outline')
            navEnd.disabled = true
            navEnd.classList.add('outline')
        }
    }
}

const handleNavigation = (button, pageHandle) => {
    button.addEventListener('click', () => {
        const newPage = pageHandle();
        if(newPage >= 0 || newPage <= states.lastPage) {
            states.currentPage = newPage;
            renderEmployeeTable()
                .then(() => renderTableButtonNavigationButtons())
        }
    });
}

const setupListeners = () => {
    document.getElementById('close-dialog').addEventListener('click', () => {
        document.getElementById("edit-employee-dialog").open = false
    });

    handleNavigation(navStart, () => 0);
    handleNavigation(navBackward, () => states.currentPage - 1);
    handleNavigation(navForward, () => states.currentPage + 1);
    handleNavigation(navEnd, () => states.lastPage);
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

window.onload = async () => {
    await renderEmployeeTable();
    renderTableButtonNavigationButtons();
    setupListeners();
    setupValidation();
}

/* Post-onload handlers */


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

const navigate = (page) => {
    states.currentPage = page;
    renderEmployeeTable();
}
