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
    let response = await getAllEmployees(states.currentPage)

    states.lastPage = Math.trunc(response.data.getEmployees.totalCount / 10)
    
    document.getElementById("employee-table").innerHTML =
        response.data.getEmployees.employees.map(employee => {
            let primaryAddress = employee.addresses.filter(a => a.isPrimary)[0];
            let primaryContact = employee.contacts.filter(a => a.isPrimary)[0];
            return `
                <tr>
                    <th scope="row">${[employee.firstName, employee.middleName, employee.lastName].join(' ')}</th>
                    <td>${primaryAddress ? [primaryAddress.address1, primaryAddress.address2].join(' ') : '<i class="whisper">None provided...</i>'}</td>
                    <td>${primaryContact ? primaryContact.contactInfo : '<i class="whisper">None provided...</i>'}</td>
                    <td>${calculateAge(employee.birthDate)}</td>
                    <td>${getLengthOfStay(moment(employee.hireDate))}</td>
                    <td>
                        <div class="grid">
                            <i class="fas fa-edit clickeable-icon" onclick="openUpdateEmployeeModal(${employee.employeeId})"></i>
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
    document.getElementById('delete').addEventListener('click', () => {
        deleteEmployee(Number(document.getElementById('employeeId').value))
        document.getElementById("edit-employee-dialog").open = false
    })

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


const openEmployeeFormModal = async (employeeId) => {
    resetEmployeeFormModal()
    document.getElementById("edit-employee-dialog").open = true

    // If employeeId is not null, modal is for existing employee
    if(employeeId) {
        document.getElementById("edit-employee-dialog-title").innerHTML = "UPDATE EMPLOYEE INFORMATION"
        const employeeById = await getEmployeeById(employeeId);
        const {contacts, addresses, ...employeeBasicFields} = employeeById.data.getEmployeeById
        
        // Basic Fields
        employeeBasicFields.birthDate = moment(employeeBasicFields.birthDate).format("yyyy-MM-DD")
        employeeBasicFields.hireDate = moment(employeeBasicFields.hireDate).format("yyyy-MM-DD")
        Object.keys(employeeBasicFields).forEach(key => {
            document.getElementsByName(key)[0].value = employeeBasicFields[key]
        })
        
        // Contact and Address
        contactInfoTableBody.innerHTML = ""
        addressInfoTableBody.innerHTML = ""
        contacts.forEach(c => addContactInfo(c))
        addresses.forEach(a => addAddressInfo(a))

        document.getElementById('delete').classList.remove('hidden')
    }else {
        document.getElementById("edit-employee-dialog-title").innerHTML = "NEW EMPLOYEE REGISTRATION"
        document.getElementById('delete').classList.add('hidden')
    }
}

const resetEmployeeFormModal = () => {
    clearValidations()
    document.getElementById("regis-form").reset();
    contactInfoTableBody.innerHTML = ""
    addressInfoTableBody.innerHTML = ""
    addContactInfo()
    addAddressInfo()
}

const saveEmployeeData = async () => {
    const valid = validateRequiredFields()
    if(!valid) 
        return

    var formData = Object.fromEntries(new FormData(document.getElementById("regis-form")));
    var sanitized = sanitizeFormData({
        ...formData, 
        contactSize: contactInfoTableBody.children.length, 
        addressSize: addressInfoTableBody.children.length
    });
    
    if(sanitized.employee.employeeId) {
        await updateEmployee(sanitized);
    }else {
        await createEmployee(sanitized);
    }
    
    setTimeout(() => {
        renderEmployeeTable();
        document.getElementById("edit-employee-dialog").open = false;
        saveSuccessToast.showToast();
    }, 1000)
}

const sanitizeFormData = (formData) => {
    formData.contacts = []
    formData.addresses = []
    
    if(formData.contactSize) {
        let keys = Object.keys(formData).filter(key => key.startsWith("contact-"))
        let groupedKeys = Object.groupBy(keys, (key) => {
            let keySplit = key.split("-")
            return keySplit[keySplit.length - 1]
        })
        Object.keys(groupedKeys).forEach(key => {
            formData.contacts.push({
                contactId: formData["contact-id-" + key],
                contactInfo: formData["contact-info-" + key],
                isPrimary: formData["contact-is-primary-" + key] == "on"
            })
        })
    }

    if(formData.addressSize) {
        let keys = Object.keys(formData).filter(key => key.startsWith("address-"))
        let groupedKeys = Object.groupBy(keys, (key) => {
            let keySplit = key.split("-")
            return keySplit[keySplit.length - 1]
        })
        Object.keys(groupedKeys).forEach(key => {
            formData.addresses.push({
                addressId: formData["address-id-" + key],
                address1: formData["address1-" + key],
                address2: formData["address2-" + key],
                isPrimary: formData["address-is-primary-" + key] == "on"
            })
        })
    }

    let sanitizedData = {
        employee: {
            firstName: formData.firstName, 
            lastName: formData.lastName, 
            middleName: formData.middleName, 
            gender: formData.gender, 
            maritalStatus: formData.maritalStatus, 
            currentPosition: formData.currentPosition, 
            birthDate: formatDate(formData.birthDate), 
            hireDate: formatDate(formData.hireDate), 
            contacts: formData.contacts, 
            addresses: formData.addresses
        }
    }
    if(formData.employeeId) {
        sanitizedData.employee.employeeId = Number(formData.employeeId);
        sanitizedData.employee.clearContacts = formData.contacts.length == 0
        sanitizedData.employee.clearAddresses = formData.addresses.length == 0
        return sanitizedData
    }
    
    return sanitizedData
    
}

const closeRow = (rowId) => {
    document.getElementById(rowId).remove()
}

const addContactInfo = (contact) => {
    const i = contactInfoTableBody.children.length + 1;
    $('#contact-info-table-body').append(`
        <tr id="contact-${i}">
            <th scope="row">
                ${contact && contact?.contactId ? `<span name="contact-id-${i}" class="hidden">${contact?.contactId}</span>` : ''}
                <input type="text" name="contact-info-${i}" maxlength=100
                    class="required validate-maxlength" placeholder="Contact Information"
                    aria-label="Contact Information" value="${contact && contact?.contactInfo ? contact?.contactInfo : ''}">
                <small id="contact-info-${i}-helper"></small>
            </th>
            <td><input type="checkbox" name="contact-is-primary-${i}" ${contact && contact?.isPrimary ? 'checked' : ''} /></td>
            <td><i class="fas fa-times clickeable-icon" onclick="closeRow('contact-${i}')"></i></td>
        </tr>`)
}

const addAddressInfo = (address) => {
    const i = addressInfoTableBody.children.length + 1;
    $('#address-info-table-body').append(`
        <tr id="address-${i}">
            <th scope="row">
                ${address && address?.addressId ? `<span name="address-id-${i}" class="hidden">${address?.addressId}</span>` : ''}
                <input type="text" name="address1-${i}" maxlength=500
                    class="required validate-maxlength" placeholder="Address 1"
                    aria-label="Address 1" value="${address && address?.address1 ? address?.address1 : ''}">
                <small id="address1-${i}-helper"></small>
            </th>
            <td>
                <input type="text" name="address2-${i}" maxlength=500
                    class="required validate-maxlength" placeholder="Address 2"
                    aria-label="Address 2" value="${address && address?.address2 ? address?.address2 : ''}">
                <small id="address2-${i}-helper"></small>
            </td>
            <td><input type="checkbox" name="address-is-primary-${i}" ${address && address.isPrimary ? 'checked' : ''}></td>
            <td><i class="fas fa-times clickeable-icon" onclick="closeRow('address-${i}')"></i></td>
        </tr>`)
}

const openUpdateEmployeeModal = async (employeeId) => {
    openEmployeeFormModal(employeeId)
}

const deleteEmployee = async (employeeId) => {
    await deleteEmployeeById(employeeId);
    await renderEmployeeTable();
    deleteSuccessToast.showToast();
}

const navigate = (page) => {
    states.currentPage = page;
    renderEmployeeTable();
}


