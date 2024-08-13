const fetchEmployeeData = async () => {
    const res = await fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                query GetEmployees {
                    getEmployees(pageSize: {page: 0, size: 10}) {
                        employeeId
                        firstName
                        lastName
                        middleName
                        birthDate
                        hireDate
                        contacts {
                            contactInfo
                            isPrimary
                        }
                        addresses {
                            address1
                            isPrimary
                        }
                    }
                }`
        })
    }).then(res => res.json())
    return res
}

const createEmployee = async (formData) => {
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
                address1: formData["address1-" + key],
                address2: formData["address2-" + key],
                isPrimary: formData["address-is-primary-" + key] == "on"
            })
        })
    }

    const res = await fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                mutation CreateEmployee {
                    createEmployee(
                        employee: {
                            firstName: "${formData.firstName}"
                            lastName: "${formData.lastName}"
                            middleName: "${formData.middleName}"
                            gender: "${formData.gender}"
                            maritalStatus: "${formData.maritalStatus}"
                            currentPosition: "${formData.position}"
                            birthDate: "${formatDate(formData.birthDate)}"
                            hireDate: "${formatDate(formData.hireDate)}"
                            contacts: [${formData.contacts.length > 0 ? 
                                formData.contacts.map(c => `{contactInfo: "${c.contactInfo}", isPrimary: ${c.isPrimary}}`).join(',') : ''}]
                            addresses: [${formData.addresses.length > 0 ? 
                                formData.addresses.map(a => `{address1: "${a.address1}", address2: "${a.address2}", isPrimary: ${a.isPrimary}}`) : ''}]
                        }
                    )
                }`
        })
    }).then(res => res.json())
    return res
}

const deleteEmployeeById = async (employeeId) => {
    const res = await fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                mutation DeleteEmployee {
                    deleteEmployee(employeeId: ${employeeId})
                }`
        })
    }).then(res => res.json())
    return res
}
