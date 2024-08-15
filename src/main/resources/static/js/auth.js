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

const saveTokenToStorage = (token) => {
    Cookies.set(TOK3N_KEY, JSON.stringify(token))
}

const sendLoginRequest = async () => {
    var formData = Object.fromEntries(new FormData(document.getElementById("login-form")));
    var token = await getToken(formData);
    setTimeout(() => {
        saveTokenToStorage(token.data.getToken)
        window.location.href = '/employee';
    }, 1000)
}
