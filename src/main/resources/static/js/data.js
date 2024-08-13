const fetchEmployeeData = async () => {
    const res = await fetch(serverUrl + '/graphql', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                query GetEmployees2 {
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
