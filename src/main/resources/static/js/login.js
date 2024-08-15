document.getElementById('login-button').addEventListener('click', evt => {
    evt.preventDefault();
    clearTokenFromStorage();
    sendLoginRequest();
})