const TOK3N_KEY = "JESSION_TKEN";

const getTokenFromStorage = () => {
    return Cookies.get(TOK3N_KEY);
}

const saveTokenToStorage = (token) => {
    Cookies.set(TOK3N_KEY, token)
}

const sendLoginRequest = async () => {
    var formData = Object.fromEntries(new FormData(document.getElementById("login-form")));
    var token = await getToken(formData);
    setTimeout(() => {
        saveTokenToStorage(token.data.getToken)
        window.location.href = '/employee';
    }, 1000)
}
