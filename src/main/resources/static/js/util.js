const DATE_FORMAT_ISO_8601 = "yyyy-mm-ddTHH:MM:SS+01:00";
const DATE_FORMAT_INPUT = "yyyy-mm-dd";

const calculateAge = (date) => {
    return Math.trunc(moment().diff(date, 'years'))
}

const getLengthOfStay = (date) => {
    const yearsDiff = moment().diff(date, 'years')
    const monthsDiff = moment().diff(date, 'months')
    let formatted = []
    if(yearsDiff > 0) {
        formatted.push(yearsDiff.toFixed(0) + 'y')
    }
    if(monthsDiff > 0) {
        const subtractor = Math.trunc(Number(yearsDiff.toFixed(0))) * 12
        formatted.push((monthsDiff.toFixed(0) - subtractor) + 'm')
    }
    if(formatted.length == 0) return '0m'
    return formatted.join(' ')
}

const formatDate = (date) => {
    return moment(date).format()
}

const saveSuccessToast = Toastify({
    text: "Record saved successfully!",
    duration: 2000,
    gravity: "bottom",
    style: {
      background: "#00895A",
      fontFamily: "GothamPro"
    },
})

const deleteSuccessToast = Toastify({
    text: "Record deleted successfully!",
    duration: 2000,
    gravity: "bottom",
    style: {
      background: "#00895A",
      fontFamily: "GothamPro"
    },
})

const failedToSaveToast = Toastify({
    text: "An unknown error has occurred",
    duration: 2000,
    gravity: "bottom",
    style: {
      background: "#D93526",
      fontFamily: "GothamPro"
    },
})