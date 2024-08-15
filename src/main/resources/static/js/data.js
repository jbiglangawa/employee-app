const sendAuthRequest = (query, variables) => {
    return fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
            'Authorization': `Bearer ${getTokenFromStorage().token}`
        },
        body: JSON.stringify({
            query: query,
            variables: variables
        })
    }).then(res => {
        if(res.status == 403) {
            window.location.href = '/login';
        }
        return res.json()
    })
}

const getAllEmployees = (page) => {
    return sendAuthRequest(`
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
    return sendAuthRequest(`
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
    return sendAuthRequest(`
        mutation CreateEmployee($employee: CreateEmployeeInput!) {
            createEmployee(employee: $employee)
        }`, employee)
}

const updateEmployee = (employee) => {
    return sendAuthRequest(`
        mutation UpdateEmployee($employee: UpdateEmployeeInput!) {
            updateEmployee(employee: $employee)
        }`, employee)
}

const deleteEmployeeById = (employeeId) => {
    return sendAuthRequest(`
        mutation DeleteEmployee($employeeId: Int!) {
            deleteEmployee(employeeId: $employeeId)
        }`, {employeeId})
}

const getToken = async (loginForm) => {
    const query = `
        mutation GetToken($loginForm: LoginForm!) {
            getToken(loginForm: $loginForm) {
                token
                roles
            }
        }`

    const res = await fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: query,
            variables: {loginForm}
        })
    }).then(res => res.json())
    
    if(res.errors) {
        if(res.errors[0].message === "Unauthorized") {
            loginErrorToast("Invalid Username or Password").showToast();
            return null;
        }else {
            failedToSaveToast.showToast();
        }
    }

    return res;
}
