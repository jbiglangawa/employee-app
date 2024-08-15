const sendRequest = (query, variables, href) => {
    const headers = {
        'content-type': 'application/json'
    }
    if(href != 'getToken') {
        headers['Authorization'] = `Bearer ${getTokenFromStorage().token}`
    }

    return fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
            query: query,
            variables: variables
        })
    }).then(res => {
        if(res.status == 403 && window.location.href != "/login") {
            window.location.href = '/login';
        }
        return res.json()
    })
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
                contacts {
                    contactId
                    contactInfo
                    isPrimary
                }
                addresses {
                    addressId
                    address1
                    address2
                    isPrimary
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

const updateEmployee = (employee) => {
    return sendRequest(`
        mutation UpdateEmployee($employee: UpdateEmployeeInput!) {
            updateEmployee(employee: $employee)
        }`, employee)
}

const deleteEmployeeById = (employeeId) => {
    return sendRequest(`
        mutation DeleteEmployee($employeeId: Int!) {
            deleteEmployee(employeeId: $employeeId)
        }`, {employeeId})
}

const getToken = (loginForm) => {
    return sendRequest(`
        mutation GetToken($loginForm: LoginForm!) {
            getToken(loginForm: $loginForm) {
                token
                roles
            }
        }`, {loginForm}, "getToken")        
}
