const calculateAge = (date) => {
    return moment().diff(date, 'years').toFixed(0)
}

const getLengthOfStay = (date) => {
    const yearsDiff = moment().diff(date, 'years')
    const monthsDiff = moment().diff(date, 'months')
    let formatted = []
    if(yearsDiff > 0) {
        formatted.push(yearsDiff.toFixed(0) + 'y')
    }
    if(monthsDiff > 0) {
        const subtractor = Number(yearsDiff.toFixed(0)) * 12
        formatted.push((monthsDiff.toFixed(0) - subtractor) + 'm')
    }
    return formatted.join(' ')
}
