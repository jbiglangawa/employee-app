const sendRequest = (query, variables) => {
    return fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: query,
            variables: variables
        })
    }).then(res => res.json())
}

const getAllEmployees = (page) => {
    return sendRequest(`
        query GetEmployees($pageSize: PageSize!) {
            getEmployees(pageSize: $pageSize) {
                employees {
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
                totalCount
            }
        }`, {
            pageSize: {
                page: Number(page),
                size: 10,
            }
        })
}

const getEmployeeById = (employeeId) => {
    return sendRequest(`
        query GetEmployees($employeeId: Int!) {
            getEmployeeById(employeeId: $employeeId) {
                employeeId
                firstName
                lastName
                middleName
                birthDate
                hireDate
                gender
                maritalStatus
                currentPosition
                createdOn
                updatedOn
                contacts {
                    contactId
                    contactInfo
                    isPrimary
                    createdOn
                    updatedOn
                }
                addresses {
                    addressId
                    address1
                    address2
                    isPrimary
                    createdOn
                    updatedOn
                }
            }
        }`, {employeeId})
}

const createEmployee = (employee) => {
    return sendRequest(`
        mutation CreateEmployee($employee: CreateEmployeeInput!) {
            createEmployee(employee: $employee)
        }`, employee)
}

const deleteEmployeeById = (employeeId) => {
    return sendRequest(`
        mutation DeleteEmployee($employeeId: Int!) {
            deleteEmployee(employeeId: $employeeId)
        }`, {employeeId})
}
