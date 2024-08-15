const TOK3N_KEY = "JESSION_TKEN";

const clearTokenFromStorage = () => {
    Cookies.remove(TOK3N_KEY);
}

const getTokenFromStorage = () => {
    let cookie = Cookies.get(TOK3N_KEY);
    if(cookie && cookie != "null") {
        return JSON.parse(cookie);
    }else {
        window.location.href = '/login';
    }
}

const isUserAndAdmin = () => {
    const roles = getTokenFromStorage().roles
    if(!roles) {
        window.location.href = '/login?error=forbidden-access';
    }
    return roles.includes('ROLE_USER') && roles.includes('ROLE_ADMIN')
}

const saveTokenToStorage = (token) => {
    Cookies.set(TOK3N_KEY, JSON.stringify(token))
}

const sendLoginRequest = async () => {
    var formData = Object.fromEntries(new FormData(document.getElementById("login-form")));
    var token = await getToken(formData);
    if(token) {
        setTimeout(() => {
            saveTokenToStorage(token.data.getToken)
            window.location.href = '/employee';
        }, 1000)
    }
}
