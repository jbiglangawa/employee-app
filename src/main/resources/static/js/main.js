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
                            <i class="fas fa-edit"></i>
                            <i class="fas fa-trash"></i>
                        </div>
                    </td>
                </tr>
            `}).join('')
}

window.onload = renderEmployeeTable
